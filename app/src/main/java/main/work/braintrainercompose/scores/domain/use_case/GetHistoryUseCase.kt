package main.work.braintrainercompose.scores.domain.use_case

import kotlinx.coroutines.flow.Flow
import main.work.braintrainercompose.scores.domain.models.SessionHistory

interface GetHistoryUseCase {
    fun execute(): Flow<List<SessionHistory>>
}