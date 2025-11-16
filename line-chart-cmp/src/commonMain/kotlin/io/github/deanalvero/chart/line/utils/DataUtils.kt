package io.github.deanalvero.chart.line.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import io.github.deanalvero.chart.line.axis.AxisValue
import io.github.deanalvero.chart.line.axis.XAxis
import io.github.deanalvero.chart.line.axis.YAxis
import io.github.deanalvero.chart.line.marker.MarkerData
import io.github.deanalvero.chart.line.data.LineData
import io.github.deanalvero.chart.line.point.Point
import io.github.deanalvero.chart.line.transformer.DataTransformer
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

fun calculateBounds(data: List<LineData>, xAxis: XAxis?, yAxis: YAxis?, isStacked: Boolean): Rect {
    if (data.isEmpty() || data.all { it.points.isEmpty() }) {
        return Rect(0f, 0f, 1f, 1f)
    }

    if (isStacked) {
        val (stackedTop, _) = computeStackedSeries(data.map { it.points })
        val topSeries = stackedTop.last()

        val xs = topSeries.map { it.x }
        val ys = topSeries.map { it.y }

        var minX = xs.min()
        var maxX = xs.max()

        val minY = 0f
        var maxY = ys.max()

        if (minX == maxX) { minX -= 1f; maxX += 1f }
        if (maxY == minY) { maxY += 1f }

        val finalMinX = (xAxis?.min as? AxisValue.Fixed)?.value ?: minX
        val finalMaxX = (xAxis?.max as? AxisValue.Fixed)?.value ?: maxX
        val finalMinY = (yAxis?.min as? AxisValue.Fixed)?.value ?: minY
        val finalMaxY = (yAxis?.max as? AxisValue.Fixed)?.value ?: maxY
        return Rect(finalMinX, finalMinY, finalMaxX, finalMaxY)
    }


    var minX = Float.MAX_VALUE
    var maxX = Float.MIN_VALUE
    var minY = Float.MAX_VALUE
    var maxY = Float.MIN_VALUE

    data.forEach { line ->
        line.points.forEach {
            minX = min(minX, it.x)
            maxX = max(maxX, it.x)
            minY = min(minY, it.y)
            maxY = max(maxY, it.y)
        }
    }

    if (minX == maxX) {
        minX -= 1f
        maxX += 1f
    }
    if (minY == maxY) {
        minY -= 1f
        maxY += 1f
    }

    val finalMinX = (xAxis?.min as? AxisValue.Fixed)?.value ?: minX
    val finalMaxX = (xAxis?.max as? AxisValue.Fixed)?.value ?: maxX
    val finalMinY = (yAxis?.min as? AxisValue.Fixed)?.value ?: minY
    val finalMaxY = (yAxis?.max as? AxisValue.Fixed)?.value ?: maxY

    return Rect(finalMinX, finalMinY, finalMaxX, finalMaxY)
}

fun findClosest(
    data: List<LineData>,
    tap: Point,
    transformer: DataTransformer
): List<MarkerData> {
    if (data.isEmpty()) return emptyList()

    val firstLine = data.firstOrNull() ?: return emptyList()
    if (firstLine.points.isEmpty()) return emptyList()

    val first = firstLine.points.minByOrNull { abs(it.x - tap.x) } ?: return emptyList()
    val x = first.x

    return data.mapNotNull { line ->
        if (line.points.isEmpty()) return@mapNotNull null
        val p = line.points.minByOrNull { abs(it.x - x) } ?: return@mapNotNull null
        MarkerData(p, line, transformer.dataToOffset(p))
    }
}

fun calculateMarkerOffset(
    pointOffset: Offset,
    markerSize: IntSize,
    offsetFromPoint: Float,
    containerWidth: Int,
    containerHeight: Int,
    verticalOffset: Int = 0
): IntOffset {
    val markerWidth = markerSize.width.toFloat()
    val markerHeight = markerSize.height.toFloat()

    val preferredX = pointOffset.x + offsetFromPoint
    val preferredY = pointOffset.y - markerHeight / 2 - verticalOffset

    val finalX = when {
        preferredX + markerWidth > containerWidth ->
            pointOffset.x - markerWidth - offsetFromPoint
        preferredX < 0 -> offsetFromPoint
        else -> preferredX
    }

    val finalY = when {
        preferredY < 0 -> offsetFromPoint
        preferredY + markerHeight > containerHeight ->
            containerHeight - markerHeight - offsetFromPoint
        else -> preferredY
    }

    return IntOffset(finalX.roundToInt(), finalY.roundToInt())
}

fun computeStackedSeries(series: List<List<Point>>): Pair<List<List<Point>>, List<List<Point>>> {
    if (series.isEmpty()) return Pair(emptyList(), emptyList())

    val nSeries = series.size
    val nX = series[0].size

    val xs = series[0].map { it.x }

    series.forEachIndexed { si, pts ->
        require(pts.size == nX) { "All series must have same x size" }
        for (i in 0 until nX) {
            require(pts[i].x == xs[i]) { "All series must have same x positions" }
        }
    }

    val values = Array(nSeries) { FloatArray(nX) }
    for (s in 0 until nSeries)
        for (i in 0 until nX)
            values[s][i] = series[s][i].y

    val stacked = Array(nSeries) { FloatArray(nX) }

    for (i in 0 until nX) {
        var acc = 0f
        for (s in 0 until nSeries) {
            acc += values[s][i]
            stacked[s][i] = acc
        }
    }

    val topPoints = (0 until nSeries).map { s ->
        (0 until nX).map { i -> Point(xs[i], stacked[s][i]) }
    }

    val basePoints = (0 until nSeries).map { s ->
        (0 until nX).map { i ->
            val baseY = if (s == 0) 0f else stacked[s - 1][i]
            Point(xs[i], baseY)
        }
    }

    return topPoints to basePoints
}
