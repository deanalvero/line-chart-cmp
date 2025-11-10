package io.github.deanalvero.chart.line.demoapp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "LineChartComposeMultiplatform",
    ) {
        App()
    }
}