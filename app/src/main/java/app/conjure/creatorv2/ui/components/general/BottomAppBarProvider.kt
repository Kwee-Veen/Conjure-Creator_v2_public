package app.conjure.creatorv2.ui.components.general

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.conjure.creatorv2.R
import app.conjure.creatorv2.navigation.AppDestination
import app.conjure.creatorv2.navigation.Browse
import app.conjure.creatorv2.navigation.Edit
import app.conjure.creatorv2.navigation.bottomAppBarDestinations
import app.conjure.creatorv2.ui.theme.ConjureTheme

@Composable
fun BottomAppBarProvider(
    navController: NavHostController,
    currentScreen: AppDestination
) {

    var navigationSelectedItem by remember { mutableIntStateOf(0) }
    // Can only see the Edit menu item if you're in it (Edit requires a CardModel, can't navigate to it without one)
    if (currentScreen.route == Edit.route) {
        if (bottomAppBarDestinations.size == 3) {
            bottomAppBarDestinations.add(Edit)
        }
    } else {
        bottomAppBarDestinations.remove(Edit)
    }

    NavigationBar(
        containerColor = colorResource(R.color.standard_fawn),
    ) {

        bottomAppBarDestinations.forEachIndexed { index, navigationItem ->

            NavigationBarItem(
                selected = navigationItem == currentScreen,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorResource(R.color.highlight_rose),
                    selectedTextColor = Black,
                    indicatorColor = White,
                    unselectedIconColor = White,
                    unselectedTextColor = Black
                ),
                label = {
                    Text(text = navigationItem.label)
                },
                icon = {
                    Icon(
                        navigationItem.icon,
                        contentDescription = navigationItem.label
                    )
                },
                onClick = {
                    navigationSelectedItem = index
                    // The below solves a bug where selecting 'Browse' from the menu wouldn't return to the menu if in the Edit route
                    if (navigationItem.route == Browse.route) {
                        navController.navigate(Browse.route) {
                            popUpTo(Browse.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    } else {
                        navController.navigate(navigationItem.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomAppBarScreenPreview() {
    ConjureTheme {
        BottomAppBarProvider(
            rememberNavController(),
            bottomAppBarDestinations.get(1))
    }
}