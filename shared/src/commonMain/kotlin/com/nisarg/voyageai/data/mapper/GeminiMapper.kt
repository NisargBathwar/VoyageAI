package com.nisarg.voyageai.data.mapper

import com.nisarg.voyageai.data.remote.AiDto.Contentt
import com.nisarg.voyageai.data.remote.AiDto.Partt
import com.nisarg.voyageai.data.remote.AiDto.Req
import com.nisarg.voyageai.data.remote.TripDTO.ActivityDto
import com.nisarg.voyageai.data.remote.TripDTO.BudgetDto
import com.nisarg.voyageai.data.remote.TripDTO.DayDto
import com.nisarg.voyageai.data.remote.TripDTO.TripResponse
import com.nisarg.voyageai.domain.model.Activity
import com.nisarg.voyageai.domain.model.Budget
import com.nisarg.voyageai.domain.model.Day
import com.nisarg.voyageai.domain.model.GeminiModel
import com.nisarg.voyageai.domain.model.Trip


fun TripResponse.toDomain() = Trip(
    tripTitle = tripTitle,
    duration = duration,
    overview = overview,
    tags = tags,
    days = days.map { it.toDomain() },
    recommendedHotels = recommendedHotels,
    recommendedRestaurants = recommendedRestaurants,
    budget = budget.toDomain(),
    travelTips = travelTips,
    importantNotes = importantNotes
)

fun DayDto.toDomain() = Day(
    dayNumber = day,
    title = title,
    activities = activities.map { it.toDomain() }
)

fun ActivityDto.toDomain() = Activity(
    time = time,
    title = title,
    description = description
)

fun BudgetDto.toDomain() = Budget(
    accommodation = accommodation,
    food = food,
    transport = transport,
    activities = activities,
    miscellaneous = miscellaneous,
    total = total
)

fun GeminiModel.toDto() : Req{
    return Req(
        contents = listOf(
            Contentt(
                parts = listOf(
                    Partt(text = text)
                )
            )
        )
    )
}