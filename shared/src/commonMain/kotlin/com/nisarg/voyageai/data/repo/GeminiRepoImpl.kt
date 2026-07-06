package com.nisarg.voyageai.data.repo

import com.nisarg.voyageai.data.mapper.toDomain
import com.nisarg.voyageai.data.mapper.toDto
import com.nisarg.voyageai.data.remote.TripDTO.TripResponse
import com.nisarg.voyageai.data.remote.aiapi.GeminiApi
import com.nisarg.voyageai.domain.model.GeminiModel
import com.nisarg.voyageai.domain.model.Trip
import com.nisarg.voyageai.domain.repo.GeminiRepo
import kotlinx.serialization.json.Json

class GeminiRepoImpl(private val api: GeminiApi) : GeminiRepo {
    override suspend fun getTrip(text: String): Trip {
        val req = GeminiModel(text).toDto()

        val json = api.generateTrip(req)
            .candidates
            .firstOrNull()
            ?.content
            ?.parts
            ?.firstOrNull()
            ?.text
            .orEmpty()
        return Json.decodeFromString<TripResponse>(json).toDomain()
    }

}