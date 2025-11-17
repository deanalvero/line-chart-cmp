package io.github.deanalvero.chart.line.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.dp
import io.github.deanalvero.chart.line.axis.XAxis
import io.github.deanalvero.chart.line.axis.XAxisPosition
import io.github.deanalvero.chart.line.axis.YAxis
import io.github.deanalvero.chart.line.axis.YAxisPosition
import io.github.deanalvero.chart.line.grid.GridLines
import io.github.deanalvero.chart.line.data.LineData
import io.github.deanalvero.chart.line.data.LineInterpolation
import io.github.deanalvero.chart.line.point.Point
import io.github.deanalvero.chart.line.transformer.DataTransformer
import kotlin.math.max

fun DrawScope.drawGridLines(
    gridLines: GridLines,
    viewport: Rect,
    bounds: Rect,
    xAxis: XAxis?,
    yAxis: YAxis?,
    transformer: DataTransformer
) {
    val xCount = max(0, xAxis?.labelCount ?: 0)
    val yCount = max(0, yAxis?.labelCount ?: 0)

    if (gridLines.showVertical) {
        val step = bounds.width / (xCount - 1)
        for (i in 0 until xCount) {
            val x = transformer.dataToOffset(Point(bounds.left + step * i, bounds.top)).x
            drawLine(
                color = gridLines.verticalStyle.color,
                start = Offset(x, viewport.top),
                end = Offset(x, viewport.bottom),
                strokeWidth = gridLines.verticalStyle.strokeWidth.toPx(),
                pathEffect = gridLines.verticalStyle.pathEffect
            )
        }
    }
    if (gridLines.showHorizontal) {
        val step = bounds.height / (yCount - 1)
        for (i in 0 until yCount) {
            val y = transformer.dataToOffset(Point(bounds.left, bounds.top + step * i)).y
            drawLine(
                color = gridLines.horizontalStyle.color,
                start = Offset(viewport.left, y),
                end = Offset(viewport.right, y),
                strokeWidth = gridLines.horizontalStyle.strokeWidth.toPx(),
                pathEffect = gridLines.horizontalStyle.pathEffect
            )
        }
    }
}

fun DrawScope.drawXAxis(
    axis: XAxis?,
    viewport: Rect,
    bounds: Rect,
    transformer: DataTransformer,
    textMeasurer: TextMeasurer
) {
    if (axis == null) {
        return
    }

    val labelStyle = axis.labelStyle.textStyle.copy(color = axis.labelStyle.color)
    val labelCount = max(2, axis.labelCount)

    val y = if (axis.axisLinePosition == XAxisPosition.Bottom) viewport.bottom else viewport.top
    drawLine(
        color = axis.style.color,
        start = Offset(viewport.left, y),
        end = Offset(viewport.right, y),
        strokeWidth = axis.style.strokeWidth.toPx(),
        pathEffect = axis.style.pathEffect
    )

    val step = bounds.width / (labelCount - 1)
    repeat(labelCount) {
        val x = bounds.left + step * it
        val label = axis.valueFormatter.format(x)
        val text = textMeasurer.measure(label, labelStyle)
        val posX = transformer.dataToOffset(Point(x, bounds.top)).x - text.size.width / 2
        val labelY = if (axis.labelPosition == XAxisPosition.Bottom)
            viewport.bottom + 4.dp.toPx()
        else viewport.top - text.size.height - 4.dp.toPx()
        drawText(text, topLeft = Offset(posX, labelY))
    }
}

fun DrawScope.drawYAxis(
    axis: YAxis?,
    viewport: Rect,
    bounds: Rect,
    transformer: DataTransformer,
    textMeasurer: TextMeasurer
) {
    if (axis == null) {
        return
    }

    val labelStyle = axis.labelStyle.textStyle.copy(color = axis.labelStyle.color)
    val labelCount = max(2, axis.labelCount)

    val x = if (axis.axisLinePosition == YAxisPosition.Left) viewport.left else viewport.right
    drawLine(
        color = axis.style.color,
        start = Offset(x, viewport.top),
        end = Offset(x, viewport.bottom),
        strokeWidth = axis.style.strokeWidth.toPx(),
        pathEffect = axis.style.pathEffect
    )

    val step = bounds.height / (labelCount - 1)
    repeat(labelCount) {
        val y = bounds.top + step * it
        val label = axis.valueFormatter.format(y)
        val text = textMeasurer.measure(label, labelStyle)
        val posY = transformer.dataToOffset(Point(bounds.left, y)).y - text.size.height / 2
        val labelX = if (axis.labelPosition == YAxisPosition.Left)
            viewport.left - text.size.width - 6.dp.toPx()
        else viewport.right + 6.dp.toPx()
        drawText(text, topLeft = Offset(labelX, posY))
    }
}

