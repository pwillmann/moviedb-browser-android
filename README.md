# moviedb-browser-android

This project is a personal demo app to test new libraries and new architectures. The [TMDB](https://www.themoviedb.org/) API is used to provide a homescreen with popular tv shows and a detail page with details for one specific tv show.

It is currently based around Kotlin, MvRx, Epoxy, Dagger and ReactiveX. 

## Setup

In order to run the app a TMDB Api Token is required. 
The token must be put in either the project root levels `gradle.properties` or the `gradle.properties`
of the system wide `.gradle` folder.

`TMDB_API_KEY="API_KEY"`.

## Third-Party Libraries used

The following libraries were at least partially used in this project:
* [MvRX](https://github.com/airbnb/MvRx)
* [Epoxy](https://github.com/airbnb/epoxy)
* [Dagger](https://github.com/google/dagger)
* [AssistedInject](https://github.com/square/AssistedInject)
* [Retrofit](https://github.com/square/retrofit)
* [Moshi](https://github.com/square/moshi)
* [Glide](https://github.com/bumptech/glide)
* [Timber](https://github.com/JakeWharton/timber)
* [DiscreteScrollView](https://github.com/yarolegovich/DiscreteScrollView)
* [ReactiveX](https://github.com/ReactiveX/RxJava)
* [Detekt](https://github.com/arturbosch/detekt)
* [Spotless](https://github.com/diffplug/spotless/tree/master/plugin-gradle)
* [Butterknife gradle plugin](https://github.com/JakeWharton/butterknife)

