package com.example.braintrainercompose.scores.domain.use_case.implementations

import com.example.braintrainercompose.scores.domain.models.SessionHistory
import com.example.braintrainercompose.scores.domain.use_case.GetHistoryUseCase
import com.example.braintrainercompose.utils.domain.api.DataBaseRepository
import kotlinx.coroutines.flow.Flow

class GetHistoryUseCaseImp(private val repository: DataBaseRepository) : GetHistoryUseCase {
    override fun execute(): Flow<List<SessionHistory>> =
        repository.getSessionsHistory()

}