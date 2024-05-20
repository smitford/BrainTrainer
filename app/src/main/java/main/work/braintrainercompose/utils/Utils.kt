package main.work.braintrainercompose.utils

import android.util.Log
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import main.work.braintrainercompose.game.domain.models.MathExpression
import main.work.braintrainercompose.utils.domain.models.BottomNavItems
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun RowScope.AddItem(
    screen: BottomNavItems,
    navController: NavController,
    onSameDestinationClick: (Boolean) -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBarItem(
        label = { },
        selected = currentDestination?.hierarchy?.any { it.route == screen.title } == true,
        onClick = {

            navController.navigate(screen.title) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
            if (navController.previousBackStackEntry == null
            ) {
                onSameDestinationClick(true)
                Log.d("Click", "action")
            } else {
                onSameDestinationClick(false)
            }
        },
        icon = {
            Icon(
                painter = painterResource(id = screen.icon),
                contentDescription = screen.title
            )
        },
        colors = NavigationBarItemDefaults.colors(),
        alwaysShowLabel = false
    )
}

@Composable
fun BottomNavigation(
    navController: NavController,
    items: List<BottomNavItems>,
    onSameDestinationClick: (Boolean) -> Unit
) {
    NavigationBar {
        items.forEach { item ->
            AddItem(
                screen = item,
                navController = navController,
                onSameDestinationClick = onSameDestinationClick
            )
        }
    }
}

fun getExpression(expression: MathExpression, expressionSymbol: String) =
    "${expression.firstNumber} $expressionSymbol ${expression.secondNumber}="


fun getDoubleDotsString(string1: String, string2: String) =
    "$string1: $string2"

fun formatMaker(time: Long): String = SimpleDateFormat(
    "mm:ss",
    Locale.getDefault()
).format(time)

fun <T> debounce(
    delayMillis: Long,
    coroutineScope: CoroutineScope,
    useLastParam: Boolean,
    action: (T) -> Unit
): (T) -> Unit {
    var debounceJob: Job? = null
    return { param: T ->
        if (useLastParam) {
            debounceJob?.cancel()
        }
        if (debounceJob?.isCompleted != false || useLastParam) {
            debounceJob = coroutineScope.launch {
                delay(delayMillis)
                action(param)
            }
        }
    }
}

@Composable
fun EventListener(onEvent: (event: Lifecycle.Event) -> Unit) {
    val eventHandler = rememberUpdatedState(newValue = onEvent)
    val lifecycleOwner = rememberUpdatedState(newValue = LocalLifecycleOwner.current)

    DisposableEffect(lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { source, event ->
            eventHandler.value(event)
        }

        lifecycle.addObserver(observer)

        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}

@Composable
fun StateListener (onState: (state: Lifecycle.State)->Unit){
    val stateHandler = rememberUpdatedState(newValue = onState)
    val lifecycleOwner = rememberUpdatedState(newValue = LocalLifecycleOwner.current)

    DisposableEffect(lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { source, event->
            stateHandler.value(source.lifecycle.currentState)
        }

        lifecycle.addObserver(observer)

        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

}

class DataUtils {
    companion object {
        const val GAME_SETTINGS = "Game settings"
        const val APP_SETTINGS = "App settings"
        const val GAME_SESSIONS = "Game sessions"
        const val EASY_TIME_SEC = 8
        const val NORMAL_TIME_SEC = 6
        const val HARD_TIME_SEC = 5
        const val IMPOSSIBLE_TIME_SEC = 4
        const val EASY_COUNT_EXPRESSION = 20
        const val NORMAL_COUNT_EXPRESSION = 25
        const val HARD_COUNT_EXPRESSION = 30
        const val IMPOSSIBLE_COUNT_EXPRESSION = 35
        const val TIMER_DELAY_MSC = 500L
        const val SCORE_COEFFICIENT_EASY = 1000
        const val SCORE_COEFFICIENT_NORMAL = 1100
        const val SCORE_COEFFICIENT_HARD = 1200
        const val SCORE_COEFFICIENT_IMPOSSIBLE = 1300
        const val DIFFICULTY_COEF_ADDITION = 1
        const val DIFFICULTY_COEF_SUBTRACTION = 2
        const val DIFFICULTY_COEF_MULTIPLICATION = 3
        const val DIFFICULTY_COEF_DIVISION = 4
        const val REDUCTION_FACTOR = 0.85
        const val SHOW_RESULTS_DELAY_MSC = 1000L
        const val THEME_SETTINGS = "theme_settings"
        const val WEB_SCREEN_ROUT = "Web View"
    }

}

