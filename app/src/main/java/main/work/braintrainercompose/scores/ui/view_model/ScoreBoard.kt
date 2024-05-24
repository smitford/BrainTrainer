package main.work.braintrainercompose.scores.ui.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import main.work.braintrainercompose.scores.domain.models.GamesHistory
import main.work.braintrainercompose.scores.domain.use_case.ClearHistoryUseCase
import main.work.braintrainercompose.scores.domain.use_case.GetHistoryUseCase
import main.work.braintrainercompose.scores.ui.models.ScoreBoardStatus

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

    private fun consumeHistory(sessionsHistory: GamesHistory) {
        status.value = getCurrentStatus().copy(
            sessionHistoryList = sessionsHistory,
            isEmpty = sessionsHistory.freeGamesHistory.isEmpty() &&
                    sessionsHistory.timedGamesHistory.isEmpty() &&
                    sessionsHistory.timeRaceGamesHistory.isEmpty()
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