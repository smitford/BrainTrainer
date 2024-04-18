package main.work.braintrainercompose.scores.ui.score_board

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import main.work.braintrainercompose.R
import main.work.braintrainercompose.scores.domain.models.SessionHistory
import main.work.braintrainercompose.scores.ui.models.TableTitleElement
import main.work.braintrainercompose.scores.ui.view_model.ScoreBoard
import org.koin.androidx.compose.koinViewModel

@Composable
fun ScoreBoard(viewModel: ScoreBoard = koinViewModel(), navHostController: NavHostController) {
    val status = viewModel.status.observeAsState()
    viewModel.getHistory()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 50.dp)
                .padding(top = dimensionResource(id = R.dimen.padding_16)), shape = RectangleShape
        ) {
            Text(
                text = stringResource(id = R.string.score_board), modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.padding_16),
                ), style = TextStyle(MaterialTheme.colorScheme.primary).merge(
                    MaterialTheme.typography.titleLarge
                )
            )
        }
        if (status.value?.isEmpty == true) EmptyHistory { navHostController.navigate(route = "Play") }
        else status.value!!.sessionHistoryList?.let { History(sessionHistoryList = it) { viewModel.clearHistory() } }
    }

}

@Composable
fun History(sessionHistoryList: List<SessionHistory>, historyEvent: () -> Unit) {
    val tableHeaderModifier = Modifier
        .fillMaxWidth()
        .background(color = MaterialTheme.colorScheme.primaryContainer)
    val tableElementModifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 6.dp)

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.padding_16), vertical = 28.dp)
    ) {
        LazyColumn {
            item {
                TableElement(tableElement = null, elementIndex = 0, modifier = tableHeaderModifier)
            }
            items(sessionHistoryList.size) { index ->
                TableElement(
                    tableElement = sessionHistoryList[index],
                    elementIndex = index + 1,
                    modifier = tableElementModifier
                )
            }
        }
        ButtonTemplate(textId = R.string.clear_history, onClickEvent = historyEvent)
    }
}

@Composable
fun TableElement(tableElement: SessionHistory?, elementIndex: Int, modifier: Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween, modifier = modifier
    ) {
        if (tableElement == null) TableTitleElement.entries.forEach { element ->
            Text(
                text = stringResource(id = element.textId),
                textAlign = TextAlign.Center,
                style = TextStyle(MaterialTheme.colorScheme.primary).merge(
                    MaterialTheme.typography.headlineLarge
                )
            )
        }
        else TableTitleElement.entries.forEach { element ->
            when (element) {
                TableTitleElement.DIFFICULTY -> TableElementText(
                    text = tableElement.difficulty,
                    modifier = Modifier
                )

                TableTitleElement.NICK_NAME -> TableElementText(
                    text = tableElement.userName,
                    modifier = Modifier
                )

                TableTitleElement.NUMBER -> TableElementText(
                    text = elementIndex.toString(),
                    modifier = Modifier
                )

                TableTitleElement.SCORE -> TableElementText(
                    text = tableElement.score,
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
fun TableElementText(text: String, modifier: Modifier) {
    Text(
        text = text,
        modifier = modifier,
        textAlign = TextAlign.Start,
        style = TextStyle(MaterialTheme.colorScheme.tertiary).merge(
            MaterialTheme.typography.bodyMedium
        )
    )
}

@Composable
fun EmptyHistory(emptyHistoryEvent: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.empty_board),
            contentDescription = "empty_history",
            modifier = Modifier.padding(top = 84.dp, bottom = 12.dp)
        )
        Text(
            modifier = Modifier.padding(vertical = 12.dp),
            text = stringResource(id = R.string.empty_games_results),
            textAlign = TextAlign.Center,
            style = TextStyle(MaterialTheme.colorScheme.primary).merge(
                MaterialTheme.typography.headlineLarge
            )
        )
        ButtonTemplate(textId = R.string.try_now, emptyHistoryEvent)
    }
}

@Composable
fun ButtonTemplate(textId: Int, onClickEvent: () -> Unit) {
    Button(onClick = { onClickEvent() }, modifier = Modifier.padding(vertical = 12.dp)) {
        Text(
            text = stringResource(id = textId),
            style = TextStyle(MaterialTheme.colorScheme.onPrimary).merge(
                MaterialTheme.typography.headlineLarge
            )
        )
    }
}

