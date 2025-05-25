package app.conjure.creatorv2.helpers

import androidx.compose.runtime.Composable
import app.conjure.creatorv2.R

@Composable
fun GetStatusEffectIcon(input: Int?): Int {
    return when (input) {
        0 -> R.drawable.status_confused_white // should be poison, but icon hasn't been designed yet
        1 -> R.drawable.status_confused_white // should be bleed, but icon hasn't been designed yet
        2 -> R.drawable.status_confused_white // should be concuss, but icon hasn't been designed yet
        3 -> R.drawable.status_confused_white // should be leech, but icon hasn't been designed yet
        4 -> R.drawable.status_confused_white // should be slow, but icon hasn't been designed yet
        5 -> R.drawable.status_immobilise_white
        6 -> R.drawable.status_confused_white // should be solidify, but icon hasn't been designed yet
        7 -> R.drawable.status_confused_white // should be blind, but icon hasn't been designed yet
        8 -> R.drawable.status_confused_white // should be silence, but icon hasn't been designed yet
        9 -> R.drawable.status_confused_white // should be sleep, but icon hasn't been designed yet
        10 -> R.drawable.status_confused_white
        11 -> R.drawable.status_confused_white // should be paralyse, but icon hasn't been designed yet
        12 -> R.drawable.status_heavy_white
        13 -> R.drawable.status_confused_white // should be swimming, but icon hasn't been designed yet
        14 -> R.drawable.status_flying_white
        else -> R.drawable.null_icon_grey
    }
}