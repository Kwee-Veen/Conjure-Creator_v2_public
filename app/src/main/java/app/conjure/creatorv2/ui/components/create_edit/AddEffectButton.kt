package app.conjure.creatorv2.ui.components.create_edit

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.conjure.creatorv2.R

@Composable
fun AddEffectButton(
    onAddEffect: () -> Unit
) {

    Row (
        modifier = Modifier.widthIn(max = 195.dp).wrapContentWidth()
    ){
        Button(
            onClick = {
                onAddEffect()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.colorCrystalDark)
            )
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Icon", tint = Color.White)
            Spacer(Modifier.width(width = 4.dp))
            Text(
                text = stringResource(R.string.button_effects),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }
}