package app.conjure.creatorv2.ui.components.create_edit

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import app.conjure.creatorv2.R
import app.conjure.creatorv2.data.DamageModifierModel
import app.conjure.creatorv2.data.exampleCards
import app.conjure.creatorv2.ui.theme.ConjureTheme

@Composable
fun DamageModifierList(
    damageModifiers: List<DamageModifierModel>,
    onSwipeDelete: (DamageModifierModel) -> Unit,
) {
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }
    var currentMod by remember { mutableStateOf<DamageModifierModel?>(null) }

    LazyColumn(
        modifier = Modifier
            .wrapContentHeight()
    ) {
        items(
            items = damageModifiers,
            key = { mod -> mod.id }
        ) { mod ->
            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = {
                    when(it) {
                        StartToEnd, EndToStart -> {
                            currentMod = mod
                            showDeleteConfirmDialog = true
                            return@rememberSwipeToDismissBoxState false
                        }
                        Settled -> return@rememberSwipeToDismissBoxState false
                    }
                },
                positionalThreshold = { it * .30f }
            )

            if (showDeleteConfirmDialog && currentMod == mod) {
                ShowDeleteAlert(
                    onDismiss = {
                        showDeleteConfirmDialog = false
                    },
                    onDelete = {
                        onSwipeDelete(mod)
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
                            StartToEnd -> colorResource(R.color.white)
                            EndToStart -> colorResource(R.color.white)
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
                    DamageModifier (
                        damageModifier = mod,
                        onModChange = {}
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
        text = { Text(stringResource(id = R.string.confirm_delete_modifier_message)) },
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

@Preview(showBackground = true,
    wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE
)

@Composable
fun DamageModifierListPreview() {
    ConjureTheme {
        DamageModifierList(exampleCards[1].damageModifiers.toMutableStateList(),onSwipeDelete = {})
    }
}