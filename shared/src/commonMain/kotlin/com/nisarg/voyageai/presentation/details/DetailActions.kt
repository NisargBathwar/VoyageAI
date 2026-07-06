package com.nisarg.voyageai.presentation.details

import com.nisarg.voyageai.domain.model.Location

sealed class DetailActions {
    data class DetailScreen(val locations : Location) : DetailActions()
}