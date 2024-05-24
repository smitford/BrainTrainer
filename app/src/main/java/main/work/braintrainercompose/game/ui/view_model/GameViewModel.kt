package main.work.braintrainercompose.game.ui.view_model

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import main.work.braintrainercompose.game.domain.models.GameResults
import main.work.braintrainercompose.game.domain.models.GameSettings
import main.work.braintrainercompose.game.domain.models.MathExpression
import main.work.braintrainercompose.game.domain.use_case.GetExpression
import main.work.braintrainercompose.game.domain.use_case.GetTime
import main.work.braintrainercompose.game.domain.use_case.ResultSaverUseCase
import main.work.braintrainercompose.game.domain.use_case.ResultsCounterUseCase
import main.work.braintrainercompose.game.domain.use_case.SettingsInteractor
import main.work.braintrainercompose.game.ui.models.GameProgress
import main.work.braintrainercompose.game.ui.models.GameState
import main.work.braintrainercompose.utils.DataUtils.Companion.SHOW_RESULTS_DELAY_MSC
import main.work.braintrainercompose.utils.DataUtils.Companion.TIMER_DELAY_MSC
import main.work.braintrainercompose.utils.debounce
import main.work.braintrainercompose.utils.domain.models.GameType

class GameViewModel(
    private val expressionGetter: GetExpression,
    private val settingsInteractor: SettingsInteractor,
    private val timeGetter: GetTime,
    private val resultsCounter: ResultsCounterUseCase,
    private val resultSaver: ResultSaverUseCase,
    private val bottomBarSynchronizer: (GameProgress) -> Unit
) : ViewModel() {
    private var gameState = MutableLiveData(GameState())
    private val answersList = mutableStateMapOf<Int, String>()
    private var expressions: List<MathExpression> = listOf()
    private var timerJob: Job? = null
    private var currentTimer = 0L
    private val showAnswerDebounce = debounce<Unit>(
        delayMillis = SHOW_RESULTS_DELAY_MSC,
        coroutineScope = viewModelScope,
        useLastParam = true
    ) { showAnswer() }

    init {
        getSettings()
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
        timerJob = null
    }

    fun getState(): LiveData<GameState> = gameState

    private fun getCurrentStatus(): GameState = gameState.value ?: GameState()

    fun addNewAnswer(id: Int, answer: String) {
        answersList[id] = answer
        gameState.value = getCurrentStatus().copy(answers = answersList)
        if (answersList.size == expressions.size) {
            showAnswerDebounce(Unit)
        }

    }

    private fun showAnswer() {
        val results = resultsCounter.execute(
            answerList = answersList.toMap(),
            expressionAnswers = expressions.map { it.answer },
            gameSettings = gameState.value!!.settings,
            time = currentTimer
        )
        gameState.value =
            getCurrentStatus().copy(
                gapsFiled = true,
                gamesProgress = GameProgress.STOPPED,
                gameResults = results
            )
        bottomBarSynchronizer(GameProgress.STOPPED)
    }

    private fun defaultValue() {
        answersList.clear()
        gameState.value = getCurrentStatus().copy(
            answers = answersList,
            gapsFiled = false,
            gamesProgress = GameProgress.NOT_STARTED,
            expression = listOf(),
            timer = "00:00",
        )
        currentTimer = 0L
        timerJob?.cancel()
        timerJob = null
    }

    fun getExpression() {
        defaultValue()
        if (gameState.value?.settings?.countDown == true)
            currentTimer =
                (gameState.value?.settings!!.difficulty.time * gameState.value?.settings!!.difficulty.count).toLong() * 1000
        expressions =
            expressionGetter.execute(gameSettings = gameState.value?.settings ?: GameSettings())
        gameState.value = getCurrentStatus().copy(
            expression = expressions,
            gamesProgress = GameProgress.IN_PROGRESS
        )
        bottomBarSynchronizer(GameProgress.IN_PROGRESS)
    }

    fun saveGameSettings(settings: GameSettings) {
        settingsInteractor.saveSettings(settings = settings)
    }

    fun getSettings() {
        val settings = settingsInteractor.getSettings()
        defaultValue()
        gameState.value = getCurrentStatus().copy(settings = settings)
    }

    fun timerRefresher() {
        timerJob = viewModelScope.launch {
            val countDownSetting = gameState.value!!.settings.countDown
            while (gameState.value?.gamesProgress == GameProgress.IN_PROGRESS) {
                if (countDownSetting && currentTimer == 0L) {
                    val results = resultsCounter.execute(
                        answersList.toMap(),
                        expressions.map { it.answer },
                        gameState.value!!.settings,
                        time = currentTimer
                    )
                    gameState.value =
                        getCurrentStatus().copy(
                            gapsFiled = true,
                            gamesProgress = GameProgress.STOPPED,
                            gameResults = results
                        )
                    bottomBarSynchronizer(GameProgress.STOPPED)
                }

                gameState.value =
                    getCurrentStatus().copy(
                        timer = timeGetter.execute(
                            currentTimer = currentTimer,
                            isCountDown = countDownSetting
                        )
                    )
                if (countDownSetting)
                    currentTimer -= TIMER_DELAY_MSC
                else
                    currentTimer += TIMER_DELAY_MSC

                delay(TIMER_DELAY_MSC)
            }
        }
    }

    fun pauseGame() {
        gameState.value = getCurrentStatus().copy(gamesProgress = GameProgress.PAUSED)
        bottomBarSynchronizer(GameProgress.PAUSED)
    }

    fun continueGame() {
        gameState.value = getCurrentStatus().copy(gamesProgress = GameProgress.IN_PROGRESS)
        timerRefresher()
        bottomBarSynchronizer(GameProgress.IN_PROGRESS)
    }

    fun saveResults(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            resultSaver.execute(
                results = gameState.value?.gameResults ?: GameResults(),
                name = name,
                difficulty = gameState.value?.settings?.difficulty?.name ?: " ",
                gameType = gameState.value?.settings?.getGameType() ?: GameType.FREE_GAME
            )
        }
        saveMode()
    }

    fun saveMode() {
        gameState.value = getCurrentStatus().copy(gamesProgress = GameProgress.SAVED)
    }


}
