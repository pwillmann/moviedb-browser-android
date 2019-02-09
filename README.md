# moviedb-browser-android [![Build Status](https://travis-ci.com/pwillmann/moviedb-browser-android.svg?branch=master)](https://travis-ci.com/pwillmann/moviedb-browser-android)

This project is a personal demo app to test new libraries and new architectures. The [TMDB](https://www.themoviedb.org/) API is used to provide a homescreen with popular tv shows and a detail page with details for one specific tv show.

It is currently based around Kotlin, MvRx, Epoxy, Dagger and ReactiveX. 

## Setup

In order to run the app a TMDB Api Token is required. 
The token must be put in either the project root levels `gradle.properties` or the `gradle.properties`
of the system wide `.gradle` folder.

`TMDB_API_KEY="API_KEY"`.

## Project

Modules are split in three main categories:

* feature/
* lib/
* core/

### Feature Modules

Are Android Library Modules that contain Screens of the App. Features can not depend on each other,
only lib and core modules are allowed to be imported

### Lib Modules

Are Plain Java/Kotlin/ Android Modules and provide more advanced helper utilities, resources, architecture helper and data provider.
Lib Modules can not depend on Feature Modules, only core modules are allowed as dependencies.

Only Exception are Repo modules, those are allowed to depend on DataSource modules.

### Core Modules

Are Plain Java/Kotlin/Android Modules that don't have any further project internal dependencies and are safe to be depended on by anything.


## Third-Party Libraries

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

