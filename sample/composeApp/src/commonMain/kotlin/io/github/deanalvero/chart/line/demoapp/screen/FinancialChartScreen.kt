package io.github.deanalvero.chart.line.demoapp.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.deanalvero.chart.line.LineChart
import io.github.deanalvero.chart.line.axis.XAxis
import io.github.deanalvero.chart.line.axis.YAxis
import io.github.deanalvero.chart.line.data.LineData
import io.github.deanalvero.chart.line.marker.CrosshairStyle
import io.github.deanalvero.chart.line.marker.Marker
import io.github.deanalvero.chart.line.point.Point
import io.github.deanalvero.chart.line.segment.SegmentStyle
import kotlin.math.round

@Composable
fun FinancialChartScreen() {
    val stockData = remember {
        listOf(
            Point(0f, 245.27f),
            Point(1f, 247.66f),
            Point(2f, 249.34f),
            Point(3f, 247.77f),
            Point(4f, 247.45f),
            Point(5f, 252.29f),
            Point(6f, 258.45f),
            Point(7f, 259.58f),
            Point(8f, 262.82f),
            Point(9f, 268.81f),
            Point(10f, 269.00f),
            Point(11f, 269.70f),
            Point(12f, 271.40f),
            Point(13f, 270.37f),
            Point(14f, 270.04f),
            Point(15f, 270.14f),
            Point(16f, 269.05f),
            Point(17f, 270.37f),
            Point(18f, 271.00f),
            Point(19f, 272.15f),
            Point(20f, 273.50f),
            Point(21f, 274.80f),
            Point(22f, 275.20f),
            Point(23f, 276.30f),
            Point(24f, 275.90f),
            Point(25f, 276.10f),
            Point(26f, 275.50f),
            Point(27f, 276.00f),
            Point(28f, 277.10f),
            Point(29f, 277.32f),
            Point(30f, 277.50f)
        )
    }

    val lineData = LineData(
        points = stockData,
        label = "AAPL",
        segmentStyle = SegmentStyle(
            color = if (stockData.last().y > stockData.first().y) Color(0xFF4CAF50) else Color(0xFFF44336),
            strokeWidth = 3.dp
        )
    )

    val change = stockData.last().y - stockData.first().y
    val changePercent = round((change / stockData.first().y) * 10000) / 100

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Financial Chart",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            "Stock price chart with custom formatting.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("AAPL", style = MaterialTheme.typography.titleLarge, color = Color.White)
                    Text("Apple Inc.", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "${stockData.last().y}",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                    Text(
                        text = "${if (change >= 0) "+" else ""}${round(change * 100) / 100} (${if (change >= 0) "+" else ""}$changePercent %)",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (change >= 0) Color(0xFF4CAF50) else Color(0xFFF44336)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp),
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
                    valueFormatter = { "D${it.toInt()}" }
                ),
                yAxis = YAxis(
                    labelCount = 6,
                    valueFormatter = { "$${it.toInt()}" }
                ),
                marker = Marker(
                    content = { markerData ->
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF2C2C2C)
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "Day ${markerData.point.x.toInt()}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.Gray
                                )
                                Text(
                                    text = "${markerData.point.y}",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    },
                    verticalLineStyle = CrosshairStyle(
                        color = Color.White.copy(alpha = 0.3f),
                        strokeWidth = 1.dp
                    ),
                    horizontalLineStyle = CrosshairStyle(
                        color = Color.White.copy(alpha = 0.3f),
                        strokeWidth = 1.dp
                    )
                )
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("High", color = Color.Gray, style = MaterialTheme.typography.labelSmall)
                    Text(
                        "${stockData.maxOf { it.y }}",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Low", color = Color.Gray, style = MaterialTheme.typography.labelSmall)
                    Text(
                        "${stockData.minOf { it.y }}",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Range", color = Color.Gray, style = MaterialTheme.typography.labelSmall)
                    Text(
                        "${stockData.maxOf { it.y } - stockData.minOf { it.y }}",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
