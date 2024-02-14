package com.example.braintrainercompose.scores.ui.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.braintrainercompose.scores.domain.models.SessionHistory
import com.example.braintrainercompose.scores.domain.use_case.ClearHistoryUseCase
import com.example.braintrainercompose.scores.domain.use_case.GetHistoryUseCase
import com.example.braintrainercompose.scores.ui.models.ScoreBoardStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ScoreBoard(
    val getDataUseCase: GetHistoryUseCase,
    val clearHistoryUseCase: ClearHistoryUseCase
) : ViewModel() {
    val status = MutableLiveData(ScoreBoardStatus())

    private fun getCurrentStatus() = status.value ?: ScoreBoardStatus()
    // private val sessionList = listOf<SessionHistory>()
    fun getHistory() {
        viewModelScope.launch {
            getDataUseCase.execute().collect {
                consumeHistory(it)
            }
        }
    }

    private fun consumeHistory(sessionsHistory: List<SessionHistory>) {
        status.value = getCurrentStatus().copy(
            sessionHistoryList = sessionsHistory,
            isEmpty = sessionsHistory.isEmpty()
        )
    }

    fun clearHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            clearHistoryUseCase.execute()
        }
        status.value = getCurrentStatus().copy(
            isEmpty = true
        )
    }
}