package io.github.deanalvero.chart.line.data

import androidx.compose.ui.graphics.Color
import io.github.deanalvero.chart.line.point.Point
import io.github.deanalvero.chart.line.segment.SegmentStyle

data class LineData(
    val points: List<Point>,
    val label: String,
    val segmentStyle: SegmentStyle = SegmentStyle(),
    val interpolation: LineInterpolation = LineInterpolation.Linear,
    val fillColor: Color? = null
)
