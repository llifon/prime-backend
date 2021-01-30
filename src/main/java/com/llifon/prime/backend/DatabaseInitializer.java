package com.llifon.prime.backend;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * An application runner which is auto-configured by Spring to run before the server is launched.
 * <p>
 * This task will populate the database with all of the prime numbers that we need if the database is empty.
 */
@Component
public class DatabaseInitializer implements ApplicationRunner {

    // the class logger
    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);

    // The repository to store the prime numbers into
    private final PrimeNumberRepository repository;

    /**
     * Creates a new instance of the application runner.
     *
     * @param repository The repository to store the prime numbers into.
     */
    public DatabaseInitializer(PrimeNumberRepository repository) {
        this.repository = repository;
    }

    /**
     * Runs the initializer task of storing all of the prime numbers which exist in the pre-set range.
     *
     * @param args Not used.
     */
    public void run(ApplicationArguments args) throws InterruptedException {

        var largestSupportedPrime = this.repository.getLargestPrime();
        if (largestSupportedPrime != null) {
            logger.info("Skipping database population - data already exists");
            return;
        }

        var generator = new SieveOfEratosthenesPrimeSequenceGenerator();
        var batchRanges = new LinkedList<AbstractMap.SimpleEntry<Integer, Integer>>();

        final long batchSize = 1000000L; // save to the database in chunks of 1 million
        final long populateUpTo = 10000000;
        var offset = 2L; // Start from prime #2

        logger.info("Initializing the database with all primes between {} and {}", offset, populateUpTo);

        //// Creates many range batches that will define the parallel work

        do {
            long amount = offset + batchSize;
            if (amount > populateUpTo) {
                amount = populateUpTo;
            }

            batchRanges.add(new AbstractMap.SimpleEntry<>((int) offset, (int) amount));
            offset += batchSize;
        } while (offset <= populateUpTo);

        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Callable<Boolean>> tasks = new ArrayList<>();

        for (final var object : batchRanges) {
            Callable<Boolean> c = () -> {
                List<PrimeNumberEntity> primeList = new LinkedList<>();
                var primes = generator.Generate(object.getKey(), object.getValue(), false);
                while (primes.hasNext()) {
                    var prime = new PrimeNumberEntity();
                    prime.setPrimeNumber((long) primes.next());
                    primeList.add(prime);
                }

                this.repository.saveAll(primeList);
                return true;
            };
            tasks.add(c);
        }

        executorService.invokeAll(tasks);
        largestSupportedPrime = this.repository.getLargestPrime();
        logger.info("Finished populating the database. Largest available prime is {}", largestSupportedPrime);
    }
}