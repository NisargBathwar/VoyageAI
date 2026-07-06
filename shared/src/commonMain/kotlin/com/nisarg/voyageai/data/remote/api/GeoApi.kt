package com.nisarg.voyageai.data.remote.api

import com.nisarg.voyageai.data.remote.dto.GeoResponse
import com.nisarg.voyageai.di.NetworkConstant
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class GeoApi(private val client: HttpClient) {
    suspend fun getLocation(text : String) : GeoResponse{
        return client.get(NetworkConstant.BASE_URL){
            parameter("text" , text)
            parameter("apiKey" , NetworkConstant.API_KEY)
            parameter("limit" , 5)
        }.body()

    }
}