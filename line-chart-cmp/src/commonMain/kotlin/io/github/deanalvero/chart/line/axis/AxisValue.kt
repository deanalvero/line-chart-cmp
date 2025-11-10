package io.github.deanalvero.chart.line.axis

sealed class AxisValue {
    data object Auto : AxisValue()
    data class Fixed(val value: Float) : AxisValue()
}
