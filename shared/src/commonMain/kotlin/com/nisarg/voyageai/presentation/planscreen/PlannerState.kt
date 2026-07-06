package com.nisarg.voyageai.presentation.planscreen

import com.nisarg.voyageai.domain.model.Trip

data class PlannerState(
    val days: String = "",
    val travellers: Int = 2,
    val budget: String = "₹10K - ₹20K",
    val travelStyle: String = "Culture",
    val interests: List<String> = emptyList(),
    val notes: String = "",
    val trip : Trip? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)