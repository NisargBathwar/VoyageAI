package com.nisarg.voyageai.presentation.search

import com.nisarg.voyageai.domain.model.Location

sealed class SearchActions {
    data class Search(val city : String) : SearchActions()
    data class SelectedLocation(val location : Location) : SearchActions()
}