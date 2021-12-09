# Zip Codes By Radius

## Android coding exercise by Del Edwards

### Project overview

> Simple two screen app that fetches nearby zip codes. The first screen accepts a zip code and a distance in km, the second screen shows all nearby zipcodes within the radius (specified distance) of the specified zip code, in a scrollable list.

### Architectual patterns

> Standard Google Jetpack MVVM app using: Jetpack View Models, LiveData for binding values to UIs and updating UI state, Android Fragment-based navigation between screens, and Kotlin coroutines for performing asyncronous actions.

### Notable Technologies used

- Hilt for dependency injection
- Retrofit2 for Http client
- Gson for json serialazation
- Arrow Core, functional toolkit for kotlin. Used for "Either<L, R>" as a return type (similar to using hand-rolled "sealed class Result..." with nested generic types for alternately returning "Success" or "Failure" from Services).
- JUnit for unit testing
