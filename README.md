Elevator Task - multithreading application with TDD approach that simulate elevator's moving and boarding/deboarding of passengers.

Elevator Task consists of 12 classes:

Direction - enum for elevator moving direction.

TransportationState - enum for current state of passenger.

Container - abstract class for elevator/dispatch/arrival containers.

Passenger - class with passengers information.

Elevator/DispatchArrival Containers - class with elevator/dispatchArrival containers respectively.

Story - class for story in DispatchArrivalContainer

Building - class for building stucture(Elevator/DispatchArrival Containers + passengers).

Generator - class that generate random passengers, dispatch/arrival container and read data from file(storiesNumber, elevatorCapacity, passengersNumber).

Controller - class implemented as Runnable to wake up passengers.

TransportationTask - class implemented as Runnable to complete every transportation task.

Runner - running class.

Build.xml - Ant file that run compile tests/src classes and run. 

Lib consists of: - hamcrest-all-1.3.jar - junit-4.12.jar - log4j-api-2.7.jar - log4j-core-2.7.jar