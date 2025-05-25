package app.conjure.creatorv2.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument

interface AppDestination {
        val icon: ImageVector
        val label: String
        val route: String
    }

    object Browse : AppDestination {
        override val icon = Icons.Filled.Home
        override val label = "Browse"
        override val route = "browse"
    }

    object Edit : AppDestination {
        override val icon = Icons.Filled.AddCircle
        override val label = "Edit"
        const val idArg = "id"
        override val route = "edit/{$idArg}"
        val arguments = listOf(
            navArgument(idArg) { type = NavType.StringType }
        )
    }

    object Add : AppDestination {
        override val icon = Icons.Filled.AddCircle
        override val label = "Add"
        override val route = "add"
    }

    object Scan : AppDestination {
        override val icon = Icons.Filled.Search
        override val label = "Scan"
        override val route = "scan"
    }

    var bottomAppBarDestinations = mutableListOf(Browse, Scan, Add)
    val allDestinations = listOf(Browse, Scan, Add, Edit)