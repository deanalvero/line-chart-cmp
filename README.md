# Line Chart Compose Multiplatform

## Overview
A Compose Multiplatform library for rendering a customizable line chart.

## Dependency
Add the dependency to your build.gradle. Replace version with what is available [here](https://central.sonatype.com/artifact/io.github.deanalvero/line-chart-cmp/versions).
```
// In your shared module's build.gradle.kts
commonMain.dependencies {
    implementation("io.github.deanalvero:line-chart-cmp:<version>")
}
```

## Usage
### Basic
```
import io.github.deanalvero.chart.line.LineChart
import io.github.deanalvero.chart.line.data.LineData
import io.github.deanalvero.chart.line.marker.Marker
import io.github.deanalvero.chart.line.point.Point

@Composable
fun SimpleLineChart() {
    val dataPoints = listOf(
        Point(x = 0f, y = 10f),
        Point(x = 1f, y = 25f),
        Point(x = 2f, y = 15f),
        Point(x = 3f, y = 30f),
        Point(x = 4f, y = 22f)
    )

    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),

        data = listOf(
            LineData(points = dataPoints, label = "Line 1")
        ),
        marker = Marker(
            content = { markerData ->
                Text(text = "${markerData.point.x}, ${markerData.point.y}")
            }
        ),
        isInteractable = true
    )
}
```

### Advanced
Try out the sample app.

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
