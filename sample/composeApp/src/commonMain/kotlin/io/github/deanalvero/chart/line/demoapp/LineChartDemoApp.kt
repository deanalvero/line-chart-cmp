package io.github.deanalvero.chart.line.demoapp


import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.github.deanalvero.chart.line.demoapp.screen.BasicChartScreen
import io.github.deanalvero.chart.line.demoapp.screen.FinancialChartScreen
import io.github.deanalvero.chart.line.demoapp.screen.HomeScreen
import io.github.deanalvero.chart.line.demoapp.screen.InteractiveChartScreen
import io.github.deanalvero.chart.line.demoapp.screen.MultiLineChartScreen
import io.github.deanalvero.chart.line.demoapp.screen.Screen
import io.github.deanalvero.chart.line.demoapp.screen.StockListChartScreen
import io.github.deanalvero.chart.line.demoapp.screen.StyledMultiLineChartScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LineChartDemoApp() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }

    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Color(0xFF6200EE),
            secondary = Color(0xFF03DAC6),
            background = Color(0xFF121212),
            surface = Color(0xFF1E1E1E)
        )
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(currentScreen.title) },
                    navigationIcon = {
                        if (currentScreen != Screen.Home) {
                            TextButton(onClick = { currentScreen = Screen.Home }) {
                                Text("◀", color = Color.White)
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )
            }
        ) { padding ->
            AnimatedContent(currentScreen) { screen ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    when (screen) {
                        Screen.Home -> HomeScreen { currentScreen = it }
                        Screen.BasicChart -> BasicChartScreen()
                        Screen.MultiLine -> MultiLineChartScreen()
                        Screen.StyledChart -> StyledMultiLineChartScreen()
                        Screen.InteractiveChart -> InteractiveChartScreen()
                        Screen.FinancialChart -> FinancialChartScreen()
                        Screen.StockListChart -> StockListChartScreen()
                    }
                }
            }
        }
    }
}
