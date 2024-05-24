package main.work.braintrainercompose.scores.domain.use_case

import kotlinx.coroutines.flow.Flow
import main.work.braintrainercompose.scores.domain.models.GamesHistory

interface GetHistoryUseCase {
    fun execute(): Flow<GamesHistory>
}