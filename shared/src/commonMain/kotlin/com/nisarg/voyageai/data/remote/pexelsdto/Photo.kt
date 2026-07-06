package com.nisarg.voyageai.data.remote.pexelsdto

import kotlinx.serialization.Serializable


@Serializable
data class PexelsResponse(
    val photos: List<Photo> = emptyList()
)

@Serializable
data class Photo(
    val src : Src
)

@Serializable
data class Src(
    val landscape : String
)
