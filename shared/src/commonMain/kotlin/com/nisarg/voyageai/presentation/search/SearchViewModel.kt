package com.nisarg.voyageai.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nisarg.voyageai.domain.usecase.GetCityUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(private val getCityUseCase: GetCityUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchState())
    val uiState : StateFlow<SearchState> = _uiState.asStateFlow()

    fun onActions(actions: SearchActions){
        when(actions){
            is SearchActions.Search -> {
                viewModelScope.launch {
                    try {
                        _uiState.value = _uiState.value.copy(
                            isLoading = true
                        )

                        val citiesData = getCityUseCase(actions.city)
                        println("The cities : ${actions.city}")

                        _uiState.value = _uiState.value.copy(
                            isLoading = false ,
                            error = null,
                            cities = citiesData
                        )
                    } catch (e: Exception) {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false ,
                            error = e.message
                        )
                    }
                }
            }

            is SearchActions.SelectedLocation -> {
                _uiState.update {
                    it.copy(
                        selectedLocation = actions.location
                    )
                }
            }

        }
    }

    fun clearState(){
        _uiState.update {
            SearchState()
        }
    }
}