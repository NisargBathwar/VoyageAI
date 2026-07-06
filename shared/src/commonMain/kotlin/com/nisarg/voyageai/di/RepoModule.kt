package com.nisarg.voyageai.di

import com.nisarg.voyageai.data.remote.pexelsApi.PexelApi
import com.nisarg.voyageai.data.repo.GeminiRepoImpl
import com.nisarg.voyageai.data.repo.GeoRepoImpl
import com.nisarg.voyageai.data.repo.PexelRepoImpl
import com.nisarg.voyageai.domain.repo.GeminiRepo
import com.nisarg.voyageai.domain.repo.GeoRepo
import com.nisarg.voyageai.domain.repo.PexelRepo
import org.koin.dsl.module

val repoModule = module {
    single<GeoRepo> {
        GeoRepoImpl(get())
    }
    single<GeminiRepo> {
        GeminiRepoImpl(get())
    }
    
    single<PexelRepo> {
        PexelRepoImpl(get())
    }
}


