package com.example.braintrainercompose.utils.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.braintrainercompose.game.di.gameModule
import com.example.braintrainercompose.game.ui.game_screen.GameScreen
import com.example.braintrainercompose.scores.di.scoreBoard
import com.example.braintrainercompose.scores.ui.score_board.ScoreBoard
import com.example.braintrainercompose.settings.ui.settings.Settings
import com.example.braintrainercompose.utils.BottomNavItems
import com.example.braintrainercompose.utils.BottomNavigation
import com.example.braintrainercompose.utils.di.utils
import com.example.braintrainercompose.utils.ui.theme.BrainTrainerComposeTheme
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startKoin {
            androidContext(this@MainActivity)
            modules(gameModule, utils,scoreBoard)
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

    Scaffold(bottomBar = { BottomNavigation(navController = navController) }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItems.Game.title,
            Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItems.ScoreBoard.title) {
                ScoreBoard(navHostController = navController)
            }
            composable(BottomNavItems.Game.title) {
                GameScreen()
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