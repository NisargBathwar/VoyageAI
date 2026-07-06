package com.nisarg.voyageai.presentation.details

import androidx.lifecycle.ViewModel
import com.nisarg.voyageai.domain.usecase.GetImageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DetailViewModel(private val getImageUseCase: GetImageUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState : StateFlow<DetailUiState> = _uiState.asStateFlow()

    suspend fun onAction(actions: DetailActions){
        when(actions){
            is DetailActions.DetailScreen -> {

                val image = getImageUseCase(actions.locations.city)
                println("IMAGE URL = $image")
                _uiState.value = _uiState.value.copy(
                    selectedLocations = actions.locations,
                    image = image
                )
            }
        }
    }

    fun clearState(){
        _uiState.value = DetailUiState()
    }

    fun clearSelectedLocation(){
        _uiState.update {
            it.copy(selectedLocations = null)
        }
    }
}