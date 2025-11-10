package io.github.deanalvero.chart.line.grid

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class GridLineStyle(
    val color: Color = Color.LightGray,
    val strokeWidth: Dp = 0.5.dp,
    val pathEffect: PathEffect? = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
)
