package com.nisarg.voyageai.presentation.search

import com.nisarg.voyageai.domain.model.Location

data class SearchState(
    val cities : List<Location> = emptyList(),
    val selectedLocation : Location? = null  ,
    val isLoading : Boolean = false,
    val error : String? = null
)
