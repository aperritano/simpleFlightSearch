# simpleFlightSearch (Angular+Spring)

Prerequistes 

Maven: 3.6.1
Angular: 8.0.4
Node: 12.10.0
Spring Boot: 2.2.0

# simpleFlightSearch (Angular+Spring MVC)

### Prerequisites 

Maven: 3.6.1
Angular: 8.0.4
Node: 12.10.0
Spring Boot: 2.2.0

## Structure
simpleFlightSearch
		|_ client - angular client
		|_server - spring mvc, spring boot

## Running 

in simpleFlightSearch directory run: 
mvn package
mvn spring-boot:run

Then in the server directory run:
mvn spring-boot:run

Then in a browser go to: http://localhost:8080

![image](preview.png)

# simpleFlightSearch
I want to asses your ability to create a web application and web service. It truly is the bare minimum of knowledge necessary to be successful in this position. I don't want you to spend a lot of time on this. You should be able to do this in a few hours if the job is right for you.

### Flight Search

This programming task consists of building a simple web application to search for flights. Fork this repository and create your application. It should take this input from the user:

(Flight Number ||  (Origin && Destination)) && Date

The application will call a service that you create using either Node with Express or Java with Spring MVC. I have provided some sample data for the application in this file 


[Flight Documents](./flight-docs/flight-sample.json)



The file contains an array whose elements represent flights. The data should be defined as a constant in your service. 


You must use Angular 4 or higher. Style however you would like. You have total freedom to do what you want but make sure it can do these two things:

	• Validate that the user has provided the right criteria to make a search 
	• Neatly display the results

Create a pull request once you have it working. I will clone your repository, verify that it works, and evaluate it.



