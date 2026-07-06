package com.nisarg.voyageai.data.remote.AiDto

import kotlinx.serialization.Serializable

@Serializable
data class Req(
    val contents: List<Contentt>
)

@Serializable
data class Contentt(
    val parts: List<Partt>
)

@Serializable
data class Partt(
    val text: String
)