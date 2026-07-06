package com.nisarg.voyageai.presentation.planscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.nisarg.voyageai.domain.model.Budget
import com.nisarg.voyageai.domain.model.Trip
import com.nisarg.voyageai.presentation.details.DetailViewModel

@Composable
fun PlannerScreen(
    dvm: DetailViewModel , pvm : PlannerViewModel , navController: NavController
) {

    val detail by dvm.uiState.collectAsStateWithLifecycle()
    val planner by pvm.uiState.collectAsStateWithLifecycle()


    val budgetOptions = listOf(
        "₹5K - ₹10K",
        "₹10K - ₹20K",
        "₹20K+"
    )

    val styleOptions = listOf(
        "Relaxation",
        "Adventure",
        "Culture",
        "Luxury"
    )


    val interestOptions = listOf(
        "Heritage",
        "Food",
        "Shopping",
        "Nature",
        "Nightlife",
        "Photography"
    )
    Scaffold(
        topBar = {
            TopAppBar(
                windowInsets = WindowInsets.safeDrawing,
                title = {
                    Text(
                        text = "AI Trip Planner",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            pvm.onPlan(PlannerActions.UpdateState(PlannerState()))
                            navController.popBackStack()
                        }
                    ) {
                        Text(
                            text = "‹",   // use this instead of ←
                            fontSize = 34.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onSurface,
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

        Spacer(Modifier.height(8.dp))

        println(detail.image)

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        ) {
            Box {
                AsyncImage(
                    model = detail.image,
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
                        text = "${detail.selectedLocations?.city} Trip",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "${planner.days} Days",
                        color = Color.White
                    )
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        Text(
            text = "Number of Days",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(8.dp))

        val keyboardController = LocalSoftwareKeyboardController.current
        val focusManager = LocalFocusManager.current

        OutlinedTextField(
            value = planner.days,
            onValueChange = { value ->
                if(value.length <= 2 && value.all { it.isDigit() }){
                    pvm.onPlan(
                        PlannerActions.UpdateState(
                            planner.copy(days = value)
                        )
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Days")
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                }
            )
        )

        Spacer(Modifier.height(20.dp))

        Text(
            text = "Travellers",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                "${planner.travellers} Adults",
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                FilledTonalIconButton(
                    onClick = {
                        if (planner.travellers > 1) {
                            pvm.onPlan(
                                PlannerActions.UpdateState(
                                    planner.copy(
                                        travellers = planner.travellers - 1
                                    )
                                )
                            )
                        }
                    }
                ) {
                    Text("-")
                }

                Spacer(Modifier.width(8.dp))

                FilledTonalIconButton(
                    onClick = {
                        pvm.onPlan(
                            PlannerActions.UpdateState(
                                planner.copy(
                                    travellers = planner.travellers + 1
                                )
                            )
                        )
                    }
                ) {
                    Text("+")
                }

            }

        }

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Budget Range",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(10.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            budgetOptions.forEach { budget ->

                FilterChip(
                    selected = planner.budget == budget,
                    onClick = {
                        pvm.onPlan(
                            PlannerActions.UpdateState(
                                planner.copy(
                                    budget = budget
                                )
                            )
                        )
                    },
                    label = {
                        Text(budget)
                    }
                )

            }

        }

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Travel Style",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(10.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            styleOptions.forEach { style ->

                FilterChip(
                    selected = planner.travelStyle == style,
                    onClick = {
                        pvm.onPlan(
                            PlannerActions.UpdateState(
                                planner.copy(
                                    travelStyle = style
                                )
                            )
                        )
                    },
                    label = {
                        Text(style)
                    }
                )

            }

        }

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Interests (Select up to 3)",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(10.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            interestOptions.forEach { interest ->

                val selected = planner.interests.contains(interest)

                FilterChip(
                    selected = selected,
                    onClick = {

                        val updatedInterests = planner.interests.toMutableList()
                        if (selected) {
                            updatedInterests.remove(interest)
                        } else {

                            if (updatedInterests.size < 3) {
                                updatedInterests.add(interest)
                            }

                        }

                        pvm.onPlan(
                            PlannerActions.UpdateState(
                                planner.copy(
                                    interests = updatedInterests
                                )
                            )
                        )

                    },
                    label = {
                        Text(interest)
                    }
                )

            }

        }

        Spacer(Modifier.height(32.dp))


        val canGenerate = planner.days.isNotBlank() &&
                    planner.days.toIntOrNull()?.let { it > 0 } == true &&
                    planner.budget.isNotBlank() &&
                    planner.travelStyle.isNotBlank() &&
                    planner.interests.isNotEmpty()

        if (!canGenerate) {
            Text(
                text = "Please fill all the required fields.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(Modifier.height(8.dp))
        }

        Button(
            onClick = {
                pvm.onPlan(
                    PlannerActions.GenerateTrip(
                        detail.selectedLocations!!.city, detail.selectedLocations!!.country
                    )
                )
            },
            enabled = canGenerate,
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),
            shape = RoundedCornerShape(16.dp)
        ) {

            Text(
                text = "Generate AI Plan ✨",
                style = MaterialTheme.typography.titleMedium
            )

        }

        Spacer(Modifier.height(24.dp))

        if (planner.isLoading) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()

                Spacer(Modifier.height(16.dp))

                Text(
                    "Generating your personalized itinerary...",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    "This usually takes 5–15 seconds.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }

        planner.error?.let {

            Text(
                text = it,
                color = MaterialTheme.colorScheme.error
            )

            Spacer(Modifier.height(16.dp))
        }

        planner.trip?.let { trip ->
            TripItinerary(
                trip = trip,
                image = detail.image
            )
        }
        Spacer(Modifier.height(30.dp))
    }
}
}

