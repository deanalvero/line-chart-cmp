package io.github.deanalvero.chart.line.segment

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class SegmentStyle(
    val color: Color = Color.Companion.Blue,
    val strokeWidth: Dp = 2.dp,
    val pathEffect: PathEffect? = null
)