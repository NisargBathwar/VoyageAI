package com.nisarg.voyageai.domain.usecase

import com.nisarg.voyageai.domain.model.Location
import com.nisarg.voyageai.domain.repo.GeoRepo

class GetCityUseCase(private val repo: GeoRepo) {
    suspend operator fun invoke(city : String): List<Location> {
        print("city : $city")
        return repo.getLocation(city)
    }
}