package com.nisarg.voyageai.presentation.navigation

sealed class NavRoutes(val route : String) {
    object Search : NavRoutes("Search")
    object Detail : NavRoutes("Detail")
    object Planner : NavRoutes("Planner")
}

