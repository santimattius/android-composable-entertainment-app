# Android Entertainment App

This is a template to build an Android app applying good practices and using a clean architecture,
you will see that the code is super decoupled with external frameworks and even with the same
Android framework, this will help you to model your domain purely in Kotlin without generating
external dependencies.

[//]: # (Screenshot)
<p align="center">

  <img wight="280" src="https://github.com/santimattius/android-entertainment-app/blob/master/screenshoot/entertainment_app.png?raw=true" alt="App Capture"/>

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

- **[Koin](https://insert-koin.io/)**, dependencies provider.
- **[Retrofit](https://square.github.io/retrofit/)**, networking.
- **[Moshi](https://github.com/square/moshi)**, json parser.
- **[Glide](https://github.com/bumptech/glide)**, with image loader.
- **[Kotlin coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)**.
- **[Mockk](https://mockk.io/)**, testing library.
- **[MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver)**, networking testing library.
- **[Robolectric](http://robolectric.org/)**, android testing library.

## References

- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Clean Code](https://blog.cleancoder.com/)
- [Jetpack](https://developer.android.com/jetpack?gclid=CjwKCAjw7diEBhB-EiwAskVi13xJGdb6SCxqntF3pNt6JQ4ulvEQsB9JelBK2OIG5P0cePTCcsOksBoCk1sQAvD_BwE&gclsrc=aw.ds)
- [Android developers](https://developer.android.com/)
