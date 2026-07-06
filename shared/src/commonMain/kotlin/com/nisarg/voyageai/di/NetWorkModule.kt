package com.nisarg.voyageai.di

import com.nisarg.voyageai.data.remote.aiapi.GeminiApi
import com.nisarg.voyageai.data.remote.api.GeoApi
import com.nisarg.voyageai.data.remote.pexelsApi.PexelApi
import org.koin.dsl.module



    val networkModule = module {
        single {
            ClientFactory.create()
        }

        single {
            GeoApi(get())
        }

        single {
            GeminiApi(get())
        }

        single {
            PexelApi(get())
        }
    }
