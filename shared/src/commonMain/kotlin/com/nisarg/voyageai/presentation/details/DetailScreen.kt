package com.nisarg.voyageai.presentation.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.nisarg.voyageai.domain.model.Location
import com.nisarg.voyageai.presentation.navigation.NavRoutes
import com.nisarg.voyageai.presentation.search.SearchViewModel
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(
    vm: SearchViewModel,
    dvm: DetailViewModel,
    navController: NavController
) {

    val state by vm.uiState.collectAsStateWithLifecycle()
    val detailState by dvm.uiState.collectAsStateWithLifecycle()

    val location = state.selectedLocation ?: return

    LaunchedEffect(location.city){
        dvm.onAction(
            DetailActions.DetailScreen(location)
        )
    }

    val scope = rememberCoroutineScope()

    Scaffold(

        topBar = {

            TopAppBar(

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.80f)
                ),

                windowInsets = WindowInsets.safeDrawing,

                title = {

                    Text(
                        text = "Destination Details",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                },

                navigationIcon = {

                    IconButton(

                        onClick = {
                            vm.clearState()
                           dvm.clearState()
                            dvm.clearSelectedLocation()
                            navController.popBackStack()
                        }

                    ) {
                        Text(
                            text = "‹",
                            fontSize = 34.sp,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier.offset(y = (-2).dp)
                        )
                    }
                }
            )
        }
    ) { padding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(horizontal = 20.dp)

        ) {

            Spacer(Modifier.height(12.dp))

            Card(

                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)

            ) {

                Box {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalPlatformContext.current)
                            .data(detailState.image)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(

                                Brush.verticalGradient(

                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.65f)
                                    )
                                )
                            )
                    )

                    Column(

                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)

                    ) {

                        Text(
                            text = location.city,
                            color = Color.White,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = location.country,
                            color = Color.White
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            Text(

                text = "About",

                style = MaterialTheme.typography.titleMedium,

                fontWeight = FontWeight.Bold

            )

            Spacer(Modifier.height(8.dp))

            Text(

                text = "${location.city} is a beautiful destination in ${location.country}. Explore famous landmarks, local food, culture, and hidden gems with an AI-generated itinerary tailored to your interests.",

                style = MaterialTheme.typography.bodyLarge,

                color = MaterialTheme.colorScheme.onSurfaceVariant

            )

            Spacer(Modifier.height(24.dp))

            Text(

                text = "Coordinates",

                style = MaterialTheme.typography.titleMedium,

                fontWeight = FontWeight.Bold

            )

            Spacer(Modifier.height(10.dp))

            Card(

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(16.dp)

            ) {

                Column(
                    modifier = Modifier.padding(18.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(location.lat.toString())
                    }

                    Spacer(Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(location.lng.toString())
                    }
                }
            }

            Spacer(Modifier.height(30.dp))

            Button(
                onClick = {
                    navController.navigate(NavRoutes.Planner.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Generate AI Trip ✨",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(Modifier.height(24.dp))

            if (detailState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            detailState.error?.let {
                Spacer(Modifier.height(16.dp))
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFEBEE)
                    )
                ) {
                    Text(
                        text = it,
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            Spacer(Modifier.height(20.dp))
        }
    }
}