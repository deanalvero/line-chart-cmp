package io.github.deanalvero.chart.line.demoapp.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.deanalvero.chart.line.LineChart
import io.github.deanalvero.chart.line.axis.XAxis
import io.github.deanalvero.chart.line.axis.YAxis
import io.github.deanalvero.chart.line.data.LineData
import io.github.deanalvero.chart.line.point.Point
import io.github.deanalvero.chart.line.segment.SegmentStyle

@Composable
fun BasicChartScreen() {
    val points = remember {
        listOf(
            Point(0f, 10f),
            Point(1f, 20f),
            Point(2f, 15f),
            Point(3f, 30f),
            Point(4f, 25f),
            Point(5f, 40f),
            Point(6f, 35f)
        )
    }

    val lineData = LineData(
        points = points,
        label = "Sample Data",
        defaultSegmentStyle = SegmentStyle(
            color = Color(0xFF2196F3),
            strokeWidth = 3.dp
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Basic Line Chart",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            "A simple line chart with default settings.",
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
                data = listOf(lineData),
                xAxis = XAxis(
                    labelCount = 7,
                    valueFormatter = { "Day ${it.toInt()}" }
                ),
                yAxis = YAxis(
                    labelCount = 5,
                    valueFormatter = { "${it.toInt()}" }
                )
            )
        }
    }
}
