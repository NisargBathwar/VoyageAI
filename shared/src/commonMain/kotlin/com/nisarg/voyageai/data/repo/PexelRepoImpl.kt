package com.nisarg.voyageai.data.repo

import com.nisarg.voyageai.data.remote.pexelsApi.PexelApi
import com.nisarg.voyageai.domain.repo.PexelRepo

class PexelRepoImpl(private val api: PexelApi) : PexelRepo {
    override suspend fun getImage(city: String): String {
        return api.getImageUrl(city).photos
            .firstOrNull()
            ?.src
            ?.landscape
            ?: ""
    }
}