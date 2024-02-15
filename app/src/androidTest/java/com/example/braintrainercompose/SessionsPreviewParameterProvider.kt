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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.braintrainercompose.game.domain.models.GameResults
import com.example.braintrainercompose.game.domain.models.MathExpression
import com.example.braintrainercompose.game.ui.game_screen.ExtraWindow
import com.example.braintrainercompose.game.ui.game_screen.GameSettings
import com.example.braintrainercompose.game.ui.game_screen.ResultsWindow
import com.example.braintrainercompose.game.ui.game_screen.TableElement
import com.example.braintrainercompose.game.ui.game_screen.TopBar
import com.example.braintrainercompose.game.ui.models.ExtraWindowStatus
import com.example.braintrainercompose.utils.ui.theme.BrainTrainerComposeTheme


/*@Preview(showBackground = true)
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

}*/

@Preview(showBackground = true)
@Composable
fun GamePreview() {
    val focusManager = LocalFocusManager.current
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
                    items(expressionList){ expression ->
                        TableElement(
                            expression = expression,
                            enteredResult = "12",
                            expressionSymbol = "+",
                            showAnswer = true,
                            focusManager = focusManager,
                        ) { text, id -> }
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
                    baseGameSettings = com.example.braintrainercompose.game.domain.models.GameSettings()
                ) { gameSettings ->
                }
            }

            else -> {}
        }
    }
}
