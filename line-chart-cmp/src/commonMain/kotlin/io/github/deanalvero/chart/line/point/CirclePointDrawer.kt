package io.github.deanalvero.chart.line.point

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope

object CirclePointDrawer : PointDrawer {
    override fun DrawScope.drawPoint(center: Offset, style: PointStyle) {
        drawCircle(style.color, radius = style.size.toPx() / 2, center)
    }
}
