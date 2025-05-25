package app.conjure.creatorv2.ui.components.browse

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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.conjure.creatorv2.R
import app.conjure.creatorv2.data.CardModel

// References for swipe to delete:
// https://www.geeksforgeeks.org/android-jetpack-compose-swipe-to-dismiss-with-material-3/
// ChatGPT (08/12/2024)
// https://composables.com/material3/swipetodismissbox

@Composable
fun MiniCardList(
    cards: List<CardModel>,
    onClickCard: (String) -> Unit,
    onSwipeDelete: (CardModel) -> Unit,
) {
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }
    var currentCard by remember { mutableStateOf<CardModel?>(null) }

    LazyColumn(
        modifier = Modifier
            .heightIn(max = (144 * cards.size).dp)
            .wrapContentHeight(),
    ) {
        items(
            items = cards,
            key = { card -> card.id }
        ) { card ->
            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = {
                    when(it) {
                        StartToEnd, EndToStart -> {
                            currentCard = card
                            showDeleteConfirmDialog = true
                            return@rememberSwipeToDismissBoxState false
                        }
                        Settled -> return@rememberSwipeToDismissBoxState false
                    }
                },
                positionalThreshold = { it * .60f }
            )

            if (showDeleteConfirmDialog && currentCard == card) {
                ShowDeleteAlert(
                    onDismiss = {
                        showDeleteConfirmDialog = false
                    },
                    onDelete = {
                        onSwipeDelete(card)
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
                            StartToEnd -> colorResource(R.color.colorHP)
                            EndToStart -> colorResource(R.color.colorHP)
                        }, label = ""
                    )
                    Box(Modifier.fillMaxSize().padding(vertical = 4.dp, horizontal = 2.dp).clip(RoundedCornerShape(10.dp)).background(color))
                },
                content = {
                    MiniCard(
                        card = card,
                        onClickCard = { onClickCard(card.id) }
                    )
                }
            )
        }
    }
}

@Composable
fun ShowDeleteAlert(
    onDismiss: () -> Unit,
    onDelete: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss ,
        title = { Text(stringResource(id = R.string.confirm_delete)) },
        text = { Text(stringResource(id = R.string.confirm_delete_message)) },
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