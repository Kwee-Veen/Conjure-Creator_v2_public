package app.conjure.creatorv2.ui.components.general

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
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
fun DamageTypePicker(
    onAmountChange: (Int) -> Unit,
    inputValue: Int,
) {
    val icons = listOf(
        R.drawable.icon_crystal_white,
        R.drawable.damage_fire,
        R.drawable.damage_ice,
        R.drawable.damage_wind,
        R.drawable.damage_earth,
        R.drawable.damage_lightning,
        R.drawable.damage_water,
        R.drawable.damage_psychic,
        R.drawable.damage_nature,
        R.drawable.damage_light,
        R.drawable.damage_dark,
    )
    var expanded by remember { mutableStateOf(false) }
    var pickerIndex by remember { mutableIntStateOf(inputValue) }

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
                painter = painterResource(icons[pickerIndex]),
                contentDescription = "Current damage type icon",
            )
        }
        DropdownMenu(
            modifier = Modifier.background(colorResource(R.color.standard_jet)).align(Alignment.CenterHorizontally).wrapContentWidth().wrapContentHeight(),
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            icons.forEachIndexed{ index, icon ->
                DropdownMenuItem(
                    text = {
                        Image(
                            painter = painterResource(id = icon),
                            contentDescription = "Current damage type icon",
                            modifier = Modifier.size(70.dp),
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
fun DamageTypePickerPreview() {
    ConjureTheme {
        DamageTypePicker(onAmountChange = {}, 2)
    }
}