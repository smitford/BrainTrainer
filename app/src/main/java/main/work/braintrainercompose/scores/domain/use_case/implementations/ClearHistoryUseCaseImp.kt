package main.work.braintrainercompose.scores.domain.use_case.implementations

import main.work.braintrainercompose.scores.domain.use_case.ClearHistoryUseCase
import main.work.braintrainercompose.utils.domain.api.DataBaseRepository

class ClearHistoryUseCaseImp(private val repository: DataBaseRepository) : ClearHistoryUseCase {
    override suspend fun execute() {
        repository.clearHistory()
    }

}