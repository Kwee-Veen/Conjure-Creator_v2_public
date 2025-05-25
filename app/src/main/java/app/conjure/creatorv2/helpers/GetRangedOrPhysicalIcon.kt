package app.conjure.creatorv2.helpers

import app.conjure.creatorv2.R

fun GetRangedOrPhysicalIcon(input: Boolean): Int {
    return when (input) {
        true -> R.drawable.damage_physical_white
        false -> R.drawable.damage_ranged_white
    }
}