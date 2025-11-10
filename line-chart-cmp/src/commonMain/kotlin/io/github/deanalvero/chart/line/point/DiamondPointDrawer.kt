package io.github.deanalvero.chart.line.point

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope

object DiamondPointDrawer : PointDrawer {
    override fun DrawScope.drawPoint(center: Offset, style: PointStyle) {
        val size = style.size.toPx() / 2
        val path = Path().apply {
            moveTo(center.x, center.y - size)
            lineTo(center.x + size, center.y)
            lineTo(center.x, center.y + size)
            lineTo(center.x - size, center.y)
            close()
        }
        drawPath(path, style.color)
    }
}