fun DrawScope.drawLineSeries(line: LineData, transformer: DataTransformer) {
    val offsets = line.points.map { transformer.dataToOffset(it) }
    if (offsets.size < 2) return

    val linePath = buildLinePath(offsets, line.interpolation)

    line.fillColor?.let {
        val baseline = List(offsets.size) { i ->
            Offset(offsets[i].x, transformer.viewport.bottom)
        }

        val area = buildAreaPath(
            topPoints = offsets,
            basePoints = baseline,
            interpolation = line.interpolation
        )

        drawPath(
            path = area,
            color = it,
            style = Fill
        )
    }

    val style = line.segmentStyle

    drawPath(
        path = linePath,
        color = style.color,
        style = Stroke(
            width = style.strokeWidth.toPx(),
            cap = StrokeCap.Round,
            join = StrokeJoin.Round,
            pathEffect = style.pathEffect
        )
    )

    line.points.forEach { point ->
        point.style?.let { style ->
            style.drawer.run { drawPoint(transformer.dataToOffset(point), style) }
        }
    }
}

fun DrawScope.drawLineSeriesList(
    data: List<LineData>,
    transformer: DataTransformer,
    isStacked: Boolean
) {
    if (!isStacked) {
        drawAllFills(data, transformer)
        drawAllLines(data, transformer)
        drawAllPoints(data, transformer)
        return
    }

    val seriesPoints = data.map { it.points }
    val (stackedTop, stackedBase) = computeStackedSeries(seriesPoints)
    drawAllStackedFills(data, transformer, stackedTop, stackedBase)
    drawAllStackedLines(data, transformer, stackedTop)
    drawAllStackedPoints(data, transformer, stackedTop)
}

private fun DrawScope.drawAllFills(data: List<LineData>, transformer: DataTransformer) {
    data.forEach { series ->
        val offsets = series.points.map { transformer.dataToOffset(it) }
        if (series.fillColor != null) {
            val baseline = offsets.map { Offset(it.x, transformer.viewport.bottom) }
            val area = buildAreaPath(offsets, baseline, series.interpolation)
            drawPath(area, color = series.fillColor, style = Fill)
        }
    }
}

private fun DrawScope.drawAllLines(data: List<LineData>, transformer: DataTransformer) {
    data.forEach { series ->
        val offsets = series.points.map { transformer.dataToOffset(it) }
        val path = buildLinePath(offsets, series.interpolation)
        val style = series.segmentStyle
        drawPath(
            path = path,
            color = style.color,
            style = Stroke(
                width = style.strokeWidth.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Round,
                pathEffect = style.pathEffect
            )
        )
    }
}

private fun DrawScope.drawAllPoints(data: List<LineData>, transformer: DataTransformer) {
    data.forEach { series ->
        series.points.forEach { point ->
            point.style?.let { st ->
                val pos = transformer.dataToOffset(point)
                st.drawer.run {
                    drawPoint(pos, st)
                }
            }
        }
    }
}

fun DrawScope.drawAllStackedFills(
    data: List<LineData>,
    transformer: DataTransformer,
    stackedTop: List<List<Point>>,
    stackedBase: List<List<Point>>
) {
    data.forEachIndexed { index, series ->
        val fillColor = series.fillColor ?: return@forEachIndexed

        val topOffsets = stackedTop[index].map { transformer.dataToOffset(it) }
        val baseOffsets = stackedBase[index].map { transformer.dataToOffset(it) }

        val area = buildAreaPath(
            topPoints = topOffsets,
            basePoints = baseOffsets,
            interpolation = series.interpolation
        )

        drawPath(
            path = area,
            color = fillColor,
            style = Fill
        )
    }
}

fun DrawScope.drawAllStackedLines(
    data: List<LineData>,
    transformer: DataTransformer,
    stackedTop: List<List<Point>>
) {
    data.forEachIndexed { index, series ->
        val topOffsets = stackedTop[index].map { transformer.dataToOffset(it) }
        val path = buildLinePath(topOffsets, series.interpolation)

        val style = series.segmentStyle

        drawPath(
            path = path,
            color = style.color,
            style = Stroke(
                width = style.strokeWidth.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Round,
                pathEffect = style.pathEffect
            )
        )
    }
}

fun DrawScope.drawAllStackedPoints(
    data: List<LineData>,
    transformer: DataTransformer,
    stackedTop: List<List<Point>>
) {
    data.forEachIndexed { index, series ->
        val topOffsets = stackedTop[index].map { transformer.dataToOffset(it) }

        series.points.forEachIndexed { i, point ->
            point.style?.let { style ->
                style.drawer.run {
                    drawPoint(
                        center = topOffsets[i],
                        style = style
                    )
                }
            }
        }
    }
}

