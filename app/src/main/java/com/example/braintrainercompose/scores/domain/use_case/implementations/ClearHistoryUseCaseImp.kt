package com.example.braintrainercompose.scores.domain.use_case.implementations

import com.example.braintrainercompose.scores.domain.use_case.ClearHistoryUseCase
import com.example.braintrainercompose.utils.domain.api.DataBaseRepository

class ClearHistoryUseCaseImp(private val repository: DataBaseRepository) : ClearHistoryUseCase {
    override suspend fun execute() {
        repository.clearHistory()
    }

}