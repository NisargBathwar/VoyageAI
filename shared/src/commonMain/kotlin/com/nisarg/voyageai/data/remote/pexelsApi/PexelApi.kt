package com.nisarg.voyageai.data.remote.pexelsApi

import com.nisarg.voyageai.data.remote.pexelsdto.PexelsResponse
import com.nisarg.voyageai.di.NetworkConstant
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter

class PexelApi(private val client: HttpClient) {

    suspend fun getImageUrl(city : String) : PexelsResponse{
        return client.get("https://api.pexels.com/v1/search"){
            parameter("query"  , city)
            parameter("per_page" ,1)
            header("Authorization" , NetworkConstant.PEXEL_KEY)
        }.body()
    }
}