private fun buildLinePath(points: List<Offset>, interpolation: LineInterpolation): Path {
    val path = Path()
    if (points.isEmpty()) return path

    path.moveTo(points.first().x, points.first().y)

    when (interpolation) {

        LineInterpolation.Linear -> {
            for (i in 1 until points.size) {
                path.lineTo(points[i].x, points[i].y)
            }
        }

        LineInterpolation.Step -> {
            for (i in 1 until points.size) {
                val prev = points[i - 1]
                val cur = points[i]
                path.lineTo(cur.x, prev.y)
                path.lineTo(cur.x, cur.y)
            }
        }

        LineInterpolation.Quadratic -> {
            for (i in 1 until points.size) {
                val p0 = points[i - 1]
                val p1 = points[i]
                val mid = Offset((p0.x + p1.x) / 2f, (p0.y + p1.y) / 2f)
                path.quadraticBezierTo(p0.x, p0.y, mid.x, mid.y)
                path.quadraticBezierTo(p1.x, p1.y, p1.x, p1.y)
            }
        }

        LineInterpolation.Cubic -> {
            if (points.size < 3) {
                for (i in 1 until points.size) path.lineTo(points[i].x, points[i].y)
                return path
            }

            val ext = listOf(points.first()) + points + listOf(points.last())

            for (i in 1 until ext.size - 2) {
                val p0 = ext[i - 1]
                val p1 = ext[i]
                val p2 = ext[i + 1]
                val p3 = ext[i + 2]

                val cp1 = Offset(
                    p1.x + (p2.x - p0.x) / 6f,
                    p1.y + (p2.y - p0.y) / 6f,
                )
                val cp2 = Offset(
                    p2.x - (p3.x - p1.x) / 6f,
                    p2.y - (p3.y - p1.y) / 6f,
                )
                path.cubicTo(
                    cp1.x, cp1.y,
                    cp2.x, cp2.y,
                    p2.x, p2.y
                )
            }
        }
    }
    return path
}

private fun appendInterpolatedPath(
    path: Path,
    points: List<Offset>,
    interpolation: LineInterpolation
) {
    if (points.isEmpty()) return

    when (interpolation) {
        LineInterpolation.Linear -> {
            for (i in 1 until points.size) path.lineTo(points[i].x, points[i].y)
        }
        LineInterpolation.Step -> {
            for (i in 1 until points.size) {
                val prev = points[i - 1]; val cur = points[i]
                path.lineTo(cur.x, prev.y)
                path.lineTo(cur.x, cur.y)
            }
        }
        LineInterpolation.Quadratic -> {
            for (i in 1 until points.size) {
                val p0 = points[i - 1]; val p1 = points[i]
                val mid = Offset((p0.x + p1.x) / 2f, (p0.y + p1.y) / 2f)
                path.quadraticTo(p0.x, p0.y, mid.x, mid.y)
                path.quadraticTo(p1.x, p1.y, p1.x, p1.y)
            }
        }
        LineInterpolation.Cubic -> {
            if (points.size < 3) {
                for (i in 1 until points.size) path.lineTo(points[i].x, points[i].y)
                return
            }
            val ext = listOf(points.first()) + points + listOf(points.last())
            for (i in 1 until ext.size - 2) {
                val p0 = ext[i - 1]; val p1 = ext[i]; val p2 = ext[i + 1]; val p3 = ext[i + 2]
                val cp1x = p1.x + (p2.x - p0.x) / 6f
                val cp1y = p1.y + (p2.y - p0.y) / 6f
                val cp2x = p2.x - (p3.x - p1.x) / 6f
                val cp2y = p2.y - (p3.y - p1.y) / 6f
                path.cubicTo(cp1x, cp1y, cp2x, cp2y, p2.x, p2.y)
            }
        }
    }
}

private fun buildAreaPath(
    topPoints: List<Offset>,
    basePoints: List<Offset>,
    interpolation: LineInterpolation
): Path {
    val path = Path()
    if (topPoints.isEmpty() || basePoints.isEmpty()) return path
    require(topPoints.size == basePoints.size) { "Top and base must have same point count" }

    path.moveTo(topPoints.first().x, topPoints.first().y)

    appendInterpolatedPath(path, topPoints, interpolation)

    val lastBase = basePoints.last()
    path.lineTo(lastBase.x, lastBase.y)

    appendInterpolatedPath(path, basePoints.asReversed(), interpolation)

    path.close()
    return path
}
