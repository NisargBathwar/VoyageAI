package com.nisarg.voyageai.data.remote.aiapi

import com.nisarg.voyageai.data.remote.AiDto.Req
import com.nisarg.voyageai.data.remote.AiDto.Res
import com.nisarg.voyageai.di.NetworkConstant
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

class GeminiApi(private val client: HttpClient) {
    suspend fun generateTrip(req: Req): Res {

        var lastError = ""

        NetworkConstant.GEMINI_KEY.forEachIndexed { index, key ->
            print("Trying keys ${index + 1}")

            val response = client.post(NetworkConstant.GEMINI_BASE_URL){
                parameter("key" , key)
                contentType(ContentType.Application.Json)
                setBody(req)
            }

            println("Status : ${response.status}")

            if(response.status.isSuccess()){
                return response.body()
            }

            lastError = response.bodyAsText()

            if (response.status.value !=429){
                throw Exception(lastError)
            }

        }
        throw Exception("out of quota")
    }
}