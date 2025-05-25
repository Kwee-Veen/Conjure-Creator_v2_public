package app.conjure.creatorv2.helpers

import androidx.compose.runtime.Composable

@Composable
fun TargetTranslator(chance: Int): String {
    return when (chance) {
        0 -> "Self"
        1 -> "Enemies"
        2 -> "Allies"
        3 -> "Everyone"
        else -> "Self"
    }
}