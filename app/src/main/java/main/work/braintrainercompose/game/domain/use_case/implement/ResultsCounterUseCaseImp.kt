package main.work.braintrainercompose.game.domain.use_case.implement

import main.work.braintrainercompose.game.domain.api.ResultsCounterRepo
import main.work.braintrainercompose.game.domain.models.GameResults
import main.work.braintrainercompose.game.domain.models.GameSettings
import main.work.braintrainercompose.game.domain.use_case.ResultsCounterUseCase

class ResultsCounterUseCaseImp(private val repository: ResultsCounterRepo) : ResultsCounterUseCase {
    override fun execute(
        answerList: Map<Int, String>,
        expressionAnswers: List<Int>,
        gameSettings: GameSettings,
        time: Long
    ): GameResults =
        repository.count(
            answerList = answerList,
            expressionAnswers = expressionAnswers,
            gameSettings = gameSettings,
            time=time
        )

}