package com.nisarg.voyageai.presentation.details

import com.nisarg.voyageai.domain.model.Location

data class DetailUiState(
    val image : String = "" ,
    val isLoading : Boolean = false   ,
    val selectedLocations : Location? = null ,
    val error : String? = null
)
