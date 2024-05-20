package main.work.braintrainercompose.game.ui.game_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import main.work.braintrainercompose.R
import main.work.braintrainercompose.game.domain.models.GameResults
import main.work.braintrainercompose.game.domain.models.GameSettings
import main.work.braintrainercompose.game.domain.models.MathExpression
import main.work.braintrainercompose.game.ui.models.ExtraWindowStatus
import main.work.braintrainercompose.game.ui.models.GameProgress
import main.work.braintrainercompose.game.ui.view_model.GameViewModel
import main.work.braintrainercompose.utils.EventListener
import main.work.braintrainercompose.utils.getExpression
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalComposeUiApi::class, ExperimentalLayoutApi::class)
@Composable
fun GameScreen(
    bottomBarSynchronizer: (GameProgress) -> Unit,
    bottomBarVisibilityChanger: (Boolean) -> Unit,
    snackBarHostState: SnackbarHostState,
    scope: CoroutineScope,
    innerPadding: PaddingValues,
    playButtonClicked: Boolean,
    viewModel: GameViewModel = koinViewModel(
        parameters = { parametersOf(bottomBarSynchronizer) }
    ),
    playButtonClickRefresher: () -> Unit
) {
    val gameState = viewModel.getState().observeAsState()
    val focusManager = LocalFocusManager.current
    var extraWindowStatus by remember {
        mutableStateOf(ExtraWindowStatus.CLOSED)
    }
    val snackbarText = stringResource(id = R.string.save_results_warning)
    val blurModifier = Modifier
        .fillMaxSize()
        .blur(10.dp)
        .alpha(0.5F)
        .clickable { extraWindowStatus = ExtraWindowStatus.CLOSED }
    val usualModifier = Modifier
        .fillMaxSize()
    var enteredName by rememberSaveable {
        mutableStateOf("")
    }

    EventListener {
        when (it) {
            Lifecycle.Event.ON_PAUSE -> {
                if (gameState.value?.gamesProgress == GameProgress.IN_PROGRESS) {
                    viewModel.pauseGame()
                    playButtonClickRefresher()
                }
            }

            else -> {}
        }
    }

    if (gameState.value?.gamesProgress == GameProgress.STOPPED
        && gameState.value?.settings?.scoreCount != false
    ) {
        extraWindowStatus =
            ExtraWindowStatus.RESULTS_WINDOW
    }

    if (playButtonClicked) {
        when (gameState.value?.gamesProgress) {
            GameProgress.NOT_STARTED, GameProgress.SAVED, GameProgress.STOPPED -> {
                viewModel.getExpression()
                if (gameState.value!!.settings.timeGame) viewModel.timerRefresher()
            }

            GameProgress.IN_PROGRESS -> {
                viewModel.pauseGame()
            }

            GameProgress.PAUSED -> {
                viewModel.continueGame()
            }

            else -> Unit
        }
        playButtonClickRefresher()
    }

    Box {
        val extraWindowModifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 10.dp)
            .clip(RoundedCornerShape(16.dp))
            .align(Alignment.Center)
            .background(MaterialTheme.colorScheme.secondaryContainer)

        Card(
            modifier = when (extraWindowStatus) {
                ExtraWindowStatus.SETTINGS_WINDOW, ExtraWindowStatus.RESULTS_WINDOW -> blurModifier
                else -> usualModifier
            },
            shape = RectangleShape,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
        ) {
            TopBar(
                currentTime = gameState.value?.timer ?: "00:00",
                menuIconClicked = {
                    extraWindowStatus = ExtraWindowStatus.SETTINGS_WINDOW
                    if (gameState.value?.gamesProgress == GameProgress.IN_PROGRESS)
                        viewModel.pauseGame()
                },
                gameProgress = gameState.value?.gamesProgress ?: GameProgress.NOT_STARTED
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(if (LocalDensity.current.fontScale > 1F) 1 else 2),
                modifier = Modifier
                    .consumeWindowInsets(innerPadding)
                    .fillMaxSize()
                    .imePadding()
                    .imeNestedScroll()
                    .padding(horizontal = 6.dp)
            ) {
                gameState.value?.expression?.let { expressionList ->
                    items(expressionList) { item ->
                        TableElement(
                            expression = item,
                            expressionSymbol = gameState.value?.settings?.expressionType?.symbol
                                ?: "+",
                            enteredResult = gameState.value?.answers?.get(item.id) ?: "",
                            showAnswer = gameState.value!!.gapsFiled,
                            gameIsPaused = gameState.value?.gamesProgress == GameProgress.PAUSED,
                            focusManager = focusManager
                        ) { text, id ->
                            if (text.length < 5)
                                viewModel.addNewAnswer(id = id, answer = text)
                        }
                    }
                }
            }
        }
        when (extraWindowStatus) {
            ExtraWindowStatus.RESULTS_WINDOW -> {
                ExtraWindow(extraWindowModifier) {
                    ResultsWindow(resultOfGame = gameState.value?.gameResults ?: GameResults(),
                        name = enteredName,
                        nameChanged = { name -> if (name.length < 16) enteredName = name },
                        saveIconClicked = {
                            if (enteredName.isEmpty()) {
                                scope.launch {
                                    snackBarHostState.showSnackbar(snackbarText)
                                }
                            } else {
                                viewModel.saveResults(enteredName)
                                enteredName = ""
                                extraWindowStatus = ExtraWindowStatus.CLOSED
                            }
                        }
                    ) {
                        viewModel.saveMode()
                        enteredName = ""
                        extraWindowStatus = ExtraWindowStatus.CLOSED
                    }
                }
                bottomBarVisibilityChanger(false)
            }

            ExtraWindowStatus.SETTINGS_WINDOW -> {
                ExtraWindow(extraWindowModifier) {
                    GameSettings(
                        baseGameSettings = gameState.value?.settings ?: GameSettings()
                    ) { gameSettingsNew ->
                        if (gameSettingsNew != gameState.value?.settings) {
                            viewModel.saveGameSettings(gameSettingsNew)
                            viewModel.getSettings()
                        }
                        extraWindowStatus = ExtraWindowStatus.CLOSED
                    }
                }
                bottomBarVisibilityChanger(false)
            }

            else -> {
                bottomBarVisibilityChanger(true)
            }
        }
    }
}

