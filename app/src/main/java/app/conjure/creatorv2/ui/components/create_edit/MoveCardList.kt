package app.conjure.creatorv2.ui.components.create_edit

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.conjure.creatorv2.R
import app.conjure.creatorv2.data.MoveModel

@Composable
fun MoveCardList(
    moves: List<MoveModel>,
    onSwipeDelete: (MoveModel) -> Unit
) {
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }
    var currentMove by remember { mutableStateOf<MoveModel?>(null) }

    var listMaxHeight = 0.dp

    for (move in moves) {
        listMaxHeight += 250.dp
        for (effect in move.effects) {
            if (!effect.effectDescription.isNullOrEmpty()) {
                listMaxHeight += 60.dp
            }
        }
    }

    LazyColumn(
        // Calculating the max height of the container in response to how many items are inside it.
        // Without something like this for the LazyColumn elements, height is unconstrained, causing heaps of issues.
        // I will copy this height strategy to DamageModifierList and MiniCardList elements;
        // hopefully then I will be able to set overall height dynamically; it's currently hardcoded in MainActivity to prevent crashes.

        modifier = Modifier
            .heightIn(max = listMaxHeight)
            .wrapContentHeight(),
    ) {
        items(
            items = moves,
            key = { move -> move.id }
        ) { move ->
            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = {
                    when(it) {
                        StartToEnd, EndToStart -> {
                            currentMove = move
                            showDeleteConfirmDialog = true
                            return@rememberSwipeToDismissBoxState false
                        }
                        Settled -> return@rememberSwipeToDismissBoxState false
                    }
                },
                positionalThreshold = { it * .25f }
            )

            if (showDeleteConfirmDialog && currentMove == move) {
                ShowDeleteMoveAlert(
                    onDismiss = {
                        showDeleteConfirmDialog = false
                    },
                    onDelete = {
                        onSwipeDelete(move)
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
                        Modifier.fillMaxSize()
                            .padding(vertical = 2.dp, horizontal = 2.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(color))
                },
                content = {
                    MoveCard(
                        move = move
                    )
                }
            )
        }
    }
}

@Composable
fun ShowDeleteMoveAlert(
    onDismiss: () -> Unit,
    onDelete: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss ,
        title = { Text(stringResource(id = R.string.confirm_delete)) },
        text = { Text(stringResource(id = R.string.confirm_delete_move_message)) },
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