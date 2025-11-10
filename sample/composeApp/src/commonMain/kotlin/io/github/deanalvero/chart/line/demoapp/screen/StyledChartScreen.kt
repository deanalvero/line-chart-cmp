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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.deanalvero.chart.line.LineChart
import io.github.deanalvero.chart.line.axis.AxisStyle
import io.github.deanalvero.chart.line.axis.XAxis
import io.github.deanalvero.chart.line.axis.YAxis
import io.github.deanalvero.chart.line.data.LineData
import io.github.deanalvero.chart.line.grid.GridLineStyle
import io.github.deanalvero.chart.line.grid.GridLines
import io.github.deanalvero.chart.line.point.CirclePointDrawer
import io.github.deanalvero.chart.line.point.DiamondPointDrawer
import io.github.deanalvero.chart.line.point.Point
import io.github.deanalvero.chart.line.point.PointStyle
import io.github.deanalvero.chart.line.point.SquarePointDrawer
import io.github.deanalvero.chart.line.segment.SegmentStyle

@Composable
fun StyledChartScreen() {
    val points = remember {
        listOf(
            Point(0f, 50f, PointStyle(Color(0xFFE91E63), 12.dp, CirclePointDrawer)),
            Point(1f, 60f, PointStyle(Color(0xFF9C27B0), 12.dp, SquarePointDrawer)),
            Point(2f, 55f, PointStyle(Color(0xFF673AB7), 12.dp, DiamondPointDrawer)),
            Point(3f, 70f, PointStyle(Color(0xFF3F51B5), 12.dp, CirclePointDrawer)),
            Point(4f, 65f, PointStyle(Color(0xFF2196F3), 12.dp, SquarePointDrawer)),
            Point(5f, 80f, PointStyle(Color(0xFF00BCD4), 12.dp, DiamondPointDrawer))
        )
    }

    val segmentStyles = listOf(
        SegmentStyle(Color(0xFFE91E63), 4.dp),
        SegmentStyle(Color(0xFF9C27B0), 4.dp),
        SegmentStyle(Color(0xFF673AB7), 4.dp, PathEffect.dashPathEffect(floatArrayOf(10f, 5f))),
        SegmentStyle(Color(0xFF3F51B5), 4.dp, PathEffect.dashPathEffect(floatArrayOf(10f, 5f))),
        SegmentStyle(Color(0xFF2196F3), 4.dp)
    )

    val lineData = LineData(
        points = points,
        label = "Rainbow Data",
        defaultSegmentStyle = SegmentStyle(Color.Gray),
        segmentOverrides = segmentStyles
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Styled Chart",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            "Custom point styles, colors, and line effects.",
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
                    labelCount = 6,
                    style = AxisStyle(Color(0xFF00BCD4), 2.dp)
                ),
                yAxis = YAxis(
                    labelCount = 5,
                    style = AxisStyle(Color(0xFF00BCD4), 2.dp)
                ),
                gridLines = GridLines(
                    horizontalStyle = GridLineStyle(
                        color = Color(0xFFFF3030),
                        strokeWidth = 2.dp
                    ),
                    verticalStyle = GridLineStyle(
                        color = Color(0xFF30FF30),
                        strokeWidth = 1.dp,
                        pathEffect = null
                    )
                )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Point Styles:", color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf("Circle", "Square", "Diamond").forEach {
                    Text(it, color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
