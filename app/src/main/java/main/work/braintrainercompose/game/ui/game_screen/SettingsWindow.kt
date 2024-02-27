package main.work.braintrainercompose.game.ui.game_screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import main.work.braintrainercompose.R
import  main.work.braintrainercompose.game.domain.models.DifficultyLevels
import  main.work.braintrainercompose.game.domain.models.ExpressionType
import  main.work.braintrainercompose.game.domain.models.GameSettings
import  main.work.braintrainercompose.game.ui.models.SettingsMenuElement
import  main.work.braintrainercompose.utils.ui.theme.BrainTrainerComposeTheme


@Composable
fun GameSettings(
    baseGameSettings: GameSettings,
    closeIconClicked: (GameSettings) -> Unit
) {
    var difficultyInChange by remember {
        mutableStateOf(baseGameSettings.difficulty)
    }
    var timeGameInChange by remember {
        mutableStateOf(baseGameSettings.timeGame)
    }
    var countDownInChange by remember {
        mutableStateOf(baseGameSettings.countDown)
    }
    var scoreCountInChange by remember {
        mutableStateOf(baseGameSettings.scoreCount)
    }
    var expressionTypeInChange by remember {
        mutableStateOf(baseGameSettings.expressionType)
    }
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .padding(horizontal = dimensionResource(id = R.dimen.padding_16))
    ) {
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.criss_cross),
                contentDescription = "close icon",
                modifier = Modifier
                    .padding(12.dp)
                    .clickable {
                        closeIconClicked(
                            GameSettings(
                                difficultyInChange,
                                timeGameInChange,
                                countDownInChange,
                                scoreCountInChange,
                                expressionTypeInChange
                            )
                        )
                    },
                tint = MaterialTheme.colorScheme.secondary
            )
        }

        Text(
            modifier = Modifier.padding(bottom = 20.dp),
            text = stringResource(id = R.string.game_settings),
            style = TextStyle(MaterialTheme.colorScheme.onSecondaryContainer).merge(
                MaterialTheme.typography.headlineMedium
            )
        )

        SettingsMenuElement.entries.forEach {
            GameSettingsSegment(
                element = it,
                timeGame = timeGameInChange,
                countDown = countDownInChange,
                scoresCount = scoreCountInChange,
                difficulty = difficultyInChange,
                expressionType = expressionTypeInChange,
                difficultyChanged = { id ->
                    DifficultyLevels.entries.forEach { difficulty ->
                        if (difficulty.id == id) difficultyInChange = difficulty
                    }
                },
                expressionChanged = { id ->
                    ExpressionType.entries.forEach { expression ->
                        if (expression.id == id) expressionTypeInChange = expression
                    }
                }
            ) { state, elementType ->
                when (elementType) {
                    SettingsMenuElement.GAME_TYPE -> timeGameInChange = state
                    SettingsMenuElement.COUNTDOWN -> countDownInChange = state
                    SettingsMenuElement.SCORE_COUNT -> scoreCountInChange = state
                    else -> Unit
                }
            }
        }
        OutlinedButton(
            modifier = Modifier.padding(vertical = 20.dp),
            onClick = {
                closeIconClicked(
                    GameSettings(
                        difficultyInChange,
                        timeGameInChange,
                        countDownInChange,
                        scoreCountInChange,
                        expressionTypeInChange
                    )
                )
            },
            colors = ButtonDefaults
                .outlinedButtonColors(containerColor = MaterialTheme.colorScheme.onSecondary),
            border = BorderStroke(width = 0.5.dp, color = MaterialTheme.colorScheme.outline)
        ) {
            Text(
                text = stringResource(id = R.string.save),
                style = TextStyle(MaterialTheme.colorScheme.onSecondaryContainer).merge(
                    MaterialTheme.typography.headlineLarge
                )
            )
        }
    }


}

