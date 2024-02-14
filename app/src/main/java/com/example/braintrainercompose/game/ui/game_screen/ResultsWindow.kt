package com.example.braintrainercompose.game.ui.game_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.braintrainercompose.R
import com.example.braintrainercompose.game.domain.models.GameResults
import com.example.braintrainercompose.utils.getDoubleDotsString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultsWindow(
    resultOfGame: GameResults,
    name: String,
    nameChanged: (String) -> Unit,
    closeIconClicked: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
    ) {
        Surface(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.criss_cross),
                contentDescription = "close icon",
                alignment = Alignment.TopEnd,
                modifier = Modifier
                    .padding(12.dp)
                    .clickable {
                        closeIconClicked()
                    }
            )
        }
        Text(text = stringResource(id = R.string.game_result))
        TextField(
            value = name,
            onValueChange = { nameChanged(it) },
            modifier = Modifier.focusRequester(focusRequester = FocusRequester.Default)
        )
        Row {
            Text(
                text = getDoubleDotsString(
                    string1 = stringResource(id = R.string.score),
                    string2 = resultOfGame.score
                )
            )
            Text(
                text = getDoubleDotsString(
                    string1 = stringResource(id = R.string.time),
                    string2 = resultOfGame.time
                )
            )
        }

        Button(onClick = {
            closeIconClicked(
            )
        }) {
            Text(text = stringResource(id = R.string.save))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResultsPreview() {
    ResultsWindow(GameResults(), "", {}) {
    }
}