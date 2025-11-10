package io.github.deanalvero.chart.line.point

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope

fun interface PointDrawer {
    fun DrawScope.drawPoint(center: Offset, style: PointStyle)
}
