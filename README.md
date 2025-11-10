# Line Chart Compose Multiplatform

## Overview
A Compose Multiplatform library for rendering a customizable line chart.

## Dependency
Add the dependency to your build.gradle. Replace version with what is available [here](https://central.sonatype.com/artifact/io.github.deanalvero/line-chart-cmp/versions).
```
implementation("io.github.deanalvero:line-chart-cmp:<version>")
```

## Usage
TODO

## Sample App
### Build and Run Android Application
- macOS/Linux
  ```shell
  ./gradlew :sample:composeApp:assembleDebug
  ```
- Windows
  ```shell
  .\gradlew.bat :sample:composeApp:assembleDebug
  ```

### Build and Run Desktop (JVM) Application
- macOS/Linux
  ```shell
  ./gradlew :sample:composeApp:run
  ```
- Windows
  ```shell
  .\gradlew.bat :sample:composeApp:run
  ```

### Build and Run Web Application
- Wasm target (faster, modern browsers):
  - macOS/Linux
    ```shell
    ./gradlew :sample:composeApp:wasmJsBrowserDevelopmentRun
    ```
  - Windows
    ```shell
    .\gradlew.bat :sample:composeApp:wasmJsBrowserDevelopmentRun
    ```
- JS target (slower, supports older browsers):
  - macOS/Linux
    ```shell
    ./gradlew :sample:composeApp:jsBrowserDevelopmentRun
    ```
  - Windows
    ```shell
    .\gradlew.bat :sample:composeApp:jsBrowserDevelopmentRun
    ```

### Build and Run iOS Application
Open the [/iosApp](./iosApp) directory in Xcode and run it from there.
