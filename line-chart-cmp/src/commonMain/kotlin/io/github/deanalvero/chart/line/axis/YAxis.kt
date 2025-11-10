package io.github.deanalvero.chart.line.axis

import io.github.deanalvero.chart.line.formatter.AxisValueFormatter
import io.github.deanalvero.chart.line.formatter.DefaultFormatter

data class YAxis(
    val position: YAxisPosition = YAxisPosition.Left,
    val style: AxisStyle = AxisStyle(),
    val labelStyle: LabelStyle = LabelStyle(),
    val labelCount: Int = 5,
    val valueFormatter: AxisValueFormatter = DefaultFormatter,
    val min: AxisValue = AxisValue.Auto,
    val max: AxisValue = AxisValue.Auto
)
