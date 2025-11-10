package io.github.deanalvero.chart.line.axis

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AxisStyle(
    val color: Color = Color.Gray,
    val strokeWidth: Dp = 1.dp,
    val pathEffect: PathEffect? = null
)
