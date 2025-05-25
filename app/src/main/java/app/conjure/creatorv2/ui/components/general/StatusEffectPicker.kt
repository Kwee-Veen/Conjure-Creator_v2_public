package app.conjure.creatorv2.ui.components.general

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.conjure.creatorv2.R
import app.conjure.creatorv2.helpers.GetStatusEffectIcon
import app.conjure.creatorv2.ui.theme.ConjureTheme

@Composable
fun StatusEffectPicker(
    onAmountChange: (Int) -> Unit,
    inputValue: Int,
    textValue: Color = colorResource(R.color.white),
    ) {
    val targets = listOf(
        "None",
        "Poison",
        "Bleed",
        "Concuss",
        "Leech",
        "Slow",
        "Immobilise",
        "Solidify",
        "Blind",
        "Silence",
        "Sleep",
        "Confuse",
        "Paralyse",
        "Heavy",
        "Swimming",
        "Flying",
    )
    var expanded by remember { mutableStateOf(false) }
    var pickerIndex by remember { mutableIntStateOf(inputValue + 1) }

    Column (
        Modifier
            .size(100.dp)
            .wrapContentWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = { expanded = true },  modifier = Modifier.padding(8.dp).fillMaxSize()
        ) {
            Image(
                painter = painterResource(GetStatusEffectIcon(pickerIndex -1)),
                contentDescription = "Current status effect icon",
            )
        }
        DropdownMenu(
            modifier = Modifier
                .background(colorResource(R.color.standard_jet))
                .align(Alignment.CenterHorizontally),
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            targets.forEachIndexed{ index, text ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = text,
                            color = textValue,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium
                            ),
                        )
                    },
                    onClick = {
                        pickerIndex = index
                        onAmountChange(index - 1)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StatusEffectPickerPreview() {
    ConjureTheme {
        StatusEffectPicker(onAmountChange = {}, -1)
    }
}