@Composable
fun GameSettingsSegment(
    element: SettingsMenuElement,
    timeGame: Boolean,
    countDown: Boolean,
    scoresCount: Boolean,
    difficulty: DifficultyLevels,
    expressionType: ExpressionType,
    difficultyChanged: (Int) -> Unit,
    expressionChanged: (Int) -> Unit,
    onCheckedChanged: (Boolean, SettingsMenuElement) -> Unit
) {
    val difficultyList =
        listOf(
            R.string.easy,
            R.string.normal,
            R.string.hard,
            R.string.impossible
        )
    val expressionsNameList = listOf(
        R.string.addition,
        R.string.subtraction,
        R.string.multiplication,
        R.string.division
    )
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = element.type),
            style = TextStyle(MaterialTheme.colorScheme.onSecondaryContainer).merge(
                MaterialTheme.typography.bodyMedium
            )
        )
        when (element) {
            SettingsMenuElement.DIFFICULTY -> DropDownMenu(
                elementsList = difficultyList,
                elementChanged = difficultyChanged,
                placeholder = R.string.select_difficulty,
                name = stringResource(id = difficulty.id)
            )

            SettingsMenuElement.EXPRESSIONS_TYPE -> DropDownMenu(
                elementsList = expressionsNameList,
                elementChanged = expressionChanged,
                placeholder = R.string.select_expression,
                name = stringResource(id = expressionType.id)
            )

            SettingsMenuElement.COUNTDOWN -> BaseSwitcher(
                stateBase = countDown,
                menuElement = element,
                onCheckedChanged = onCheckedChanged
            )

            SettingsMenuElement.SCORE_COUNT -> BaseSwitcher(
                stateBase = scoresCount,
                menuElement = element,
                onCheckedChanged = onCheckedChanged
            )

            SettingsMenuElement.GAME_TYPE -> BaseSwitcher(
                stateBase = timeGame,
                menuElement = element,
                onCheckedChanged = onCheckedChanged
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenu(
    elementsList: List<Int>,
    elementChanged: (Int) -> Unit,
    placeholder: Int,
    name: String
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    var selectedName by remember {
        mutableStateOf(name)
    }

    ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange = { isExpanded = it }) {
        TextField(
            value = selectedName,
            onValueChange = {},
            placeholder = { Text(text = stringResource(id = placeholder)) },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            modifier = Modifier
                .menuAnchor()
                .padding(start = 20.dp, bottom = 6.dp)
                .wrapContentSize(),
            textStyle = TextStyle(MaterialTheme.colorScheme.onSecondaryContainer).merge(
                MaterialTheme.typography.bodyMedium
            )
        )
        ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
            elementsList.forEach { name ->
                MenuContent(
                    name = stringResource(name),
                    selectedName = selectedName
                ) { clickedName ->
                    selectedName = clickedName
                    elementChanged(name)
                    isExpanded = false
                }
            }
        }
    }

}

@Composable
fun BaseSwitcher(
    stateBase: Boolean,
    menuElement: SettingsMenuElement,
    onCheckedChanged: (Boolean, SettingsMenuElement) -> Unit
) {
    Switch(checked = stateBase, onCheckedChange = { onCheckedChanged(it, menuElement) })
}

@Composable
fun MenuContent(
    name: String,
    selectedName: String,
    menuContentClicked: (String) -> Unit
) {
    AnimatedContent(
        targetState = selectedName == name,
        label = "Animate selected item"
    ) { isSelected ->
        if (isSelected) {
            DropdownMenuItem(
                text = {
                    Text(
                        text = name,
                        style = TextStyle(MaterialTheme.colorScheme.onSecondaryContainer).merge(
                            MaterialTheme.typography.bodyMedium
                        ),
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                },
                onClick = { menuContentClicked(name) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = "Included items icon"
                    )
                })
        } else {
            DropdownMenuItem(
                text = {
                    Text(
                        text = name,
                        style = TextStyle(MaterialTheme.colorScheme.onSecondaryContainer).merge(
                            MaterialTheme.typography.bodyMedium
                        )
                    )
                },
                onClick = { menuContentClicked(name) })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    BrainTrainerComposeTheme {
        GameSettings(GameSettings()) {}
    }
}