package main.work.braintrainercompose.game.ui.game_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import main.work.braintrainercompose.R
import main.work.braintrainercompose.game.domain.models.GameResults
import main.work.braintrainercompose.utils.getDoubleDotsString

@Composable
fun ResultsWindow(
    resultOfGame: GameResults,
    name: String,
    nameChanged: (String) -> Unit,
    saveIconClicked: () -> Unit,
    closeIconClicked: () -> Unit,
) {
    var openDialog by remember { mutableStateOf(false) }

    when {
        openDialog ->
            CloseWindowConformationDialog(
                onDismissRequest = {
                    closeIconClicked()
                    openDialog = false
                },
                onConfirmation = {
                    saveIconClicked()
                    openDialog = false
                },
                dialogTitle = stringResource(id = R.string.warning),
                dialogText = stringResource(id = R.string.close_results_warning),
                dismissButtonText = stringResource(id = R.string.yes),
                confirmButtonText = stringResource(id = R.string.no_save)
            )
    }

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
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
                        if (name.isEmpty()) {
                            closeIconClicked()
                        } else {
                            openDialog = true
                        }
                    }
                    .align(Alignment.End),
                tint = MaterialTheme.colorScheme.secondary
            )
        }
        Text(
            modifier = Modifier.padding(bottom = 20.dp),
            text = stringResource(id = R.string.game_result),
            style = TextStyle(MaterialTheme.colorScheme.onSecondaryContainer).merge(
                MaterialTheme.typography.headlineMedium
            )
        )
        BasicTextField(
            value = name,
            maxLines = 1,
            singleLine = true,
            onValueChange = { nameChanged(it) },
            modifier = Modifier
                // .focusRequester(focusRequester = FocusRequester.Default)
                .fillMaxWidth()
                .padding(horizontal = 52.dp, vertical = 28.dp)
                .background(
                    color = MaterialTheme.colorScheme.onPrimary,
                    shape = RoundedCornerShape(4.dp)
                ),
            textStyle = MaterialTheme.typography.bodyMedium.merge(
                TextStyle(
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    textAlign = TextAlign.Start,
                    textIndent = TextIndent(firstLine = 12.sp)
                )
            ),
            decorationBox = { innerTextField ->
                if (name.isEmpty())
                    Text(
                        text = stringResource(id = R.string.nick_name),
                        style = TextStyle(MaterialTheme.colorScheme.onSecondaryContainer).merge(
                            MaterialTheme.typography.bodySmall
                        ),
                        modifier = Modifier
                            .height(30.dp)
                            .alpha(0.5F)
                            .padding(start = 12.dp)
                    )
                innerTextField.invoke()
            }
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 52.dp)
        ) {
            Text(
                text = getDoubleDotsString(
                    string1 = stringResource(id = R.string.score),
                    string2 = resultOfGame.score
                ),
                style = TextStyle(MaterialTheme.colorScheme.onSecondaryContainer).merge(
                    MaterialTheme.typography.bodyMedium
                )
            )
            Text(
                text = getDoubleDotsString(
                    string1 = stringResource(id = R.string.time),
                    string2 = resultOfGame.time
                ),
                style = TextStyle(MaterialTheme.colorScheme.onSecondaryContainer).merge(
                    MaterialTheme.typography.bodyMedium
                )
            )
        }

        OutlinedButton(
            modifier = Modifier
                .padding(vertical = 20.dp)
                .imePadding(),
            onClick = {
                saveIconClicked()
            }, colors = ButtonDefaults
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
fun CloseWindowConformationDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    dismissButtonText: String,
    confirmButtonText: String
) {
    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(onClick = { onConfirmation() }) {
                Text(text = confirmButtonText)
            }
        },
        title = { Text(text = dialogTitle) },
        text = { Text(text = dialogText) },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(text = dismissButtonText)
            }
        }
    )
}
/*
@Preview(showBackground = true)
@Composable
fun ResultsPreview() {
    BrainTrainerComposeTheme {
        ResultsWindow(GameResults(), "", {}, {}) {
        }
    }
}*/
