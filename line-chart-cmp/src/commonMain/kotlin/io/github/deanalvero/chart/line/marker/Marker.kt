package io.github.deanalvero.chart.line.marker

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Marker(
    val content: @Composable (MarkerData) -> Unit,
    val verticalLineStyle: CrosshairStyle = CrosshairStyle(),
    val horizontalLineStyle: CrosshairStyle? = null,
    val offsetFromPoint: Dp = 8.dp
)
