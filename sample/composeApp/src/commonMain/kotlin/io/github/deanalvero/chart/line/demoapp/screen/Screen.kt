package io.github.deanalvero.chart.line.demoapp.screen

sealed class Screen(val route: String, val title: String) {
    data object Home : Screen("home", "Chart Examples")
    data object BasicChart : Screen("basic", "Basic Line Chart")
    data object MultiLine : Screen("multi", "Multi-Line Chart")
    data object StyledChart : Screen("styled_multi", "Styled Multi-Line Chart")
    data object InteractiveChart : Screen("interactive", "Interactive Markers")
    data object FinancialChart : Screen("financial", "Financial Chart")
    data object StockListChart : Screen("stock_list", "Stock List Chart")
}