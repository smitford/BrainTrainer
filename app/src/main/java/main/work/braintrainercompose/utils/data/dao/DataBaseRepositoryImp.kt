package main.work.braintrainercompose.utils.data.dao

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import main.work.braintrainercompose.game.data.models.GameSession
import main.work.braintrainercompose.scores.domain.models.GamesHistory
import main.work.braintrainercompose.utils.domain.api.DataBaseRepository


class DataBaseRepositoryImp(private val dataBase: AppDataBase, private val adapter: DaoAdapter) :
    DataBaseRepository {
    override suspend fun saveSession(sessionScore: GameSession) {
        dataBase.scoreDao().insertSessionScore(adapter.fromGameSessionToScoreEntity(sessionScore))
    }

    override suspend fun clearHistory() {
        dataBase.scoreDao().deleteAllSessions()
    }

    override fun getSessionsHistory(): Flow<GamesHistory> =
        flow {
            val freeGameHistory = dataBase.scoreDao().getAllNotTimedHistory()
            val timedGamesHistory = dataBase.scoreDao().getAllTimedHistory()
            val timeRaceGamesHistory = dataBase.scoreDao().getAllTimeRacedHistory()
            emit(GamesHistory(
                freeGamesHistory = freeGameHistory.map {
                    adapter.fromScoreEntityToSessionHistory(
                        it
                    )
                },
                timedGamesHistory = timedGamesHistory.map {
                    adapter.fromScoreEntityToSessionHistory(
                        it
                    )
                },
                timeRaceGamesHistory = timeRaceGamesHistory.map {
                    adapter.fromScoreEntityToSessionHistory(
                        it
                    )
                }
            )
            )
        }.flowOn(Dispatchers.IO)
}