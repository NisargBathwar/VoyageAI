package com.nisarg.voyageai.data.remote.AiDto

import kotlinx.serialization.Serializable

@Serializable
data class Res(
    val candidates: List<Candidate>
)

@Serializable
data class Candidate(
    val content: Content,
    val finishReason: String,
    val index: Int
)

@Serializable
data class Content(
    val parts: List<Part>,
    val role: String
)

@Serializable
data class Part(
    val text: String
)
