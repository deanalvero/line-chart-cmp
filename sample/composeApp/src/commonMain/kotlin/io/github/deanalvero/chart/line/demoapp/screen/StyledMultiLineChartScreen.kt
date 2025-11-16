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
import io.github.deanalvero.chart.line.data.LineInterpolation
import io.github.deanalvero.chart.line.grid.GridLineStyle
import io.github.deanalvero.chart.line.grid.GridLines
import io.github.deanalvero.chart.line.point.CirclePointDrawer
import io.github.deanalvero.chart.line.point.DiamondPointDrawer
import io.github.deanalvero.chart.line.point.Point
import io.github.deanalvero.chart.line.point.PointStyle
import io.github.deanalvero.chart.line.point.SquarePointDrawer
import io.github.deanalvero.chart.line.segment.SegmentStyle

@Composable
fun StyledMultiLineChartScreen() {
    val lineA = remember {
        LineData(
            points = listOf(
                Point(0f, 30f, PointStyle(Color(0xFFE91E63), 10.dp, CirclePointDrawer)),
                Point(1f, 40f, PointStyle(Color(0xFFE91E63), 10.dp, CirclePointDrawer)),
                Point(2f, 38f, PointStyle(Color(0xFFE91E63), 10.dp, CirclePointDrawer)),
                Point(3f, 50f, PointStyle(Color(0xFFE91E63), 10.dp, CirclePointDrawer)),
                Point(4f, 47f, PointStyle(Color(0xFFE91E63), 10.dp, CirclePointDrawer)),
            ),
            label = "Linear Dashed",
            segmentStyle = SegmentStyle(
                color = Color(0xFFE91E63),
                strokeWidth = 3.dp,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
            ),
            interpolation = LineInterpolation.Linear,
            fillColor = Color(0x33FF9800)
        )
    }

    val lineB = remember {
        LineData(
            points = listOf(
                Point(0f, 45f, PointStyle(Color(0xFF00BCD4), 10.dp, SquarePointDrawer)),
                Point(1f, 55f, PointStyle(Color(0xFF00BCD4), 10.dp, SquarePointDrawer)),
                Point(2f, 52f, PointStyle(Color(0xFF00BCD4), 10.dp, SquarePointDrawer)),
                Point(3f, 70f, PointStyle(Color(0xFF00BCD4), 10.dp, SquarePointDrawer)),
                Point(4f, 67f, PointStyle(Color(0xFF00BCD4), 10.dp, SquarePointDrawer)),
            ),
            label = "Quadratic Dotted",
            segmentStyle = SegmentStyle(
                color = Color(0xFF00BCD4),
                strokeWidth = 4.dp,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(4f, 12f))
            ),
            interpolation = LineInterpolation.Quadratic
        )
    }

    val lineC = remember {
        LineData(
            points = listOf(
                Point(0f, 60f, PointStyle(Color(0xFFFF9800), 12.dp, DiamondPointDrawer)),
                Point(1f, 70f, PointStyle(Color(0xFFFF9800), 12.dp, DiamondPointDrawer)),
                Point(2f, 68f, PointStyle(Color(0xFFFF9800), 12.dp, DiamondPointDrawer)),
                Point(3f, 85f, PointStyle(Color(0xFFFF9800), 12.dp, DiamondPointDrawer)),
                Point(4f, 80f, PointStyle(Color(0xFFFF9800), 12.dp, DiamondPointDrawer)),
            ),
            label = "Cubic Smooth",
            segmentStyle = SegmentStyle(
                color = Color(0xFFFF9800),
                strokeWidth = 5.dp,
                pathEffect = null
            ),
            interpolation = LineInterpolation.Cubic,
            fillColor = Color(0x339900FF)
        )
    }

    val allLines = listOf(lineA, lineB, lineC)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Styled Multi-Line Chart",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            "Different line styles, interpolations and dash effects.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(420.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            LineChart(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                data = allLines,
                xAxis = XAxis(
                    labelCount = 5,
                    style = AxisStyle(Color(0xFF00BCD4), 2.dp)
                ),
                yAxis = YAxis(
                    labelCount = 6,
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

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Legend:",
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        Column(Modifier.fillMaxWidth()) {
            LegendRow("Linear Dashed", Color(0xFFE91E63))
            LegendRow("Quadratic Dotted", Color(0xFF00BCD4))
            LegendRow("Cubic Smooth", Color(0xFFFF9800))
        }
    }
}

@Composable
private fun LegendRow(name: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(name, color = Color.Gray)
        Text("■", color = color)
    }
}
