package com.example.braintrainercompose.scores.ui.score_board

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.braintrainercompose.R
import com.example.braintrainercompose.scores.domain.models.SessionHistory
import com.example.braintrainercompose.scores.ui.models.TableTitleElement
import com.example.braintrainercompose.scores.ui.view_model.ScoreBoard
import org.koin.androidx.compose.getViewModel

@Composable
fun ScoreBoard(viewModel: ScoreBoard = getViewModel(), navHostController: NavHostController) {

    val status = viewModel.status.observeAsState()
    viewModel.getHistory()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 50.dp)
                .padding(top = dimensionResource(id = R.dimen.padding_16))
        ) {
            Text(
                text = "ScoreBoard", modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.padding_16)
                )
            )
        }
        if (status.value?.isEmpty == true) EmptyHistory { navHostController.navigate(route = "Play") }
        else status.value!!.sessionHistoryList?.let { History(sessionHistoryList = it) { viewModel.clearHistory() } }
    }

}

@Composable
fun History(sessionHistoryList: List<SessionHistory>, historyEvent: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.padding_16), vertical = 28.dp)
    ) {
        LazyColumn {
            item {
                TableElement(tableElement = null, elementIndex = 0)
            }
            items(sessionHistoryList.size) { index ->
                TableElement(tableElement = sessionHistoryList[index], elementIndex = index + 1)
            }
        }
        ButtonTemplate(textId = R.string.clear_history, onClickEvent = historyEvent)
    }
}

@Composable
fun TableElement(tableElement: SessionHistory?, elementIndex: Int) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
    ) {
        if (tableElement == null) TableTitleElement.entries.forEach { element ->
            Text(text = stringResource(id = element.textId), textAlign = TextAlign.Center)
        }
        else TableTitleElement.entries.forEach { element ->
            when (element) {
                TableTitleElement.DIFFICULTY -> TableElementText(text = tableElement.difficulty)
                TableTitleElement.NICK_NAME -> TableElementText(text = tableElement.userName)
                TableTitleElement.NUMBER -> TableElementText(text = elementIndex.toString())
                TableTitleElement.SCORE -> TableElementText(text = tableElement.score)
            }
        }
    }
}

@Composable
fun TableElementText(text: String) {
    Text(text = text, textAlign = TextAlign.Start)
}

@Composable
fun EmptyHistory(emptyHistoryEvent: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.empty_board),
            contentDescription = "empty_history"
        )
        Text(
            text = stringResource(id = R.string.empty_games_results), textAlign = TextAlign.Center
        )
        ButtonTemplate(textId = R.string.try_now, emptyHistoryEvent)
    }
}

@Composable
fun ButtonTemplate(textId: Int, onClickEvent: () -> Unit) {
    Button(onClick = { onClickEvent() }) {
        Text(text = stringResource(id = textId))
    }
}

