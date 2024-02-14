package com.example.braintrainercompose.scores.domain.use_case

import com.example.braintrainercompose.scores.domain.models.SessionHistory
import kotlinx.coroutines.flow.Flow

interface GetHistoryUseCase {
    fun execute(): Flow<List<SessionHistory>>
}