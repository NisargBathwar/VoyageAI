package com.nisarg.voyageai.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nisarg.voyageai.presentation.details.DetailScreen
import com.nisarg.voyageai.presentation.details.DetailViewModel
import com.nisarg.voyageai.presentation.planscreen.PlannerScreen
import com.nisarg.voyageai.presentation.planscreen.PlannerViewModel
import com.nisarg.voyageai.presentation.search.SearchScreen
import com.nisarg.voyageai.presentation.search.SearchViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NavGraph(navController : NavHostController) {

    val vm : SearchViewModel = koinViewModel()
    val dvm : DetailViewModel = koinViewModel()
    val pvm : PlannerViewModel  = koinViewModel()
    NavHost(
        navController = navController ,
        startDestination = NavRoutes.Search.route
    ){
        composable(NavRoutes.Search.route){
            SearchScreen(
                vm = vm ,
                navController
            )
        }

        composable(NavRoutes.Detail.route) {
            DetailScreen(
                vm = vm,
                dvm,
                navController = navController
            )
        }

        composable(NavRoutes.Planner.route) {
            PlannerScreen(
                dvm  ,
                pvm ,
                navController
            )
        }
    }
}