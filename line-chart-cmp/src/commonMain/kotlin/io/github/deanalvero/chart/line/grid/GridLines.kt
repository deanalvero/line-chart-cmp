package io.github.deanalvero.chart.line.grid

data class GridLines(
    val showHorizontal: Boolean = true,
    val showVertical: Boolean = true,
    val horizontalStyle: GridLineStyle = GridLineStyle(),
    val verticalStyle: GridLineStyle = GridLineStyle()
)
