package io.github.deanalvero.chart.line.transformer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import io.github.deanalvero.chart.line.point.Point

class DataTransformer(private val bounds: Rect, val viewport: Rect) {
    private val xRange = bounds.width.coerceAtLeast(0.001f)
    private val yRange = bounds.height.coerceAtLeast(0.001f)

    fun dataToOffset(point: Point): Offset {
        val nx = (point.x - bounds.left) / xRange
        val ny = (point.y - bounds.top) / yRange
        return Offset(
            viewport.left + nx * viewport.width,
            viewport.bottom - ny * viewport.height
        )
    }

    fun offsetToData(offset: Offset): Point {
        val viewportWidth = viewport.width.coerceAtLeast(0.001f)
        val viewportHeight = viewport.height.coerceAtLeast(0.001f)

        val nx = (offset.x - viewport.left) / viewportWidth
        val ny = 1 - (offset.y - viewport.top) / viewportHeight
        return Point(bounds.left + nx * xRange, bounds.top + ny * yRange)
    }
}
