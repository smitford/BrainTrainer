package com.example.braintrainercompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.braintrainercompose.scores.domain.models.SessionHistory
import com.example.braintrainercompose.scores.ui.score_board.History
import com.example.braintrainercompose.utils.ui.theme.BrainTrainerComposeTheme

@Composable
fun ScoreBoardTest(sessionHistoryList: List<SessionHistory>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Text("ScoreBoard")
        }
        /*        if (true)
                    EmptyHistory { }
                else*/
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
                SessionHistory(id = 1, userName = "Egorka", score = "1000", difficulty = "Easy"),
                SessionHistory(id = 1, userName = "Egorka", score = "1000", difficulty = "Easy"),
                SessionHistory(id = 1, userName = "Egorka", score = "1000", difficulty = "Easy")
            )
        )
    }
}