package io.github.deanalvero.chart.line.demoapp.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.deanalvero.chart.line.LineChart
import io.github.deanalvero.chart.line.data.LineData
import io.github.deanalvero.chart.line.point.Point
import io.github.deanalvero.chart.line.segment.SegmentStyle
import kotlin.math.floor

@Composable
fun StockListChartScreen() {
    val stocks = listOf(
        "NVDA" to listOf(188.15f, 187.80f, 189.00f, 188.50f, 188.90f, 187.70f, 189.20f, 188.60f),
        "AAPL" to listOf(268.47f, 269.10f, 267.80f, 269.50f, 268.90f, 269.00f, 268.60f, 269.20f),
        "META" to listOf(621.71f, 623.00f, 620.50f, 622.10f, 624.00f, 623.20f, 621.80f, 622.50f),
        "MSFT" to listOf(496.82f, 498.00f, 495.50f, 497.20f, 499.00f, 496.50f, 497.80f, 494.20f),
        "AMZN" to listOf(244.41f, 245.00f, 243.80f, 244.50f, 245.20f, 243.90f, 244.70f, 244.10f),
        "GOOGL" to listOf(278.83f, 279.50f, 277.90f, 278.20f, 279.00f, 278.60f, 278.10f, 279.20f),
        "TSLA" to listOf(429.52f, 430.00f, 428.50f, 431.00f, 429.80f, 430.50f, 429.20f, 426.10f),
        "INTC" to listOf(38.13f, 38.50f, 37.90f, 38.20f, 38.40f, 37.80f, 38.10f, 38.30f),
        "IBM" to listOf(306.38f, 307.00f, 305.50f, 306.50f, 307.20f, 306.10f, 305.80f, 306.70f),
        "ORCL" to listOf(239.26f, 240.00f, 238.80f, 239.50f, 240.20f, 239.10f, 238.90f, 239.70f),
        "AVGO" to listOf(349.43f, 350.00f, 348.80f, 349.50f, 350.20f, 349.10f, 348.90f, 332.70f),
        "TXN" to listOf(160.55f, 161.00f, 159.80f, 160.50f, 161.20f, 160.10f, 159.90f, 160.70f),
        "AMD" to listOf(233.54f, 234.00f, 232.80f, 233.50f, 234.20f, 233.10f, 232.90f, 233.70f),
        "QCOM" to listOf(170.89f, 171.50f, 170.20f, 171.00f, 170.60f, 171.10f, 170.40f, 169.20f)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(stocks) { (ticker, prices) ->
            val data = prices.mapIndexed { i, price ->
                Point(x = i.toFloat(), y = price, style = null)
            }

            val first = data.first().y
            val last  = data.last().y
            val change = last - first
            val changePct = (change / first) * 100f

            Card(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier.padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(ticker, style = MaterialTheme.typography.titleMedium)

                        LineChart(
                            modifier = Modifier
                                .width(120.dp)
                                .height(60.dp),
                            data = listOf(
                                LineData(
                                    points = data,
                                    label = ticker,
                                    defaultSegmentStyle = SegmentStyle(
                                        color = if (change >= 0) Color(0xFF4CAF50) else Color(0xFFF44336),
                                        strokeWidth = 2.dp
                                    )
                                )
                            ),
                            xAxis = null,
                            yAxis = null,
                            marker = null
                        )

                        Column(
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = last.toTwoDecimalsString(),
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "${if (change >= 0) "+" else ""}${change.toTwoDecimalsString()} " +
                                        "(${if (change >= 0) "+" else ""}${changePct.toTwoDecimalsString()}%)",
                                color = if (change >= 0) Color(0xFF4CAF50) else Color(0xFFF44336),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun Float.toTwoDecimalsString(): String {
    val factor = 100
    val truncated = floor(this * factor) / factor
    return truncated.toString()
}
