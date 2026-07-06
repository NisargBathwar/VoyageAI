package com.nisarg.voyageai.data.repo

import com.nisarg.voyageai.data.mapper.toDomain
import com.nisarg.voyageai.data.remote.api.GeoApi
import com.nisarg.voyageai.domain.model.Location
import com.nisarg.voyageai.domain.repo.GeoRepo


class GeoRepoImpl(private val api : GeoApi) : GeoRepo {
    override suspend fun getLocation(city: String): List<Location> {
        return api.getLocation(city).features.map { it.properties.toDomain() }
    }
}