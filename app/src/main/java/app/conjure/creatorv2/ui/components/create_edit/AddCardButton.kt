package app.conjure.creatorv2.ui.components.create_edit

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
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
import androidx.hilt.navigation.compose.hiltViewModel
import app.conjure.creatorv2.R
import app.conjure.creatorv2.ui.screens.card.CardViewModel

@Composable
fun AddCardButton(
    modifier: Modifier = Modifier,
    cardViewModel: CardViewModel = hiltViewModel(),
    onInputError: (Boolean) -> Unit,
    onAdd: () -> Unit
) {

    Row(
        modifier = Modifier.padding(bottom = 10.dp)
    ) {
        Button(
            onClick = {
                if (cardViewModel.name.isEmpty()) {
                    onInputError(true)
                } else {
                    cardViewModel.insert()
                    onAdd()
                }
            },
            elevation = ButtonDefaults.buttonElevation(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.colorCrystal)
            )
        ) {
            Icon(Icons.Default.Add, contentDescription = "AddCard", tint = Color.White)
            Spacer(modifier.width(width = 4.dp))
            Text(
                text = stringResource(R.string.button_addCardHint),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
}