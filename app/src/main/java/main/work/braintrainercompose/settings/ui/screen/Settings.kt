package main.work.braintrainercompose.settings.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import main.work.braintrainercompose.R
import main.work.braintrainercompose.utils.DataUtils.Companion.WEB_SCREEN_ROUT
import main.work.braintrainercompose.utils.ui.theme.BrainTrainerComposeTheme

@Composable
fun Settings(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = NavHostController(LocalContext.current)
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.padding_16)),
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
                text = stringResource(id = R.string.settings),
                modifier = Modifier,
                style = TextStyle(MaterialTheme.colorScheme.primary).merge(
                    MaterialTheme.typography.titleLarge
                )
            )
        }
        DarkThemeSwitcher(modifier)
        Share(modifier = modifier) {}
        PrivacyPolicy(modifier = modifier) {
            navHostController.navigate(route = WEB_SCREEN_ROUT)
        }
    }
}

@Composable
fun DarkThemeSwitcher(modifier: Modifier, isDarkTheme: Boolean = false) {
    var themeFlag by rememberSaveable {
        mutableStateOf(isDarkTheme)
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 48.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.theme),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Switch(checked = themeFlag, onCheckedChange = { themeFlag = !themeFlag })
    }

}

@Composable
fun Share(modifier: Modifier, shareButtonClick: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.share_app),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )
        IconButton(onClick = { shareButtonClick() }) {
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = "share_icon",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun PrivacyPolicy(modifier: Modifier, showPolicy: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.privacy_policy),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )
        IconButton(onClick = { showPolicy() }) {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = "share_icon",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BrainTrainerComposeTheme {
        Settings()
    }
}