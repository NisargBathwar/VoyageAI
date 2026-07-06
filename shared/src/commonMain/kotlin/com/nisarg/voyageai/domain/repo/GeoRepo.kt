package com.nisarg.voyageai.domain.repo

import com.nisarg.voyageai.domain.model.Location

interface GeoRepo {
    suspend fun getLocation(city : String) : List<Location>

}