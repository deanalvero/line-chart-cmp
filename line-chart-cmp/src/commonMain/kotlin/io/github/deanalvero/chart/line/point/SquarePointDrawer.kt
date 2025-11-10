package io.github.deanalvero.chart.line.point

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope

object SquarePointDrawer : PointDrawer {
    override fun DrawScope.drawPoint(center: Offset, style: PointStyle) {
        val size = style.size.toPx()
        drawRect(
            style.color,
            topLeft = Offset(center.x - size / 2, center.y - size / 2),
            size = Size(size, size)
        )
    }
}
