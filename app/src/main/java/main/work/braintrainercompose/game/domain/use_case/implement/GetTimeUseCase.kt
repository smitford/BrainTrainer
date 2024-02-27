package main.work.braintrainercompose.game.domain.use_case.implement

import main.work.braintrainercompose.game.domain.api.TimerRepo
import main.work.braintrainercompose.game.domain.use_case.GetTime

class GetTimeUseCase(private val timerRepo: TimerRepo) : GetTime {
    override fun execute(currentTimer: Long, isCountDown: Boolean): String =
        timerRepo.getTime(currentTime = currentTimer, isCountDown = isCountDown)

}