package app.conjure.creatorv2.ui.components.general

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import app.conjure.creatorv2.R
import app.conjure.creatorv2.ui.theme.ConjureTheme
import com.chargemap.compose.numberpicker.ListItemPicker

//Reference: Adapted from the Mobile App Development module labs.
@Composable
fun AmountPicker(
    onAmountChange: (Int) -> Unit,
    altSize: String? = "",
    textColor: Color,
    inputValue: Int?
) {
    var possibleValues = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12")
    if (altSize == "twentyFour") {
        possibleValues = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24")
    }
    if (altSize == "eleven") {
        possibleValues = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11")
    }
    if (altSize == "twelveToMinusTwelve") {
        possibleValues = listOf("-12", "-11", "-10", "-9", "-8", "-7", "-6", "-5", "-4", "-3", "-2", "-1", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12")
    }
    if (altSize == "zeroToNine") {
        possibleValues = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
    }

    var pickerValue by remember { mutableStateOf(inputValue.toString()) }

    ListItemPicker(
        label = { it },
        dividersColor = textColor,
        textStyle = TextStyle(textColor,20.sp),
        value = pickerValue,

        onValueChange = {
            pickerValue = it
            onAmountChange(pickerValue.toInt())
        },
        list = possibleValues
    )
}

@Preview(showBackground = true)
@Composable
fun PickerPreview() {
    ConjureTheme {
        AmountPicker(onAmountChange = {}, textColor = colorResource(R.color.colorHP), inputValue = 3)
    }
}