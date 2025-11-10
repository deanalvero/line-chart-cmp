package io.github.deanalvero.chart.line.formatter

fun interface AxisValueFormatter {
    fun format(value: Float): String
}