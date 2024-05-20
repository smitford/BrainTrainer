package main.work.braintrainercompose.scores.ui.score_board

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import main.work.braintrainercompose.R
import main.work.braintrainercompose.scores.domain.models.GamesHistory
import main.work.braintrainercompose.scores.domain.models.SessionHistory
import main.work.braintrainercompose.scores.ui.models.TableTitle
import main.work.braintrainercompose.scores.ui.models.tableTabsRow
import main.work.braintrainercompose.scores.ui.view_model.ScoreBoard
import main.work.braintrainercompose.utils.domain.models.GameType
import main.work.braintrainercompose.utils.ui.theme.BrainTrainerComposeTheme
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScoreBoard(viewModel: ScoreBoard = koinViewModel(), navHostController: NavHostController) {
    val status = viewModel.status.observeAsState()
    val coroutineScope = rememberCoroutineScope()
    var tabRowState by rememberSaveable {
        mutableStateOf(0)
    }
    val pagerState = rememberPagerState(
        pageCount = {
            3
        },
        initialPage = 1
    )

    LaunchedEffect(key1 = pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            tabRowState = page - 1
        }
    }

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
                text = stringResource(id = R.string.score_board),
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.padding_16),
                ),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
        if (status.value?.isEmpty == true) EmptyHistory { navHostController.popBackStack() }
        else {
            TableTabRow(
                selectedTabIndex = tabRowState,
                modifier = Modifier,
                changeState = { newState ->
                    tabRowState = newState
                    coroutineScope.launch { pagerState.scrollToPage(newState + 1) }
                })
            HorizontalTablePager(
                tablePagerState = pagerState,
                gamesHistory = status.value!!.sessionHistoryList!!,
                clearHistory = { viewModel.clearHistory() }
            )

        }
    }

}

@Composable
fun History(
    sessionHistoryList: List<SessionHistory>,
    tableList: List<TableTitle>,
    historyEvent: () -> Unit
) {
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
                TableElement(
                    tableElement = null,
                    modifier = tableHeaderModifier,
                    tableList = tableList
                )
            }
            items(sessionHistoryList.size) { index ->
                TableElement(
                    tableElement = sessionHistoryList[index],
                    modifier = tableElementModifier,
                    tableList = tableList
                )
            }
        }
        ButtonTemplate(textId = R.string.clear_history, onClickEvent = historyEvent)
    }
}

