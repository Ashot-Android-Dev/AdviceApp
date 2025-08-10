package com.example.advice.view.uiStates

import com.example.advice.model.room.advice.AdviceEntity

sealed class AdviceState {
    object Loading: AdviceState()
    data class Success(val adviceList: List<AdviceEntity>): AdviceState()
    data class Error(val message: String): AdviceState()
}