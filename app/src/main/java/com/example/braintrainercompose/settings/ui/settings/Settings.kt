package com.example.braintrainercompose.settings.ui.settings

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.braintrainercompose.game.ui.game_screen.GameScreen
import com.example.braintrainercompose.utils.ui.theme.BrainTrainerComposeTheme

@Composable
fun Settings() {
    Text("Settings")
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BrainTrainerComposeTheme {
        Settings()
    }
}