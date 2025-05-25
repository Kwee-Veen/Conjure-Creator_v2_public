package app.conjure.creatorv2.ui.components.general

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.conjure.creatorv2.R
import app.conjure.creatorv2.ui.theme.ConjureTheme

@Composable
fun PhysicalOrRangedPicker(
    onAmountChange: (Boolean) -> Unit,
    inputValue: Boolean,
) {
    val icons = listOf(
        R.drawable.damage_physical_white,
        R.drawable.damage_ranged_white,
    )
    var expanded by remember { mutableStateOf(false) }
    var physicalValue by remember { mutableStateOf(inputValue) }
    var iconIndex by remember { mutableIntStateOf( if (physicalValue) 0 else 1 ) }
    
    Column (
        Modifier
            .size(34.dp)
            .border(1.dp, colorResource(R.color.colorHP), CutCornerShape(10.dp)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = {
            expanded = true
        },  modifier = Modifier.size(32.dp)) {
            Image(
                painter = painterResource(icons[iconIndex]),
                contentDescription = "Physical or ranged icon",
            )
        }
        DropdownMenu(
            modifier = Modifier.background(colorResource(R.color.standard_jet)).align(Alignment.CenterHorizontally),
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            icons.forEachIndexed{ index, icon ->
                DropdownMenuItem(
                    text = {
                        Image(
                            painter = painterResource(id = icon),
                            contentDescription = "Physical or ranged icon",
                            modifier = Modifier.size(75.dp),
                        )
                    },
                    onClick = {
                        iconIndex = index
                        onAmountChange( if (iconIndex == 0) true else false)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PhysicalOrRangedPickerPreview() {
    ConjureTheme {
        PhysicalOrRangedPicker(onAmountChange = {}, false)
    }
}