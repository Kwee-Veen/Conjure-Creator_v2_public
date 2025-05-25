package app.conjure.creatorv2.ui.components.general

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.conjure.creatorv2.R
import app.conjure.creatorv2.ui.theme.ConjureTheme

@Composable
fun TurnsPicker(
    onAmountChange: (Int) -> Unit,
    inputValue: Int,
) {
    val turns = listOf( 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 )
    var expanded by remember { mutableStateOf(false) }
    var pickerIndex by remember { mutableIntStateOf(inputValue) }

    fun getString(inputIndex: Int): String {
        val value = when (inputIndex) {
            0 -> "∞ turns"
            1 -> "1 turn"
            else -> "$inputIndex turns"
        }
        return value
    }

    Column (
        Modifier
            .height(40.dp)
            .widthIn(max = 70.dp)
            .wrapContentWidth()
            .border(1.dp, colorResource(R.color.colorCrystalDark), CutCornerShape(10.dp)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box( modifier = Modifier.padding(horizontal = 5.dp).clickable { expanded = true } )
        {
            Text(
                text = getString(pickerIndex),
                textAlign = TextAlign.Center,
                color = colorResource(R.color.white),
                style = MaterialTheme.typography.bodyMedium.copy( fontWeight = FontWeight.Medium ),
            )
        }
        DropdownMenu(
            modifier = Modifier
                .background(colorResource(R.color.standard_jet))
                .align(Alignment.CenterHorizontally)
                .wrapContentWidth(),
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            turns.forEachIndexed{ index, turn ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = getString(index),
                            color = colorResource(R.color.white),
                            style = MaterialTheme.typography.bodyMedium.copy( fontWeight = FontWeight.Medium ),
                        )
                    },
                    onClick = {
                        pickerIndex = index
                        onAmountChange(index)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TurnsPickerPreview() {
    ConjureTheme {
        TurnsPicker(onAmountChange = {}, 0)
    }
}