package com.nisarg.voyageai.presentation.planscreen

sealed class PlannerActions {

    data class UpdateState(

        val state: PlannerState

    ) : PlannerActions()

    data class GenerateTrip(val city : String , val country : String) : PlannerActions()
}