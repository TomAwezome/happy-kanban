![](https://github.com/TomAwezome/happy-kanban/actions/workflows/android.yml/badge.svg?query=branch%3Amaster)

# Happy Kanban

Simple Kanban app for Android written in Java with Android Studio using SQLite database for local task storage. 

![](app/src/main/res/mipmap-xxhdpi/ic_launcher_round.png)

## Install

Download and install the [latest release APK](https://github.com/TomAwezome/happy-kanban/releases/latest).

## Build & Install

- Open the project in Android Studio.
- Click `Build` --> `Select Build Variant...`
- Under `Active Build Variant`, choose whether you want to build the `debug` or `release` variant.
    - To build the `release` variant, make sure to first create a local keystore, and then set `RELEASE_STORE_FILE_PATH`, `RELEASE_STORE_PASSWORD`, `RELEASE_STORE_KEY_PASS`, and `RELEASE_STORE_KEY_ALIAS` in `local.properties`.
- Plug in your Android device, and click `Run` --> `Run 'app'`.

## Screenshots

![](screenshots/screenshot-overview.png)
![](screenshots/screenshot-category-list.png)
![](screenshots/screenshot-task-edit.png)

