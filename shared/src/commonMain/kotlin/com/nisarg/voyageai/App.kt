package com.nisarg.voyageai

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.nisarg.voyageai.presentation.navigation.NavGraph
import com.nisarg.voyageai.presentation.search.SearchScreen
import com.nisarg.voyageai.presentation.search.SearchViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    MaterialTheme(colorScheme = lightColorScheme()) {
        val navController = rememberNavController()
        NavGraph(navController)
    }
}

