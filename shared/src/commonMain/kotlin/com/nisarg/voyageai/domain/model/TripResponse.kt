package com.nisarg.voyageai.domain.model

data class Trip(
    val tripTitle: String,
    val duration: String,
    val overview: String,
    val tags: List<String>,
    val days: List<Day>,
    val recommendedHotels: List<String>,
    val recommendedRestaurants: List<String>,
    val budget: Budget,
    val travelTips: List<String>,
    val importantNotes: List<String>
)

data class Day(
    val dayNumber: Int,
    val title: String,
    val activities: List<Activity>
)

data class Activity(
    val time: String,
    val title: String,
    val description: String
)

data class Budget(
    val accommodation: String,
    val food: String,
    val transport: String,
    val activities: String,
    val miscellaneous: String,
    val total: String
)