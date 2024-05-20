package main.work.braintrainercompose.game.domain.use_case

import main.work.braintrainercompose.game.domain.models.GameResults

interface ResultSaverUseCase {
    suspend fun execute(results: GameResults, name: String, difficulty: String)
}