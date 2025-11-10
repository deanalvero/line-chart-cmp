package io.github.deanalvero.chart.line.marker

import androidx.compose.ui.geometry.Offset
import io.github.deanalvero.chart.line.data.LineData
import io.github.deanalvero.chart.line.point.Point

data class MarkerData(
    val point: Point,
    val line: LineData,
    val offset: Offset
)
