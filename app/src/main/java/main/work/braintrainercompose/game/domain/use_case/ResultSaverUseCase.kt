package main.work.braintrainercompose.game.domain.use_case

import main.work.braintrainercompose.game.domain.models.GameResults
import main.work.braintrainercompose.utils.domain.models.GameType

interface ResultSaverUseCase {
    suspend fun execute(results: GameResults, name: String, difficulty: String, gameType: GameType)
}