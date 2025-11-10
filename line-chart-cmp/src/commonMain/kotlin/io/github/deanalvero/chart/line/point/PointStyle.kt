package io.github.deanalvero.chart.line.point

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class PointStyle(
    val color: Color = Color.Blue,
    val size: Dp = 8.dp,
    val drawer: PointDrawer = CirclePointDrawer
)
