package io.github.deanalvero.chart.line.selection

import androidx.compose.ui.geometry.Offset
import io.github.deanalvero.chart.line.marker.MarkerData

data class SelectionState(
    val markerData: List<MarkerData>,
    val interactionOffset: Offset
)
