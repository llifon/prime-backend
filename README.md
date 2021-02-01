# prime-backend
A REST server for serving a client with all of the prime numbers which exist within a number range.

Technologies / Dependencies:

* JDK 15
* Spring-Boot 2.4.2
* Postgresql 13 (JDBC driver 42.2.5)
* Gradle

## How it works

Starts a web-server at `localhost:8080`. On the first boot, it will populate a database with known primary numbers using the Sieve of Eratosthenes algorithm. 
The amount of data that's generated can be configured in `DatabaseInitializer.java`. Once populated, the server will be ready to serve user requests. Each time
a client accesses the endpoint, it will return paginated responses to the client. Pagination was implemented for the extensibility of the project in the future, potentially allowing
requests to span the range of billions of numbers.

## Using the service as a client

The application has one primary endpoint:

*  `GET /prime-range?from=2&to=100&page=0&size=20`

**URL Parameters** :

`from=[long]` where `from` is the first value in the range (inclusive)

`to=[long]` where `to` is the last value in the range (inclusive)

`page=[integer]` where `page` is an index (starts at 0) which points to a paginated view of the data

`size=[integer]` where `size` sets the maximum amount of data that can be shown on each page


## Running the project as a developer

* Install Postgresql 13
* Install DBC driver 42.2.5
* Configure `url`, `username` and `password` in `application.properties`
* Launch application with `BackendApplication.Main`
