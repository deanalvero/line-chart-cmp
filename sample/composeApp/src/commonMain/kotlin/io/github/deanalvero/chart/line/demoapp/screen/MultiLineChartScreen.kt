package io.github.deanalvero.chart.line.demoapp.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.deanalvero.chart.line.LineChart
import io.github.deanalvero.chart.line.axis.AxisValue
import io.github.deanalvero.chart.line.axis.XAxis
import io.github.deanalvero.chart.line.axis.YAxis
import io.github.deanalvero.chart.line.data.LineData
import io.github.deanalvero.chart.line.grid.GridLines
import io.github.deanalvero.chart.line.point.Point
import io.github.deanalvero.chart.line.segment.SegmentStyle
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun MultiLineChartScreen() {
    val series1 = remember {
        (0..10).map { x ->
            Point(x.toFloat(), 20f + 10f * sin(x * 0.5).toFloat())
        }
    }

    val series2 = remember {
        (0..10).map { x ->
            Point(x.toFloat(), 30f + 8f * cos(x * 0.5).toFloat())
        }
    }

    val series3 = remember {
        (0..10).map { x ->
            Point(x.toFloat(), 25f + 5f * sin(x * 0.7 + 1).toFloat())
        }
    }

    val lineData = listOf(
        LineData(
            points = series1,
            label = "Product A",
            segmentStyle = SegmentStyle(
                color = Color(0xFF2196F3),
                strokeWidth = 3.dp
            )
        ),
        LineData(
            points = series2,
            label = "Product B",
            segmentStyle = SegmentStyle(
                color = Color(0xFF4CAF50),
                strokeWidth = 3.dp
            )
        ),
        LineData(
            points = series3,
            label = "Product C",
            segmentStyle = SegmentStyle(
                color = Color(0xFFFF9800),
                strokeWidth = 3.dp
            )
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Multi-Line Chart",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            "Comparing multiple data series.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            LineChart(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                data = lineData,
                xAxis = XAxis(
                    labelCount = 6,
                    valueFormatter = { "M${it.toInt()}" }
                ),
                yAxis = YAxis(
                    labelCount = 5,
                    min = AxisValue.Fixed(10f),
                    max = AxisValue.Fixed(45f)
                ),
                gridLines = GridLines(
                    showHorizontal = false
                )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            lineData.forEach { line ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(
                                line.segmentStyle.color,
                                RoundedCornerShape(2.dp)
                            )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(line.label, color = Color.White, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
