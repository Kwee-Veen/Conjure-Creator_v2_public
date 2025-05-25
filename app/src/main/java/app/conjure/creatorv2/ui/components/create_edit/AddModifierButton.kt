package app.conjure.creatorv2.ui.components.create_edit

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.conjure.creatorv2.R

@Composable
fun AddModifierButton(
    modifier: Modifier = Modifier,
    onAdd: () -> Unit
) {

    Row {
        Button(
            onClick = {
                onAdd()
            },
            elevation = ButtonDefaults.buttonElevation(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.standard_grey)
            )
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Mod", tint = Color.White)
            Spacer(modifier.width(width = 2.dp))
            Text(
                text = stringResource(R.string.button_addModifierHint),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = Color.White
            )
        }
    }
}