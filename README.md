# BeerNotepad

A simple Android application to keep information and rating of tasted beers.
Allows user to create an account, log in and keep all information in a cloud service.

## Used technologies

* **Firebase** as a user login service and a database
* **Dagger2** for dependency injection (only in Activities and tests)
* **Mockito**, **JUnit** and **Espresso** for tests (unit and instrumentation)
* **Butterknife** for view injections
* **RxJava** for reactive design

## Modules

* **app** - main application module: Android related code
* **lib** - business logic module - presenters, model objects etc. Pure Java code.
* **data** - Firebase related module
* **log** - additional, helper, logging module
