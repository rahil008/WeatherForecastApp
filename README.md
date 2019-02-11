# Weather Forecast.

## Author:- Mujahid Husain Khan
email: <mujahid.hkhan@yahoo.in>

## Overview

This project currently contains only single screen that shows weather forecast for the next five days of the city specified.
It uses API from the OpenWeather.org to fetch the data. Data from the API contains 3 hourly data, however, app currently
shows only single record for a date.

## Architecture

This project follows Clean Architecture.
  - Interactor: Retrieves Entities and contains the business logic for a particular use case. They are view agnostic and can be consumed by one or more Presenters.
  - Presenter: Handles preparing content for the display.
  - Domain Entity: Classes that behave as entities for domain.

The project is segregated into three main layers.
  - Domain: This is the core of our application. It contains Entities, Repos and Interactors.
  - Presentation: It contains presenters and any presentation related components.
  - Application: It contains data, UI and device.

## Get Started

This project uses Gradle for dependency management. Please use Gradle 3.2.1 or above. It should work right out of the box.

## Dependencies

  - This app requires minimum Android SDK 25 to run.
  - This project also uses RxJava, Retrofit, RxAndroid, Android Lifecycle components, Gson which are added as dependencies.

