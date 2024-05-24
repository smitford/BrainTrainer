package main.work.braintrainercompose.utils.domain.api

import kotlinx.coroutines.flow.Flow
import main.work.braintrainercompose.game.data.models.GameSession
import main.work.braintrainercompose.scores.domain.models.GamesHistory

interface DataBaseRepository {
    suspend fun saveSession(sessionScore: GameSession)
    suspend fun clearHistory()
    fun getSessionsHistory(): Flow<GamesHistory>
}