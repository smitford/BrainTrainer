package main.work.braintrainercompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import main.work.braintrainercompose.scores.domain.models.SessionHistory
import main.work.braintrainercompose.scores.ui.score_board.History
import main.work.braintrainercompose.utils.ui.theme.BrainTrainerComposeTheme

@Composable
fun ScoreBoardTest(sessionHistoryList: List<SessionHistory>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 50.dp)
                .padding(top = dimensionResource(id = R.dimen.padding_16)),
            shape = RectangleShape
        )
        {
            Text(
                text = "ScoreBoard", modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.padding_16),
                ), style = TextStyle(MaterialTheme.colorScheme.primary).merge(
                    MaterialTheme.typography.titleLarge
                )
            )
        }

        History(sessionHistoryList = sessionHistoryList) { }
    }
}

@Preview(showBackground = true)
@Composable
fun ScoreScreenPreview(
) {
    BrainTrainerComposeTheme(darkTheme = false) {
        ScoreBoardTest(
            listOf(
                SessionHistory(
                    id = 1,
                    userName = "Egorka",
                    score = "1000",
                    difficulty = "Easy"
                ),
                SessionHistory(
                    id = 1,
                    userName = "Egorka",
                    score = "1000",
                    difficulty = "Easy"
                ),
                SessionHistory(id = 1, userName = "Egorka", score = "1000", difficulty = "Easy")
            )
        )
    }
}