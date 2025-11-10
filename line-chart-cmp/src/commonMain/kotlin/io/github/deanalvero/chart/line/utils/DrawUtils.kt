package io.github.deanalvero.chart.line.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.dp
import io.github.deanalvero.chart.line.axis.XAxis
import io.github.deanalvero.chart.line.axis.XAxisPosition
import io.github.deanalvero.chart.line.axis.YAxis
import io.github.deanalvero.chart.line.axis.YAxisPosition
import io.github.deanalvero.chart.line.grid.GridLines
import io.github.deanalvero.chart.line.data.LineData
import io.github.deanalvero.chart.line.point.Point
import io.github.deanalvero.chart.line.point.PointStyle
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

    val y = if (axis.position == XAxisPosition.Bottom) viewport.bottom else viewport.top
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
        val labelY = if (axis.position == XAxisPosition.Bottom)
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

    val x = if (axis.position == YAxisPosition.Left) viewport.left else viewport.right
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
        val labelX = if (axis.position == YAxisPosition.Left)
            viewport.left - text.size.width - 6.dp.toPx()
        else viewport.right + 6.dp.toPx()
        drawText(text, topLeft = Offset(labelX, posY))
    }
}

fun DrawScope.drawLineSeries(line: LineData, transformer: DataTransformer) {
    for (i in 0 until line.points.lastIndex) {
        val start = transformer.dataToOffset(line.points[i])
        val end = transformer.dataToOffset(line.points[i + 1])
        val style = line.segmentOverrides.getOrNull(i) ?: line.defaultSegmentStyle
        drawLine(
            color = style.color,
            start = start,
            end = end,
            strokeWidth = style.strokeWidth.toPx(),
            pathEffect = style.pathEffect
        )
    }

    line.points.forEach { point ->
        point.style?.let { style ->
            style.drawer.run { drawPoint(transformer.dataToOffset(point), style) }
        }
    }
}
