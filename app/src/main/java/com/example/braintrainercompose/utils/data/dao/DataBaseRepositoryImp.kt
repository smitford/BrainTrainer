package com.example.braintrainercompose.utils.data.dao

import com.example.braintrainercompose.game.data.models.GameSession
import com.example.braintrainercompose.scores.domain.models.SessionHistory
import com.example.braintrainercompose.utils.domain.api.DataBaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class DataBaseRepositoryImp(private val dataBase: AppDataBase, private val adapter: DaoAdapter) :
    DataBaseRepository {
    override suspend fun saveSession(sessionScore: GameSession) {
        dataBase.scoreDao().insertSessionScore(adapter.fromGameSessionToScoreEntity(sessionScore))
    }

    override suspend fun clearHistory() {
        dataBase.scoreDao().deleteAllSessions()
    }

    override fun getSessionsHistory(): Flow<List<SessionHistory>> =
        flow {
            val history = dataBase.scoreDao().getAllSessionsScores()
            emit(history.map { adapter.fromScoreEntityToSessionHistory(it) })
        }.flowOn(Dispatchers.IO)
}