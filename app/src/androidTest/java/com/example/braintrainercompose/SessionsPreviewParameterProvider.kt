package com.example.braintrainercompose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.example.braintrainercompose.game.data.expression.ExpressionGeneratorRepoImpl
import com.example.braintrainercompose.game.data.expression.GeneratorClientImpl
import com.example.braintrainercompose.game.data.results.ResultsCounterRepoImp
import com.example.braintrainercompose.game.data.settings.SettingsSharedPref
import com.example.braintrainercompose.game.data.timer.TimerRepoImp
import com.example.braintrainercompose.game.domain.models.GameResults
import com.example.braintrainercompose.game.domain.models.GameSettings
import com.example.braintrainercompose.game.domain.models.MathExpression
import com.example.braintrainercompose.game.domain.use_case.implement.GetExpressionUseCase
import com.example.braintrainercompose.game.domain.use_case.implement.GetTimeUseCase
import com.example.braintrainercompose.game.domain.use_case.implement.ResultSaverUseCaseImp
import com.example.braintrainercompose.game.domain.use_case.implement.ResultsCounterUseCaseImp
import com.example.braintrainercompose.game.domain.use_case.implement.SettingsInteractorImp
import com.example.braintrainercompose.game.ui.game_screen.ExtraWindow
import com.example.braintrainercompose.game.ui.game_screen.GameScreen
import com.example.braintrainercompose.game.ui.game_screen.GameSettings
import com.example.braintrainercompose.game.ui.game_screen.ResultsWindow
import com.example.braintrainercompose.game.ui.game_screen.TableElement
import com.example.braintrainercompose.game.ui.game_screen.TopBar
import com.example.braintrainercompose.game.ui.models.ExtraWindowStatus
import com.example.braintrainercompose.game.ui.view_model.GameViewModel
import com.example.braintrainercompose.utils.data.dao.AppDataBase
import com.example.braintrainercompose.utils.data.dao.DaoAdapter
import com.example.braintrainercompose.utils.data.dao.DataBaseRepositoryImp
import com.example.braintrainercompose.utils.ui.theme.BrainTrainerComposeTheme
import com.google.gson.Gson


@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    val context = LocalContext.current
    val gson = Gson()
    val sharedPref = context.getSharedPreferences("", 1)
    val dataBase = Room.databaseBuilder(context, AppDataBase::class.java, "brainTrainer.db")
        .build()

    BrainTrainerComposeTheme {
        GameScreen(
            GameViewModel(
                expressionGetter = GetExpressionUseCase(
                    ExpressionGeneratorRepoImpl(
                        GeneratorClientImpl()
                    )
                ),
                settingsInteractor = SettingsInteractorImp(
                    SettingsSharedPref(
                        gson = gson,
                        sharedPref = sharedPref
                    )
                ),
                timeGetter = GetTimeUseCase(TimerRepoImp()),
                resultsCounter = ResultsCounterUseCaseImp(ResultsCounterRepoImp()),
                resultSaver = ResultSaverUseCaseImp(
                    DataBaseRepositoryImp(
                        dataBase = dataBase,
                        adapter = DaoAdapter()
                    )
                )
            )
        )
    }

}

@Preview(showBackground = true)
@Composable
fun GamePreview() {
    val expressionList = listOf(
        MathExpression(
            id = 1,
            firstNumber = 1,
            secondNumber = 1,
            2
        )
    )

    Box {
        val extraWindowStatus = ExtraWindowStatus.CLOSED
        val extraWindowModifier = Modifier
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .align(Alignment.Center)
        BrainTrainerComposeTheme {
            Card(
                Modifier
                    .fillMaxSize()
            ) {
                TopBar(
                    currentTime = "00:00",
                    startButtonClicked = {
                    }) {
                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    items(expressionList) { item ->
                        TableElement(
                            expression = item,
                            expressionSymbol = "+",
                            enteredResult = "12",
                            showAnswer = true,
                            focusManager = LocalFocusManager.current
                        )
                        { text, id ->
                        }
                    }
                }
            }
        }
        when (extraWindowStatus) {
            ExtraWindowStatus.RESULTS_WINDOW -> ExtraWindow(extraWindowModifier) {
                ResultsWindow(
                    resultOfGame = GameResults(),
                    name = "",
                    nameChanged = { name -> "" }
                ) {
                }
            }

            ExtraWindowStatus.SETTINGS_WINDOW -> ExtraWindow(extraWindowModifier) {
                GameSettings(
                    baseGameSettings = GameSettings()
                ) { gameSettings ->
                }
            }

            else -> {}
        }
    }
}
