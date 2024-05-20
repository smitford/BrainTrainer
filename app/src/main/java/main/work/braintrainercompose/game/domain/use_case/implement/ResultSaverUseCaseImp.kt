package main.work.braintrainercompose.game.domain.use_case.implement

import main.work.braintrainercompose.game.data.models.GameSession
import main.work.braintrainercompose.game.domain.models.GameResults
import main.work.braintrainercompose.game.domain.use_case.ResultSaverUseCase
import main.work.braintrainercompose.utils.domain.api.DataBaseRepository

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