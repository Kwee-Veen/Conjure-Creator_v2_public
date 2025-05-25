package app.conjure.creatorv2.helpers

import app.conjure.creatorv2.R

fun getAreaOfEffectTypeIcon(input: Int?): Int? {
    return when (input) {
        0 -> null
        1 -> R.drawable.target_aoe_all_white
        2 -> R.drawable.target_aoe_linear_cross_white
        3 -> R.drawable.target_aoe_linear_column_white
        4 -> R.drawable.target_aoe_linear_row_white
        else -> null // default and zero are single-target, i.e., no area of effect
    }
}