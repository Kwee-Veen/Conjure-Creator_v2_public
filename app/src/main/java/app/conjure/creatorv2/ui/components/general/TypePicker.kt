package app.conjure.creatorv2.ui.components.general

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.conjure.creatorv2.R
import app.conjure.creatorv2.ui.theme.ConjureTheme

//Reference: Adapted from the Mobile App Development labs - DropDownMenu.kt in particular
@Composable
fun TypePicker(
    onAmountChange: (Int) -> Unit,
    inputValue: Int?,
    // if 'static', the icon is unresponsive, i.e. no dropdown on click
    static: Boolean = false
) {
    val icons = listOf(
        R.drawable.icon_crystal_white,
        R.drawable.type_magic_white,
        R.drawable.type_machine_white,
        R.drawable.type_cosmic_white,
        R.drawable.type_mutant_white,
        R.drawable.type_nightmare_white
    )
    var expanded by remember { mutableStateOf(false) }
    var pickerIndex by remember { mutableIntStateOf(inputValue ?: 0) }

    Box(
        Modifier.size(80.dp), contentAlignment = Alignment.TopCenter
    ) {
        IconButton(onClick = {
            if (!static) expanded = true
        },  modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(icons[pickerIndex]),
                contentDescription = "Current type icon",
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
        DropdownMenu(
            modifier = Modifier.background(colorResource(R.color.standard_jet)).align(Alignment.Center),
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            icons.forEachIndexed{ index, icon ->
                DropdownMenuItem(
                    text = {
                        Image(
                            painter = painterResource(id = icon),
                            contentDescription = "Type icon",
                            modifier = Modifier.size(85.dp),
                            colorFilter = ColorFilter.tint(Color.White)
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
fun TypePickerPreview() {
    ConjureTheme {
        TypePicker(onAmountChange = {}, 2)
    }
}