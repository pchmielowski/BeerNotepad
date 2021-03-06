# BeerNotepad

A simple Android application to keep information and rating of tasted beers.
Allows user to create an account, log in and keep all information in a cloud service.

<img src=graphics/login.png width=150 />
<img src=graphics/beers2.png width=150 />
<img src=graphics/beers.png width=150 />
<img src=graphics/add.png width=150 />
<img src=graphics/photo.png width=150 />

[Video of working application](https://www.youtube.com/watch?v=BpP4OdItgcw)

## Used technologies

* **Firebase** as a user login service and a database
* **Facebook SDK** for Facebook login
* **Dagger2** for dependency injection
* **Mockito**, **JUnit** and **Espresso** for tests (unit and instrumentation)
* **Butterknife** for view injections
* **RxJava** for reactive design
* **Checkstyle** for static code quality analysis

## Modules

* **app** - main application module: Android related code
* **lib** - business logic module - presenters, model objects etc. Pure Java code.
* **data** - Firebase related module
* additional, will be moved to separate repos in the future:
  * **log** - logging module
  * **numeric** - number operations module

## Design assumptions

* Usage of MVP pattern
  * Lightweight `Activities` - view logic in separate `*View` classes
* Almost all classes immutable (excluding Activities etc.)
* Dagger2 dependency injection in `Activities` and Espresso tests, plain old constructor injection in other classes
* Screen-related packages. 
  * In contrast to most popular approach of having packages like: `activities`, `adapters`, `views`,
  in this application there are packages related to the screen: `login`, `register` etc. Each one contains classes like:
  `Activity`, `Adapter`, `View`, `Presenter` related with one screen.
  This approach allow me to make more classes and methods **package-private** instead of **public**.