@Composable
fun TripItinerary(
    trip : Trip ,
    image: String,
) {

    var selectedDay by remember {
        mutableIntStateOf(0)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {

        Text(
            text = "AI Generated Itinerary",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(20.dp))

        TripBanner(
            image = image,
            title = trip.tripTitle,
            duration = trip.duration
        )

        Spacer(Modifier.height(24.dp))

        DayTabs(
            trip = trip ,
            selectedDay = selectedDay ,
            onDaySelected = {
                selectedDay = it
            }
        )

        Spacer(Modifier.height(24.dp))

        val selectedDay = trip.days[selectedDay]
        
        selectedDay.activities.forEachIndexed { index, activity ->
            TimelineItem(
                time = activity.time ,
                title  = activity.title ,
                description = activity.description ,
                image = image ,
                isLast = index == selectedDay.activities.lastIndex
            )
        }

        Spacer(Modifier.height(32.dp))

        BudgetSection(trip.budget)

        Spacer(Modifier.height(24.dp))

        HotelSection(trip.recommendedHotels)

        Spacer(Modifier.height(24.dp))

        RestaurantSection(trip.recommendedRestaurants)

        Spacer(Modifier.height(24.dp))

        TravelTipsSection(trip.travelTips)

        Spacer(Modifier.height(24.dp))

        ImportantNotesSection(trip.importantNotes)
    }
}


@Composable
fun ImportantNotesSection(
    notes: List<String>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = "⚠ Important Notes",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(12.dp))

            notes.forEach {
                Text("• $it")
                Spacer(Modifier.height(6.dp))
            }
        }
    }
}

@Composable
fun TravelTipsSection(
    tips: List<String>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = "💡 Travel Tips",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(12.dp))

            tips.forEach {
                Text("• $it")
                Spacer(Modifier.height(6.dp))
            }
        }
    }
}

@Composable
fun RestaurantSection(
    restaurants: List<String>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = "🍽 Recommended Restaurants",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(12.dp))

            restaurants.forEach {
                Text("• $it")
                Spacer(Modifier.height(6.dp))
            }
        }
    }
}

@Composable
fun HotelSection(
    hotels: List<String>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = "🏨 Recommended Hotels",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(12.dp))

            hotels.forEach {
                Text("• $it")
                Spacer(Modifier.height(6.dp))
            }
        }
    }
}

@Composable
fun BudgetSection(budget: Budget) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                "💰 Budget",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(12.dp))

            BudgetRow("Accommodation", budget.accommodation)
            BudgetRow("Food", budget.food)
            BudgetRow("Transport", budget.transport)
            BudgetRow("Activities", budget.activities)
            BudgetRow("Miscellaneous", budget.miscellaneous)

            HorizontalDivider(Modifier.padding(vertical = 8.dp))

            BudgetRow(
                title = "Total",
                value = budget.total,
                bold = true
            )
        }
    }
}

@Composable
private fun BudgetRow(
    title: String,
    value: String,
    bold: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            title,
            fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal
        )

        Text(
            value,
            fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal
        )
    }

    Spacer(Modifier.height(8.dp))
}

@Composable
fun DayTabs(
    trip : Trip ,
    selectedDay : Int ,
    onDaySelected :(Int) -> Unit
) {

    val days = List(trip.days.size) { "Day ${it + 1}" }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        itemsIndexed(days) { index, day ->

            Surface(
                onClick = {
                    onDaySelected(index)
                },
                shape = RoundedCornerShape(50),
                color =
                    if (selectedDay == index)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.surfaceVariant
            ) {

                Text(
                    text = day,
                    color =
                        if (selectedDay == index)
                            Color.White
                        else
                            MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    )
                )
            }
        }
    }
}

@Composable
fun TripBanner(
    image: String,
    title: String,
    duration: String
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(20.dp))
    ) {

        AsyncImage(
            model = image,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.75f)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(18.dp)
        ) {

            Text(
                text = title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = duration,
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 14.sp
            )

            Spacer(Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TripChip("Culture")
                TripChip("Heritage")
                TripChip("Food")
            }
        }
    }
}

@Composable
fun TripChip(
    text: String
) {

    Surface(
        shape = RoundedCornerShape(50),
        color = MaterialTheme.colorScheme.primary
    ) {

        Text(
            text = text,
            color = Color.White,
            fontSize = 12.sp,
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 6.dp
            )
        )
    }
}


@Composable
fun TimelineItem(
    time: String,
    title: String,
    description: String,
    image: String,
    isLast: Boolean = false
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.Top
    ) {

        // Time
        Text(
            text = time,
            fontSize = 15.sp,
            color = Color.Gray,
            modifier = Modifier.width(69.dp)
        )

        // Timeline
        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(
                        MaterialTheme.colorScheme.primary,
                        CircleShape
                    )
            )

            if (!isLast) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .width(2.dp)
                        .background(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                        )
                )
            }
        }

        Spacer(Modifier.width(14.dp))

        // Content
        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = description,
                fontSize = 16.sp,
                lineHeight = 18.sp,
                color = Color.Gray
            )
        }

        Spacer(Modifier.width(12.dp))

        AsyncImage(
            model = image,
            contentDescription = null,
            modifier = Modifier
                .size(72.dp)
                .shadow(
                    elevation = 3.dp ,
                    shape = RoundedCornerShape(12.dp)
                )
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )
    }
}


