package com.nisarg.voyageai.data.mapper

import com.nisarg.voyageai.data.remote.dto.Property
import com.nisarg.voyageai.domain.model.Location


fun Property.toDomain() : Location{
    return Location(
        formatted = formatted,
        city = city ?: "",
        country = country ?: "",
        lat = lat,
        lng = lon
    )
}

