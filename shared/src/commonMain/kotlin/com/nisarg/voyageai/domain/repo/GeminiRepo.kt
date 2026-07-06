package com.nisarg.voyageai.domain.repo

import com.nisarg.voyageai.domain.model.Trip

interface GeminiRepo {
    suspend fun getTrip(text : String) : Trip
}

