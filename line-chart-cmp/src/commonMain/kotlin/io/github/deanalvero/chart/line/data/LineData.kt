package io.github.deanalvero.chart.line.data

import io.github.deanalvero.chart.line.point.Point
import io.github.deanalvero.chart.line.segment.SegmentStyle

data class LineData(
    val points: List<Point>,
    val label: String,
    val defaultSegmentStyle: SegmentStyle = SegmentStyle(),
    val segmentOverrides: List<SegmentStyle?> = emptyList()
)
