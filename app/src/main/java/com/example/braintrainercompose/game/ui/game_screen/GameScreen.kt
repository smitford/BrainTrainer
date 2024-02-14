package com.example.braintrainercompose.game.ui.game_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.braintrainercompose.R
import com.example.braintrainercompose.game.di.gameModule
import com.example.braintrainercompose.game.domain.models.GameResults
import com.example.braintrainercompose.game.domain.models.GameSettings
import com.example.braintrainercompose.game.domain.models.MathExpression
import com.example.braintrainercompose.game.ui.models.ExtraWindowStatus
import com.example.braintrainercompose.game.ui.models.GameProgress
import com.example.braintrainercompose.game.ui.view_model.GameViewModel
import com.example.braintrainercompose.utils.getExpression
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication
import org.koin.core.annotation.KoinExperimentalAPI

@Composable
fun GameScreen(viewModel: GameViewModel = koinViewModel()) {
    val gameState = viewModel.getState().observeAsState()
    val focusManager = LocalFocusManager.current
    var extraWindowStatus by remember {
        mutableStateOf(ExtraWindowStatus.CLOSED)
    }
    val blurModifier = Modifier
        .fillMaxSize()
        .blur(10.dp)
        .alpha(0.5F)
        .clickable { extraWindowStatus = ExtraWindowStatus.CLOSED }
    val usualModifier = Modifier
        .fillMaxSize()
    var enteredName by remember {
        mutableStateOf("")
    }

    if (gameState.value?.gamesProgress == GameProgress.STOPPED) extraWindowStatus =
        ExtraWindowStatus.RESULTS_WINDOW


    Box {
        val extraWindowModifier = Modifier
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .align(Alignment.Center)
        Card(
            modifier = when (extraWindowStatus) {
                ExtraWindowStatus.SETTINGS_WINDOW, ExtraWindowStatus.RESULTS_WINDOW -> blurModifier
                else -> usualModifier
            }
        ) {
            TopBar(
                currentTime = gameState.value?.timer ?: "00:00",
                startButtonClicked = {
                    viewModel.getExpression()
                    if (gameState.value!!.settings.timeGame)
                        viewModel.timerRefresher()
                }) {
                extraWindowStatus = ExtraWindowStatus.SETTINGS_WINDOW
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                gameState.value?.expression?.let { expressionList ->
                    items(expressionList) { item ->
                        TableElement(
                            expression = item,
                            expressionSymbol = gameState.value?.settings?.expressionType?.symbol
                                ?: "+",
                            enteredResult = gameState.value?.answers?.get(item.id) ?: "",
                            showAnswer = gameState.value!!.gapsFiled,
                            focusManager = focusManager
                        )
                        { text, id ->
                            viewModel.addNewAnswer(id = id, answer = text)
                        }
                    }
                }
            }
        }
        when (extraWindowStatus) {
            ExtraWindowStatus.RESULTS_WINDOW -> ExtraWindow(extraWindowModifier) {
                ResultsWindow(
                    resultOfGame = gameState.value?.gameResults ?: GameResults(),
                    name = enteredName,
                    nameChanged = { name -> enteredName = name }
                ) {
                    viewModel.saveResults(enteredName)
                    extraWindowStatus = ExtraWindowStatus.CLOSED
                }
            }

            ExtraWindowStatus.SETTINGS_WINDOW -> ExtraWindow(extraWindowModifier) {
                GameSettings(
                    baseGameSettings = gameState.value?.settings ?: GameSettings()
                ) { gameSettings ->
                    extraWindowStatus = ExtraWindowStatus.CLOSED
                    viewModel.saveGameSettings(gameSettings)
                    viewModel.getSettings()
                }
            }

            else -> {}
        }
    }
}

@Composable
fun TopBar(currentTime: String, startButtonClicked: () -> Unit, menuIconClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TimeTable(currentTime = currentTime)
        Button(onClick = { startButtonClicked() }) {
            Text(text = stringResource(id = R.string.just_do_it))
        }
        Icon(
            modifier = Modifier
                .padding(end = 15.dp)
                .clickable { menuIconClicked() },
            imageVector = Icons.Default.Menu,
            contentDescription = "game_settings",
        )
    }

}

@Composable
fun TimeTable(currentTime: String) {
    Row {
        Image(
            modifier = Modifier.padding(
                horizontal = 10.dp,
                vertical = 3.dp
            ),
            contentDescription = "timer",
            painter = painterResource(id = R.drawable.timer)
        )
        Text(
            text = currentTime,
            modifier = Modifier.padding(
                horizontal = 4.dp,
                vertical = 3.dp
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TableElement(
    expression: MathExpression,
    enteredResult: String,
    expressionSymbol: String,
    showAnswer: Boolean,
    focusManager: FocusManager,
    editText: (String, Int) -> Unit
) {
    if (showAnswer) focusManager.clearFocus()
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Text(text = getExpression(expression, expressionSymbol))
        OutlinedTextField(
            value = enteredResult,
            modifier = Modifier
                .size(width = 50.dp, height = 50.dp)
                .focusable(enabled = !showAnswer),
            onValueChange = {
                editText(it, expression.id)
            },
            singleLine = true,
            maxLines = 1,
            textStyle = TextStyle(color = Color.Black, fontSize = 12.sp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            keyboardActions = KeyboardActions(),
            readOnly = showAnswer
        )
        AnimatedVisibility(visible = showAnswer) {
            Surface(
                modifier = Modifier.wrapContentSize(),
                color = if (expression.answer.toString() != enteredResult.filterNot { char -> char == ' ' })
                    Color.Red
                else
                    MaterialTheme.colorScheme.onBackground
            ) {
                Text(
                    text = "${expression.answer}",
                    modifier = Modifier
                        .size(width = 36.dp, height = 22.dp)
                        .padding(start = 12.dp)
                )
            }
        }
    }

}

@Composable
fun ExtraWindow(modifier: Modifier, objectives: @Composable () -> Unit) {
    Surface(
        modifier = modifier
    ) { objectives() }
}

@OptIn(KoinExperimentalAPI::class)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val context = LocalContext.current
    KoinApplication(application = {
        androidContext(context)
        modules(gameModule)
    }) {
        GameScreen(koinViewModel())
    }

}



