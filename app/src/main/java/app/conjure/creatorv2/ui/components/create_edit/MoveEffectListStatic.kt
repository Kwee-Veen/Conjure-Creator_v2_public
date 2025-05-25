package app.conjure.creatorv2.ui.components.create_edit

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.conjure.creatorv2.data.EffectModel

// This version of MoveEffectList has no swipe-able elements and cannot delete effects.
@Composable
fun MoveEffectListStatic(
    effects: List<EffectModel>,
) {
    var listMaxHeight = (55 * effects.size).dp
    for (effect in effects) {
        if (!effect.effectDescription.isNullOrEmpty()) {
            listMaxHeight += 50.dp
        }
    }

    LazyColumn(
        modifier = Modifier
            .heightIn(max = listMaxHeight)
            .wrapContentHeight(),
    ) {
        items(
            items = effects,
            key = { effect -> effect.id }
        )
        {
            effect -> MoveEffect(effect = effect)
        }
    }
}