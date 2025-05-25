package app.conjure.creatorv2.helpers

import androidx.compose.runtime.Composable

@Composable
fun ChancePercentageTranslator(chance: Int?): String {
    return when (chance) {
        0 -> "0/6"
        5 -> "1/20"
        10 -> "2/20"
        15 -> "3/20"
        17 -> "1/6"
        20 -> "4/20"
        25 -> "5/20"
        30 -> "6/20"
        33 -> "2/6"
        35 -> "7/20"
        40 -> "8/20"
        45 -> "9/20"
        50 -> "3/6"
        55 -> "11/20"
        60 -> "12/20"
        65 -> "13/20"
        67 -> "4/6"
        70 -> "14/20"
        75 -> "15/20"
        80 -> "16/20"
        83 -> "5/6"
        85 -> "17/20"
        90 -> "18/20"
        95 -> "19/20"
        100 -> "6/6"
        else -> "0/6"
    }
}