@Composable
fun TopBar(
    currentTime: String,
    menuIconClicked: () -> Unit,
    gameProgress: GameProgress
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 16.dp)
            .padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TimeTable(currentTime = currentTime)
        when (gameProgress) {
            GameProgress.PAUSED -> Text(
                text = stringResource(id = R.string.pause),
                color = MaterialTheme.colorScheme.primary
            )

            else -> {}
        }

        Icon(
            modifier = Modifier
                .padding(end = 15.dp)
                .clickable { menuIconClicked() },
            imageVector = Icons.Default.Menu,
            contentDescription = "game_settings",
            tint = MaterialTheme.colorScheme.primary
        )
    }

}

@Composable
fun TimeTable(currentTime: String) {
    Row(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.timer),
            contentDescription = "timer",
            modifier = Modifier.padding(
                horizontal = 10.dp, vertical = 3.dp
            ),
            tint = MaterialTheme.colorScheme.primary
        )

        Text(
            text = currentTime,
            modifier = Modifier.padding(
                horizontal = 4.dp, vertical = 3.dp
            ),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun TableElement(
    expression: MathExpression,
    enteredResult: String,
    expressionSymbol: String,
    gameIsPaused: Boolean,
    showAnswer: Boolean,
    focusManager: FocusManager,
    editText: (String, Int) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val isWrong = expression.answer.toString() != enteredResult.filterNot { char -> char == ' ' }
    if (showAnswer) focusManager.clearFocus()
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp)
    ) {
        Text(
            text = getExpression(expression, expressionSymbol),
            modifier = Modifier.fillMaxWidth(0.5F),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )
        BasicTextField(
            value = enteredResult,
            onValueChange = { newText ->
                editText(newText, expression.id)
            },
            modifier = Modifier
                .defaultMinSize(minWidth = 36.dp, minHeight = 22.dp)
                .fillMaxWidth(0.45F)
                .focusable(enabled = !showAnswer)
                .background(
                    color = if (isWrong && showAnswer) MaterialTheme.colorScheme.errorContainer
                    else MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(4.dp)
                )
                .border(
                    width = 0.5.dp,
                    shape = RoundedCornerShape(4.dp),
                    color = MaterialTheme.colorScheme.outline
                ),
            textStyle = MaterialTheme.typography.bodyLarge.merge(
                TextStyle(
                    color = if (isWrong && showAnswer) MaterialTheme.colorScheme.onErrorContainer
                    else MaterialTheme.colorScheme.onPrimaryContainer,
                    textAlign = TextAlign.Center
                )
            ),
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
            }),
            readOnly = showAnswer || gameIsPaused
        )
        if (isWrong) AnimatedVisibility(visible = showAnswer) {
            Surface(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .wrapContentSize(align = Alignment.Center),
                color = MaterialTheme.colorScheme.tertiaryContainer
            ) {
                Text(
                    text = "${expression.answer}",
                    modifier = Modifier
                        .wrapContentSize()
                        .defaultMinSize(minWidth = 36.dp, minHeight = 22.dp)
                        .align(Alignment.CenterVertically),
                    style = MaterialTheme.typography.bodyLarge
                        .merge(
                            TextStyle(
                                color = MaterialTheme.colorScheme.onTertiaryContainer,
                                textAlign = TextAlign.Center
                            )
                        )
                )
            }
        }
    }

}

@Composable
fun ExtraWindow(modifier: Modifier, objectives: @Composable () -> Unit) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.onSecondary
    ) { objectives() }
}




