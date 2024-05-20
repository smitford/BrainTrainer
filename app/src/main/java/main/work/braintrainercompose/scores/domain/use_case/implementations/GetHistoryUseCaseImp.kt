package main.work.braintrainercompose.scores.domain.use_case.implementations

import kotlinx.coroutines.flow.Flow
import main.work.braintrainercompose.scores.domain.models.GamesHistory
import main.work.braintrainercompose.scores.domain.use_case.GetHistoryUseCase
import main.work.braintrainercompose.utils.domain.api.DataBaseRepository

class GetHistoryUseCaseImp(private val repository: DataBaseRepository) : GetHistoryUseCase {
    override fun execute(): Flow<GamesHistory> =
        repository.getSessionsHistory()

}