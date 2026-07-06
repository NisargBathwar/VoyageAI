package com.nisarg.voyageai.domain.usecase

import com.nisarg.voyageai.domain.repo.PexelRepo

class GetImageUseCase(private val repo: PexelRepo) {
    suspend operator fun invoke(city : String) : String{
        return repo.getImage(city)
    }
}