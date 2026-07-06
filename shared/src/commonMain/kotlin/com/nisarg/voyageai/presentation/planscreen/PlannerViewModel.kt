package com.nisarg.voyageai.presentation.planscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nisarg.voyageai.domain.usecase.GetGeminiUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlannerViewModel(private val getGeminiUseCase: GetGeminiUseCase) : ViewModel (){

    private val _uiState = MutableStateFlow(PlannerState())
    val uiState : StateFlow<PlannerState> = _uiState.asStateFlow()



    fun onPlan(actions: PlannerActions){
        when(actions){

            is PlannerActions.UpdateState ->{
                _uiState.value = actions.state
            }
            is PlannerActions.GenerateTrip -> {
                viewModelScope.launch {
                    try {
                        _uiState.value = _uiState.value.copy(
                            isLoading = true
                        )

                        val planner = _uiState.value

                        val prompt = """
                            You are VoyageAI, an expert travel planner.
                            
                            Create a realistic, practical itinerary for:
                            
                            Destination: ${actions.city}, ${actions.country}
                            
                            Days: ${planner.days}
                            Travellers: ${planner.travellers}
                            Budget: ${planner.budget}
                            Travel Style: ${planner.travelStyle}
                            Interests: ${planner.interests.joinToString(", ")}
                            
                            Rules:
                            - Match the selected budget.
                            - 3–5 activities per day.
                            - Keep travel practical.
                            - No repeated attractions.
                            - Recommend exactly 3 hotels.
                            - Recommend exactly 3 restaurants.
                            - Return exactly 3 travel tips.
                            - Return exactly 3 important notes.
                            - Use 12-hour time format (e.g. 09:00 AM).
                            - Overview: max 30 words.
                            - Activity descriptions: max 15 words.
                            - Hotel and restaurant names only.
                            - Budget values should be concise (example: ₹2,500).
                            
                            Return ONLY valid JSON matching this schema:
                           
                            {
                              "tripTitle":"",
                              "duration":"",
                              "overview":"",
                              "tags":["","",""],
                              "days":[
                                {
                                  "day":1,
                                  "title":"",
                                  "activities":[
                                    {
                                      "time":"",
                                      "title":"",
                                      "description":""
                                    }
                                  ]
                                }
                              ],
                              "recommendedHotels":["","",""],
                              "recommendedRestaurants":["","",""],
                              "budget":{
                                "accommodation":"",
                                "food":"",
                                "transport":"",
                                "activities":"",
                                "miscellaneous":"",
                                "total":""
                              },
                              "travelTips":["","",""],
                              "importantNotes":["","",""]
                            }
                            
                            All fields are required. Never return null or omit any field.
                            
                            No markdown.
                            No code block.
                            No explanation.
                            Only valid JSON.
                            """.trimIndent()


                        println(prompt)

                        val data = getGeminiUseCase(prompt)

                        _uiState.value = _uiState.value.copy(
                            trip = data ,
                            isLoading = false ,
                            error = null
                        )
                    }catch (e : Exception) {
                        val message = when { e.message?.contains("quota", ignoreCase = true) == true ||
                                e.message?.contains("RESOURCE_EXHAUSTED", ignoreCase = true) == true ->
                            "AI service is temporarily unavailable. Please try again later."
                        e.message?.contains("timeout", ignoreCase = true) == true -> "Request timed out. Please try again."
                        else -> "Something went wrong. Please try again."
                    }
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = message
                        )
                    }
                }
            }
        }
    }
}