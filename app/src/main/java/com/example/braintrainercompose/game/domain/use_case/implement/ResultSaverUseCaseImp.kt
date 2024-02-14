package com.example.braintrainercompose.game.domain.use_case.implement

import com.example.braintrainercompose.game.data.models.GameSession
import com.example.braintrainercompose.game.domain.models.GameResults
import com.example.braintrainercompose.game.domain.use_case.ResultSaverUseCase
import com.example.braintrainercompose.utils.domain.api.DataBaseRepository

class ResultSaverUseCaseImp(val repository: DataBaseRepository) : ResultSaverUseCase {
    override suspend fun execute(results: GameResults, name: String, difficulty: String) {
        repository.saveSession(
            GameSession(
                name = name,
                gameResults = results,
                difficulty = difficulty
            )
        )
    }
}