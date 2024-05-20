package main.work.braintrainercompose.game.domain.use_case.implement

import main.work.braintrainercompose.game.data.models.GameSession
import main.work.braintrainercompose.game.domain.models.GameResults
import main.work.braintrainercompose.game.domain.use_case.ResultSaverUseCase
import main.work.braintrainercompose.utils.domain.api.DataBaseRepository
import main.work.braintrainercompose.utils.domain.models.GameType

class ResultSaverUseCaseImp(val repository: DataBaseRepository) : ResultSaverUseCase {
    override suspend fun execute(
        results: GameResults,
        name: String,
        difficulty: String,
        gameType: GameType
    ) {
        repository.saveSession(
            GameSession(
                name = name,
                gameResults = results,
                difficulty = difficulty,
                gameType = gameType
            )
        )
    }
}