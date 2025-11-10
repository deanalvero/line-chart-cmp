package io.github.deanalvero.chart.line.axis

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

enum class XAxisPosition { Top, Bottom }
data class LabelStyle(
    val textStyle: TextStyle = TextStyle.Companion.Default.copy(fontSize = 10.sp),
    val color: Color = Color.Companion.Gray,
    val alignment: TextAlign = TextAlign.Companion.Center
)