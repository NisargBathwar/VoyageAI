package com.nisarg.voyageai.data.remote.TripDTO

import kotlinx.serialization.Serializable

@Serializable
data class TripResponse(
    val tripTitle: String,
    val duration: String,
    val overview: String,
    val tags: List<String>,
    val days: List<DayDto>,
    val recommendedHotels: List<String>,
    val recommendedRestaurants: List<String>,
    val budget: BudgetDto,
    val travelTips: List<String>,
    val importantNotes: List<String>
)

@Serializable
data class DayDto(
    val day: Int,
    val title: String,
    val activities: List<ActivityDto>
)

@Serializable
data class ActivityDto(
    val time: String,
    val title: String,
    val description: String
)

@Serializable
data class BudgetDto(
    val accommodation: String,
    val food: String,
    val transport: String,
    val activities: String,
    val miscellaneous: String,
    val total: String
)