package com.nisarg.voyageai.di

import com.nisarg.voyageai.domain.usecase.GetCityUseCase
import com.nisarg.voyageai.domain.usecase.GetGeminiUseCase
import com.nisarg.voyageai.domain.usecase.GetImageUseCase
import com.nisarg.voyageai.presentation.details.DetailViewModel
import com.nisarg.voyageai.presentation.planscreen.PlannerViewModel
import com.nisarg.voyageai.presentation.search.SearchViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

private var started = false

fun startSharedKoin() {
    println("startSharedKoin called")

    if (started) {
        println("Already started")
        return
    }

    started = true
    println("Starting Koin")

    startKoin {
        modules(appModule + vm)
    }
}

val vm = module {
    factory {
        GetCityUseCase(get())
    }

    factory {
        GetGeminiUseCase(get())
    }

    factory {
        GetImageUseCase(get())
    }

    viewModel {
        SearchViewModel(get())
    }

    viewModel {
        DetailViewModel(get())
    }

    viewModel {
        PlannerViewModel( get())
    }

}