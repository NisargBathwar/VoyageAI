package com.nisarg.voyageai.domain.repo

interface PexelRepo {
    suspend fun getImage(city : String) : String
}