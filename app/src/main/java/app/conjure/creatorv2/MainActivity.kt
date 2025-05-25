package app.conjure.creatorv2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.conjure.creatorv2.navigation.Browse
import app.conjure.creatorv2.navigation.NavHostProvider
import app.conjure.creatorv2.navigation.allDestinations
import app.conjure.creatorv2.ui.components.general.BottomAppBarProvider
import app.conjure.creatorv2.ui.components.general.TopAppBarProvider
import app.conjure.creatorv2.ui.theme.ConjureTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ConjureTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(R.color.standard_jet)
                ) {
                    navController = rememberNavController()
                    ConjureCreatorApp(modifier = Modifier, navController = navController)

                    // Handle incoming intent once the composable is set up
                    LaunchedEffect(Unit) {
                        handleIncomingIntent(intent)
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // Handle incoming intent when activity is already running
        handleIncomingIntent(intent)
    }

    private fun handleIncomingIntent(intent: Intent) {
        intent.data?.let { uri ->
            val cardId = uri.lastPathSegment
            if (cardId != null && ::navController.isInitialized) {
                navController.navigate("edit/$cardId")
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ConjureCreatorApp(modifier: Modifier = Modifier, navController: NavHostController) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentNavBackStackEntry?.destination
    val currentBottomScreen = allDestinations.find { it.route == currentDestination?.route } ?: Browse

    // Introducing scrolling into the pages using:
    // a) https://composables.com/foundation/verticalscroll
    // b) ChatGPT, 15/12/2024, to prevent vertical scrolling from applying x2 to any LazyColumn elements within
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBarProvider(
                currentScreen = currentBottomScreen,
                canNavigateBack = navController.previousBackStackEntry != null
            ) { navController.navigateUp() }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(paddingValues)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {
                NavHostProvider(
                    modifier = Modifier
                        .fillMaxWidth()
                        // adapted from a response from ChatGPT (27/12/2024) to the question "How do I set the minimum height of the screen to be the height of the screen itself?"
                        // Essentially sets the minimum height of the screen to be the height of the screen itself.
                        // Without this, you'd see a different color (i.e. not color.standard_jet) at the bottom of the screen.
                        // tweaked it by 145.dp as it always goes a bit beyond the display screen, probably because of the top and bottom bars,
                        // through when I try to set this at the Scaffold level, it totally undershoots. I suspect this'll look janky on other displays.
                        .heightIn(min = LocalConfiguration.current.screenHeightDp.dp - 145.dp)
                    ,
                    navController = navController,
                    paddingValues = PaddingValues(0.dp)
                )
            }
        },
        bottomBar = {
            BottomAppBarProvider(navController,
                currentScreen = currentBottomScreen,)
        }
    )
}