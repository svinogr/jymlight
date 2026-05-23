# OpenCode Agent Instructions

## Project Overview
- This is a Kotlin Android application using Jetpack Compose
- Uses Room database for local data persistence
- Modular structure with `app` and `database` modules
- Gradle build system with Kotlin DSL

## Key Commands
- Build: `./gradlew build`
- Run: `./gradlew run` or `./gradlew installDebug`
- Test: `./gradlew test`
- Clean: `./gradlew clean`

## Architecture
- Main entry point: `MainActivityCompose.kt`
- App module depends on database module
- Uses AndroidX Compose for UI with Material3 components
- Follows MVVM pattern with ViewModels and repositories

## Key Libraries & Frameworks
- Jetpack Compose (UI framework)
- Room Database (local persistence)
- Coil (image loading)
- Accompanist (UI utilities)
- Navigation Compose (for navigation)
- Datastore for preferences
- Gson for JSON parsing

## Build & Development
- Minimum SDK: 29
- Target SDK: 34
- Kotlin version: 1.9.0
- Java compatibility: 1.8

## Important Notes
- The app uses a splash screen with core-splashscreen library
- Uses Material Design 3 components with Material3 theme
- Includes Camera and Storage permissions
- No minification in release builds
- Uses AndroidX Compose BOM for consistent versions

## Testing
- Unit tests in `app/src/test`  
- Instrumentation tests in `app/src/androidTest`
- Database tests in `database` module