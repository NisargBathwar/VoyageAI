package com.nisarg.voyageai.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class GeoResponse(
    val features : List<Feature>
)

@Serializable
data class Feature(
    val properties : Property
)

@Serializable
data class Property(
    val formatted: String,
    val city: String? = null,
    val country: String? = null,
    val lat: Double,
    val lon: Double
)