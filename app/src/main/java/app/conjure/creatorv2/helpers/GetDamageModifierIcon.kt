package app.conjure.creatorv2.helpers

import androidx.compose.runtime.Composable
import app.conjure.creatorv2.R

@Composable
fun GetDamageModifierIcon(input: Int?): Int? {
    return when (input) {
        0 -> R.drawable.defence_fire
        1 -> R.drawable.defence_ice
        2 -> R.drawable.defence_wind
        3 -> R.drawable.defence_earth
        4 -> R.drawable.defence_water
        5 -> R.drawable.defence_lightning
        6 -> R.drawable.defence_psychic
        7 -> R.drawable.defence_nature
        8 -> R.drawable.defence_light
        9 -> R.drawable.defence_dark
        10 -> R.drawable.defence
        11 -> R.drawable.defence_physical
        12 -> R.drawable.defence_ranged
        else -> null
    }
}