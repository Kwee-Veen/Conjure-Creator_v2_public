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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.conjure.creatorv2.R
import app.conjure.creatorv2.ui.theme.ConjureTheme

@Composable
fun TargetPicker(
    onAmountChange: (Int) -> Unit,
    inputValue: Int,
    textValue: Color = colorResource(R.color.white),
    borderValue: Color = colorResource(R.color.colorCrystalDark),

) {
    val targets = listOf(
        "Self",
        "Enemies",
        "Allies",
        "Everyone",
    )
    var expanded by remember { mutableStateOf(false) }
    var pickerIndex by remember { mutableIntStateOf(inputValue) }

    Column (
        Modifier
            .height(57.dp)
            .widthIn(max = 80.dp)
            .wrapContentWidth()
            .border(1.dp, borderValue, CutCornerShape(10.dp)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box( modifier = Modifier.padding(horizontal = 6.dp).clickable { expanded = true } )
        {
            Text(
                text = targets[pickerIndex],
                color = textValue,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium
                ),
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
fun TargetPickerPreview() {
    ConjureTheme {
        TargetPicker(onAmountChange = {}, 1)
    }
}