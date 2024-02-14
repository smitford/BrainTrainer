package com.example.braintrainercompose.utils.domain.api

import com.example.braintrainercompose.game.data.models.GameSession
import com.example.braintrainercompose.scores.domain.models.SessionHistory
import kotlinx.coroutines.flow.Flow

interface DataBaseRepository {
    suspend fun saveSession(sessionScore: GameSession)
    suspend fun clearHistory()
    fun getSessionsHistory(): Flow<List<SessionHistory>>
}