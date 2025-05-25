package app.conjure.creatorv2.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.conjure.creatorv2.R
import app.conjure.creatorv2.ui.screens.browse.BrowseScreen
import app.conjure.creatorv2.ui.screens.card.ScreenCard
import app.conjure.creatorv2.ui.screens.edit.ScreenEdit
import app.conjure.creatorv2.ui.screens.scan.QRCodeScanner

@Composable
fun NavHostProvider(
    modifier: Modifier,
    navController: NavHostController,
    paddingValues: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = Browse.route,
        modifier = Modifier.padding(paddingValues = paddingValues).background(colorResource(R.color.standard_jet))
    ) {
        composable(route = Browse.route) {
            BrowseScreen(
                modifier = modifier,
                onClickCard = { cardId : String ->
                    navController.navigateToCard(cardId)
                },
            )
        }
        composable(route = Add.route) {
            ScreenCard(modifier = modifier, navController = navController)
        }

        composable(
            route = Edit.route,
            arguments = Edit.arguments
        )
        { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getString(Edit.idArg)
            if (id != null) {
                ScreenEdit(
                    modifier = modifier,
                    navController = navController,
                )
            }
        }

        composable("scan") {
            QRCodeScanner(
                navController, onScan = { cardId : String ->
                    navController.navigateToCard(cardId)
                },
            )
        }
    }
}

// Without this, after Browse or Scan screens navigated to a given card's Edit screen
// you couldn't access those screens again as they'd keep showing that Edit screen with the same card.
private fun NavHostController.navigateToCard(cardId: String) {
    this.navigate("edit/$cardId") {
        popUpTo(Browse.route)

        // launchSingleTop = so that multiple instances of the Edit screen don't get made, which I don't want
        launchSingleTop = true
        // restoreState = useful for retaining form data when hopping between screens
        restoreState = true
    }
}