@Composable
fun TableElement(
    tableElement: SessionHistory?,
    modifier: Modifier,
    tableList: List<TableTitle>
) {
    val isTimeNull = tableElement?.time == null
    val tableDataList = if (isTimeNull) listOf(
        tableElement?.userName ?: "-",
        tableElement?.accuracy.toString(),
        tableElement?.difficulty ?: "-",
        tableElement?.score ?: "-"
    ) else
        listOf(
            tableElement?.userName ?: "-",
            tableElement?.accuracy.toString(),
            tableElement?.difficulty ?: "-",
            tableElement?.score ?: "-",
            tableElement?.time ?: "-"
        )


    Row(
        horizontalArrangement = Arrangement.Absolute.SpaceBetween, modifier =
        modifier.padding(horizontal = 2.dp, vertical = 2.dp)
    ) {
        if (tableElement == null) tableList.forEach { tableTitle ->
            TableElementText(
                text = stringResource(id = tableTitle.textId),
                modifier = if (isTimeNull)
                    Modifier.fillMaxWidth(tableTitle.weightNoTime)
                else
                    Modifier.fillMaxWidth(tableTitle.weightTime)
            )
        }
        else tableList.forEachIndexed { index, tableTitle ->
            val tableModifier =
                if (isTimeNull)
                    Modifier.fillMaxWidth(tableTitle.weightNoTime)
                else
                    Modifier.fillMaxWidth(tableTitle.weightTime)

            when (index) {
                2 -> TableElementIcon(
                    iconId = tableDataList[index],
                    modifier = tableModifier
                )

                else -> TableElementText(
                    text = tableDataList[index],
                    modifier = tableModifier
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
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.tertiary
    )
}

@Composable
fun TableElementIcon(iconId: String, modifier: Modifier) {
    Icon(
        painter = painterResource(id = iconId.toInt()),
        contentDescription = "Difficulty lvl icon",
        modifier = modifier.size(20.dp)
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
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )
        ButtonTemplate(textId = R.string.try_now, emptyHistoryEvent)
    }
}

@Composable
fun ButtonTemplate(textId: Int, onClickEvent: () -> Unit) {
    Button(onClick = { onClickEvent() }, modifier = Modifier.padding(vertical = 12.dp)) {
        Text(
            text = stringResource(id = textId),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun TableTabRow(selectedTabIndex: Int, modifier: Modifier, changeState: (Int) -> Unit) {
    ScrollableTabRow(selectedTabIndex = selectedTabIndex, modifier = modifier.fillMaxWidth()) {
        tableTabsRow.forEachIndexed { index, tableTabs ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { changeState(index) },
                text = { Text(text = stringResource(id = tableTabs.id)) }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalTablePager(
    tablePagerState: PagerState,
    gamesHistory: GamesHistory,
    clearHistory: () -> Unit
) {
    HorizontalPager(state = tablePagerState, modifier = Modifier.fillMaxWidth()) { page ->
        when (page) {
            1 -> {
                val tableTitleList = listOf(
                    TableTitle.NickName,
                    TableTitle.Accuracy,
                    TableTitle.Difficulty,
                    TableTitle.Score,
                )
                History(
                    sessionHistoryList = gamesHistory.freeGamesHistory,
                    tableList = tableTitleList
                ) { clearHistory() }
            }

            2 -> {
                val tableTitleList = listOf(
                    TableTitle.NickName,
                    TableTitle.Accuracy,
                    TableTitle.Difficulty,
                    TableTitle.Score,
                    TableTitle.Time
                )
                History(
                    sessionHistoryList = gamesHistory.timedGamesHistory,
                    tableList = tableTitleList
                ) { clearHistory() }
            }

            3 -> {
                val tableTitleList = listOf(
                    TableTitle.NickName,
                    TableTitle.Accuracy,
                    TableTitle.Difficulty,
                    TableTitle.Score,
                    TableTitle.Time
                )
                History(
                    sessionHistoryList = gamesHistory.timeRaceGamesHistory,
                    tableList = tableTitleList
                ) { clearHistory() }
            }
        }
    }
}

@Preview
@Composable
fun TableTabRowPreview() {
    TableTabRow(0, modifier = Modifier) { n -> }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun TablePreview() {
    val pagerState = rememberPagerState(
        pageCount = {
            3
        },
        initialPage = 1
    )
    val gamesHistory = GamesHistory(
        freeGamesHistory = listOf(
            SessionHistory(
                id = 1,
                userName = "Max",
                score = "12",
                difficulty = R.drawable.c.toString(),
                accuracy = 100F,
                time = null,
                gameType = GameType.FREE_GAME
            )
        ),
        timedGamesHistory = listOf(
            SessionHistory(
                id = 1,
                userName = "Max",
                score = "12",
                difficulty = R.drawable.c.toString(),
                accuracy = 100F,
                time = "01:22",
                gameType = GameType.TIME_GAME
            )
        ),
        timeRaceGamesHistory = listOf(
            SessionHistory(
                id = 1,
                userName = "Max",
                score = "12",
                difficulty = R.drawable.c.toString(),
                accuracy = 100F,
                time = "01:22",
                gameType = GameType.TIME_RACE
            )
        )
    )
    BrainTrainerComposeTheme(checkShared = { /*TODO*/ }) {
        HorizontalTablePager(
            tablePagerState = pagerState, gamesHistory = gamesHistory
        ) {
        }
    }

}