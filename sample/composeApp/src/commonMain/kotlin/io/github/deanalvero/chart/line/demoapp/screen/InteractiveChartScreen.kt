package io.github.deanalvero.chart.line.demoapp.screen

import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
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
import io.github.deanalvero.chart.line.selection.SelectionInfo
import kotlin.math.sin

@Composable
fun InteractiveChartScreen() {
    val points = remember {
        (0..20).map { x ->
            Point(x.toFloat(), 50f + 20f * sin(x * 0.3).toFloat())
        }
    }

    val lineData = LineData(
        points = points,
        label = "Temperature",
        defaultSegmentStyle = SegmentStyle(
            color = Color(0xFFFF5722),
            strokeWidth = 3.dp
        )
    )

    var selectionInfo by remember { mutableStateOf<SelectionInfo?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Interactive Chart",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            "Drag on the chart to see data markers.",
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
                    valueFormatter = { "${it.toInt()}h" }
                ),
                yAxis = YAxis(
                    labelCount = 5,
                    valueFormatter = { "${it.toInt()}°C" }
                ),
                marker = Marker(
                    content = { markerData ->
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF2C2C2C)
                            ),
                            elevation = CardDefaults.cardElevation(8.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Text(
                                    markerData.line.label,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.Gray
                                )
                                Text(
                                    "Hour: ${markerData.point.x.toInt()}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.White
                                )
                                Text(
                                    "Temp: ${markerData.point.y.toInt()}°C",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color(0xFFFF5722),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    },
                    verticalLineStyle = CrosshairStyle(
                        color = Color(0xFFFF5722).copy(alpha = 0.5f),
                        strokeWidth = 2.dp
                    ),
                    horizontalLineStyle = CrosshairStyle(
                        color = Color(0xFFFF5722).copy(alpha = 0.3f),
                        strokeWidth = 1.dp,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 5f))
                    )
                ),
                onSelectionChange = { selectionInfo = it }
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
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Selection Status",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (selectionInfo?.isSelected == true) {
                    val data = selectionInfo?.selectedData?.firstOrNull()
                    Text(
                        "Selected: Hour ${data?.point?.x?.toInt()}, Temp ${data?.point?.y?.toInt()}°C",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFFF5722)
                    )
                } else {
                    Text(
                        "Drag on chart to select data points",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}
