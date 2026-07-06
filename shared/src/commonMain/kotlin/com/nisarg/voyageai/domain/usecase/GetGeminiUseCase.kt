package com.nisarg.voyageai.domain.usecase

import com.nisarg.voyageai.data.remote.TripDTO.TripResponse
import com.nisarg.voyageai.domain.model.GeminiModel
import com.nisarg.voyageai.domain.model.Trip
import com.nisarg.voyageai.domain.repo.GeminiRepo

class GetGeminiUseCase(private val repo: GeminiRepo) {
    suspend operator fun invoke(text : String) : Trip {
        return repo.getTrip(text)
    }
}