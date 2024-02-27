package main.work.braintrainercompose.utils.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import main.work.braintrainercompose.game.di.gameModule
import main.work.braintrainercompose.game.ui.game_screen.GameScreen
import main.work.braintrainercompose.scores.di.scoreBoard
import main.work.braintrainercompose.scores.ui.score_board.ScoreBoard
import main.work.braintrainercompose.settings.ui.settings.Settings
import main.work.braintrainercompose.utils.BottomNavItems
import main.work.braintrainercompose.utils.BottomNavigation
import main.work.braintrainercompose.utils.di.utils
import main.work.braintrainercompose.utils.ui.theme.BrainTrainerComposeTheme
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startKoin {
            androidContext(this@MainActivity)
            modules(gameModule, utils, scoreBoard)
        }
        setContent {
            BrainTrainerComposeTheme {
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

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
            AnimatedVisibility(visible = true) {
                BottomNavigation(navController = navController)
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItems.Game.title,
            Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItems.ScoreBoard.title) {
                ScoreBoard(navHostController = navController)
            }
            composable(BottomNavItems.Game.title) {
                GameScreen(snackBarHostState = snackbarHostState, scope = scope)
            }
            composable(BottomNavItems.Settings.title) {
                Settings()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BrainTrainerComposeTheme {
        MainApp()
    }
}