package main.work.braintrainercompose.utils.ui

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import main.work.braintrainercompose.game.ui.game_screen.GameScreen
import main.work.braintrainercompose.game.ui.models.GameProgress
import main.work.braintrainercompose.scores.ui.score_board.ScoreBoard
import main.work.braintrainercompose.settings.ui.screen.Settings
import main.work.braintrainercompose.utils.BottomNavigation
import main.work.braintrainercompose.utils.DataUtils
import main.work.braintrainercompose.utils.DataUtils.Companion.THEME_SETTINGS
import main.work.braintrainercompose.utils.DataUtils.Companion.WEB_SCREEN_ROUT
import main.work.braintrainercompose.utils.domain.models.BottomBarState
import main.work.braintrainercompose.utils.domain.models.BottomNavItems
import main.work.braintrainercompose.utils.ui.theme.BrainTrainerComposeTheme
import main.work.braintrainercompose.web_view.WebViewScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        val sharedPref = applicationContext.getSharedPreferences(
            DataUtils.APP_SETTINGS,
            Application.MODE_PRIVATE
        )
        val isDarkTheme = sharedPref.getBoolean(THEME_SETTINGS, false)

        setContent {
            BrainTrainerComposeTheme(darkTheme = isDarkTheme) {
                MainApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp() {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    var bottomBarState by rememberSaveable {
        mutableStateOf(BottomBarState.PLAY_ICON)
    }
    var playButtonClicked by rememberSaveable { mutableStateOf(false) }
    var bottomBarVisibility by rememberSaveable {
        mutableStateOf(true)
    }

    Scaffold(
        modifier = Modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
            when (currentRoute(navController)) {
                WEB_SCREEN_ROUT -> {}
                else -> AnimatedVisibility(
                    visible = bottomBarVisibility,
                    enter = expandVertically(expandFrom = Alignment.Bottom),
                    exit = shrinkVertically(shrinkTowards = Alignment.Bottom)
                ) {
                    BottomNavigation(
                        navController = navController,
                        items = bottomBarState.bottomBarElements,
                        onSameDestinationClick = { buttonClicked ->
                            playButtonClicked = buttonClicked
                        }
                    )
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItems.GamePlay.title,
            Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItems.ScoreBoard.title) {
                ScoreBoard(navHostController = navController)
            }
            composable(BottomNavItems.GamePlay.title) {
                GameScreen(
                    bottomBarSynchronizer = { gameProgress ->
                        bottomBarState = changeStateBaseOnGameProgress(
                            gameProgress
                        )
                        Log.d("Bottom Bar", bottomBarState.toString())
                    },
                    snackBarHostState = snackbarHostState,
                    scope = scope,
                    innerPadding = innerPadding,
                    playButtonClicked = playButtonClicked,
                    bottomBarVisibilityChanger = { visibility -> bottomBarVisibility = visibility }
                ) {
                    playButtonClicked = false
                }
            }
            composable(BottomNavItems.Settings.title) {
                Settings(navHostController = navController)
            }
            composable(route = "Web View") {
                WebViewScreen()
            }
        }
    }
}

fun changeStateBaseOnGameProgress(
    gameProgress: GameProgress,
): BottomBarState =
    when (gameProgress) {
        GameProgress.IN_PROGRESS -> BottomBarState.PAUSE_ICON
        GameProgress.NOT_STARTED, GameProgress.PAUSED -> BottomBarState.PLAY_ICON
        GameProgress.SAVED -> BottomBarState.RESTART_ICON
        GameProgress.STOPPED -> BottomBarState.RESTART_ICON
        else -> BottomBarState.PAUSE_ICON
    }

@Composable
fun currentRoute(navController: NavController): String? {
    return navController.currentDestination?.route
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BrainTrainerComposeTheme(false) {
        MainApp()
    }
}