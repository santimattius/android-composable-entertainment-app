# Android Composable Entertainment App

This is a template to build an Android app applying good practices and using a guide app architecture by google,
you will see that the code is super decoupled with external frameworks and even with the same
Android framework, this will help you to model your domain purely in Kotlin without generating
external dependencies.

[//]: # (Screenshot)
<p align="center">
  <img width="280" src="https://github.com/santimattius/android-composable-entertainment-app/blob/master/screenshoot/entertainment_movies.png?raw=true"/>
  <img width="280" src="https://github.com/santimattius/android-composable-entertainment-app/blob/master/screenshoot/entertainment_tvshows.png?raw=true"/>
  <img width="280" src="https://github.com/santimattius/android-composable-entertainment-app/blob/master/screenshoot/entertainment_detail.png?raw=true"/> 

</p>

## Content

TheMovieDB API: Check this [documentation](https://www.themoviedb.org/documentation/api).

## Setup

Using local properties for define api key:

```properties
apiKey="{your-api-key}"
```

## Verification

Run check project:

```shell
> ./gradlew check
```

Run tests project:

```shell
> ./gradlew test
```

## Dependencies

Below you will find the libraries used to build the template and according to my criteria the most
used in android development so far.

- **[Jetpack Compose](https://developer.android.com/jetpack/compose)**, UI components.
- **[Jetpack Navigation Compose](https://developer.android.com/jetpack/compose/navigation)**, for navigation.
- **[Hilt](https://developer.android.com/training/dependency-injection/hilt-android?hl=es-419)**, dependencies provider.
- **[Retrofit](https://square.github.io/retrofit/)**, networking.
- **[Room](https://developer.android.com/training/data-storage/room)**, local storage.
- **[Moshi](https://github.com/square/moshi)**, json parser.
- **[Coil](https://coil-kt.github.io/coil/compose/)**, with image loader.
- **[Kotlin coroutines and Flows](https://kotlinlang.org/docs/reference/coroutines-overview.html)**.
- **[Mockk](https://mockk.io/)**, testing library.
- **[MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver)**, networking testing library.
- **[Robolectric](http://robolectric.org/)**, android testing library.

## References

- [Guide to App Architecture](https://developer.android.com/topic/architecture)
- [Jetpack](https://developer.android.com/jetpack?gclid=CjwKCAjw7diEBhB-EiwAskVi13xJGdb6SCxqntF3pNt6JQ4ulvEQsB9JelBK2OIG5P0cePTCcsOksBoCk1sQAvD_BwE&gclsrc=aw.ds)
- [Android developers](https://developer.android.com/)
