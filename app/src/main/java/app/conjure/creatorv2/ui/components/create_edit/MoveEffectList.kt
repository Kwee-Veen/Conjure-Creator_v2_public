package app.conjure.creatorv2.ui.components.create_edit

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue.EndToStart
import androidx.compose.material3.SwipeToDismissBoxValue.Settled
import androidx.compose.material3.SwipeToDismissBoxValue.StartToEnd
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.conjure.creatorv2.R
import app.conjure.creatorv2.data.EffectModel

// This version of MoveEffectList has swipe-able elements, which can delete effects
@Composable
fun MoveEffectList(
    effects: List<EffectModel>,
    onSwipeDeleteEffect: (EffectModel) -> Unit
) {
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }
    var currentEffect by remember { mutableStateOf<EffectModel?>(null) }

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
        ) { effect ->
            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = {
                    when(it) {
                        StartToEnd, EndToStart -> {
                            currentEffect = effect
                            showDeleteConfirmDialog = true
                            return@rememberSwipeToDismissBoxState false
                        }
                        Settled -> return@rememberSwipeToDismissBoxState false
                    }
                },
                positionalThreshold = { it * .25f }
            )

            if (showDeleteConfirmDialog && currentEffect == effect) {
                ShowDeleteEffectAlert(
                    onDismiss = {
                        showDeleteConfirmDialog = false
                    },
                    onDelete = {
                        onSwipeDeleteEffect(effect)
                        showDeleteConfirmDialog = false
                    }
                )
            }

            SwipeToDismissBox(
                state = dismissState,

                backgroundContent = {
                    val color by
                    animateColorAsState(
                        when (dismissState.targetValue) {
                            Settled -> Color.Transparent
                            StartToEnd -> Color.White
                            EndToStart -> Color.White
                        },
                        label = ""
                    )
                    Box(
                        Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .fillParentMaxSize()
                            .background(color))
                },
                content = {
                    MoveEffect(
                        effect = effect,
                        modifier = Modifier.border(2.dp, colorResource(R.color.standard_rose_quartz), RoundedCornerShape(10.dp))
                    )
                }
            )
        }
    }
}

@Composable
fun ShowDeleteEffectAlert(
    onDismiss: () -> Unit,
    onDelete: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss ,
        title = { Text(stringResource(id = R.string.confirm_delete)) },
        text = { Text(stringResource(id = R.string.confirm_delete_effect_message)) },
        confirmButton = {
            Button(
                onClick = { onDelete() }
            ) { Text("Yes") }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text("No") }
        }
    )
}