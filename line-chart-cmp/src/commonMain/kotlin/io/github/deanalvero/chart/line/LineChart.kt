package io.github.deanalvero.chart.line

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import io.github.deanalvero.chart.line.axis.XAxis
import io.github.deanalvero.chart.line.axis.XAxisPosition
import io.github.deanalvero.chart.line.axis.YAxis
import io.github.deanalvero.chart.line.axis.YAxisPosition
import io.github.deanalvero.chart.line.grid.GridLines
import io.github.deanalvero.chart.line.marker.Marker
import io.github.deanalvero.chart.line.data.LineData
import io.github.deanalvero.chart.line.selection.SelectionInfo
import io.github.deanalvero.chart.line.selection.SelectionState
import io.github.deanalvero.chart.line.transformer.DataTransformer
import io.github.deanalvero.chart.line.utils.calculateBounds
import io.github.deanalvero.chart.line.utils.calculateMarkerOffset
import io.github.deanalvero.chart.line.utils.drawGridLines
import io.github.deanalvero.chart.line.utils.drawLineSeries
import io.github.deanalvero.chart.line.utils.drawXAxis
import io.github.deanalvero.chart.line.utils.drawYAxis
import io.github.deanalvero.chart.line.utils.findClosest
import kotlin.collections.set

@Composable
fun LineChart(
    modifier: Modifier = Modifier,
    data: List<LineData>,
    xAxis: XAxis? = XAxis(),
    yAxis: YAxis? = YAxis(),
    gridLines: GridLines = GridLines(),
    marker: Marker? = null,
    chartPadding: Dp = 16.dp,
    onSelectionChange: (SelectionInfo) -> Unit = {}
) {
    if (data.isEmpty() || data.all { it.points.isEmpty() }) {
        Box(modifier)
        return
    }

    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()
    val bounds = remember(data, xAxis, yAxis) {
        calculateBounds(data, xAxis, yAxis)
    }
    var selectionState by remember { mutableStateOf<SelectionState?>(null) }
    val markerSizes = remember { mutableStateMapOf<Int, IntSize>() }

    BoxWithConstraints(modifier) {
        val paddingPx = with(density) { chartPadding.toPx() }
        val leftPadding = if (yAxis != null && yAxis.labelPosition == YAxisPosition.Left) paddingPx * 3 else paddingPx
        val rightPadding = if (yAxis != null && yAxis.labelPosition == YAxisPosition.Right) paddingPx * 3 else paddingPx
        val topPadding = if (xAxis != null && xAxis.labelPosition == XAxisPosition.Top) paddingPx * 2 else paddingPx
        val bottomPadding = if (xAxis != null && xAxis.labelPosition == XAxisPosition.Bottom) paddingPx * 2 else paddingPx

        val viewport = Rect(
            left = leftPadding,
            top = topPadding,
            right = constraints.maxWidth.toFloat() - rightPadding,
            bottom = constraints.maxHeight.toFloat() - bottomPadding
        )

        val transformer = remember(bounds, viewport) {
            DataTransformer(bounds, viewport)
        }

        Canvas(
            Modifier
                .fillMaxSize()
                .then(
                    if (marker != null) {
                        Modifier.pointerInput(data, transformer) {
                            detectDragGestures(
                                onDragStart = { offset ->
                                    val p = transformer.offsetToData(offset)
                                    val closest = findClosest(data, p, transformer)
                                    if (closest.isNotEmpty()) {
                                        selectionState = SelectionState(closest, offset)
                                        onSelectionChange(
                                            SelectionInfo(true, closest, offset)
                                        )
                                    }
                                },
                                onDrag = { change, _ ->
                                    val p = transformer.offsetToData(change.position)
                                    val closest = findClosest(data, p, transformer)
                                    if (closest.isNotEmpty()) {
                                        selectionState = SelectionState(closest, change.position)
                                        onSelectionChange(
                                            SelectionInfo(true, closest, change.position)
                                        )
                                    }
                                    change.consume()
                                },
                                onDragEnd = {
                                    selectionState = null
                                    onSelectionChange(
                                        SelectionInfo(false, emptyList(), null)
                                    )
                                },
                                onDragCancel = {
                                    selectionState = null
                                    onSelectionChange(
                                        SelectionInfo(false, emptyList(), null)
                                    )
                                }
                            )
                        }
                    } else {
                        Modifier
                    }
                )
        ) {
            drawGridLines(gridLines, viewport, bounds, xAxis, yAxis, transformer)
            drawXAxis(xAxis, viewport, bounds, transformer, textMeasurer)
            drawYAxis(yAxis, viewport, bounds, transformer, textMeasurer)
            data.forEach { drawLineSeries(it, transformer) }

            selectionState?.let { state ->
                if (state.markerData.isNotEmpty() && marker != null) {
                    val first = state.markerData.first().offset

                    marker.verticalLineStyle.let {
                        drawLine(
                            color = it.color,
                            start = Offset(first.x, viewport.top),
                            end = Offset(first.x, viewport.bottom),
                            strokeWidth = it.strokeWidth.toPx(),
                            pathEffect = it.pathEffect
                        )
                    }

                    marker.horizontalLineStyle?.let { style ->
                        state.markerData.forEach { markerData ->
                            drawLine(
                                color = style.color,
                                start = Offset(viewport.left, markerData.offset.y),
                                end = Offset(viewport.right, markerData.offset.y),
                                strokeWidth = style.strokeWidth.toPx(),
                                pathEffect = style.pathEffect
                            )
                        }
                    }
                }
            }
        }

        selectionState?.let { state ->
            if (marker != null && state.markerData.isNotEmpty()) {
                state.markerData.forEachIndexed { index, markerData ->
                    val pointOffset = markerData.offset
                    val markerSize = markerSizes[index] ?: IntSize.Zero
                    val offsetPx = with(density) { marker.offsetFromPoint.toPx() }

                    Box(
                        modifier = Modifier
                            .onGloballyPositioned { coords ->
                                markerSizes[index] = coords.size
                            }
                            .offset {
                                calculateMarkerOffset(
                                    pointOffset = pointOffset,
                                    markerSize = markerSize,
                                    offsetFromPoint = offsetPx,
                                    containerWidth = constraints.maxWidth,
                                    containerHeight = constraints.maxHeight,
                                    verticalOffset = index * (markerSize.height + offsetPx.toInt())
                                )
                            }
                    ) {
                        marker.content(markerData)
                    }
                }
            }
        }
    }
}
