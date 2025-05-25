package app.conjure.creatorv2.helpers

import app.conjure.creatorv2.R

fun getDamageTypeIcon(input: Int?): Int? {
    return when (input) {
        0 -> null // Normal type doesn't get an icon
        1 -> R.drawable.damage_fire
        2 -> R.drawable.damage_ice
        3 -> R.drawable.damage_wind
        4 -> R.drawable.damage_earth
        5 -> R.drawable.damage_lightning
        6 -> R.drawable.damage_water
        7 -> R.drawable.damage_psychic
        8 -> R.drawable.damage_nature
        9 -> R.drawable.damage_light
        10 -> R.drawable.damage_dark
        else -> null
    }
}