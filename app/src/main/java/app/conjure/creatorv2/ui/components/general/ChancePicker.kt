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
import app.conjure.creatorv2.helpers.ChancePercentageTranslator
import app.conjure.creatorv2.ui.theme.ConjureTheme

@Composable
fun ChancePicker(
    onAmountChange: (Int) -> Unit,
    inputValue: Int,
) {
    val chances = listOf(
        0, 5, 10, 15, 17, 20, 25, 30, 33, 35, 40, 45, 50, 55, 60, 65, 67, 70, 75, 80, 83, 85, 90, 95, 100,
    )
    var expanded by remember { mutableStateOf(false) }
    var pickerIndex by remember { mutableIntStateOf(inputValue) }

    Column (
        Modifier
            .height(40.dp)
            .widthIn(max = 80.dp)
            .wrapContentWidth()
            .border(1.dp, colorResource(R.color.colorCrystalDark), CutCornerShape(10.dp)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box( modifier = Modifier.padding(horizontal = 6.dp).clickable { expanded = true } )
        {
            Text(
                text = ChancePercentageTranslator(chances[chances.indexOf(pickerIndex)]) + " chance",
                textAlign = TextAlign.Center,
                color = colorResource(R.color.white),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium
                ),
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
            chances.forEachIndexed{ index, chance ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = ChancePercentageTranslator(chance) + " chance",
                            color = colorResource(R.color.white),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium
                            ),
                        )
                    },
                    onClick = {
                        pickerIndex = chance
                        onAmountChange(chance)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChancePickerPreview() {
    ConjureTheme {
        ChancePicker(onAmountChange = {}, 18)
    }
}