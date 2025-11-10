package io.github.deanalvero.chart.line.formatter

object DefaultFormatter : AxisValueFormatter {
    override fun format(value: Float): String = value.toString()
}
