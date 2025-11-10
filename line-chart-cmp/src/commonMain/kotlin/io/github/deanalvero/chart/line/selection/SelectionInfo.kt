package io.github.deanalvero.chart.line.selection

import androidx.compose.ui.geometry.Offset
import io.github.deanalvero.chart.line.marker.MarkerData

data class SelectionInfo(
    val isSelected: Boolean,
    val selectedData: List<MarkerData>,
    val interactionOffset: Offset?
)
