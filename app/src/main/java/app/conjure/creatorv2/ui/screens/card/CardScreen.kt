package app.conjure.creatorv2.ui.screens.card

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import app.conjure.creatorv2.R
import app.conjure.creatorv2.data.DamageModifierModel
import app.conjure.creatorv2.ui.components.create_edit.AddCardButton
import app.conjure.creatorv2.ui.components.create_edit.AddModifierButton
import app.conjure.creatorv2.ui.components.general.AmountPicker
import app.conjure.creatorv2.ui.components.general.TypePicker
import app.conjure.creatorv2.ui.components.create_edit.DamageModifier
import app.conjure.creatorv2.ui.components.create_edit.DamageModifierList

@Composable
fun ScreenCard(
    modifier: Modifier = Modifier,
    cardViewModel: CardViewModel = hiltViewModel(),
    navController: NavController
) {
    var showError by remember { mutableStateOf(false) }
    val colorStandardFawn = colorResource(id = R.color.standard_fawn)
    val colorHighlightPink = colorResource(id = R.color.highlight_pink)
    val colorHighlightRose = colorResource(id = R.color.highlight_rose)
    val context = LocalContext.current
    val message = stringResource(R.string.error_addCardInput)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                top = 5.dp,
                start = 10.dp,
                end = 10.dp,
            ),
        horizontalAlignment = Alignment.Start
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
        ) {
            OutlinedTextField(
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorHighlightRose,
                    unfocusedBorderColor = colorStandardFawn,
                    focusedTextColor = colorHighlightPink,
                    unfocusedTextColor = colorStandardFawn,
                ),
                value = cardViewModel.name,
                onValueChange = {
                    cardViewModel.name = it
                    showError = false
                },
                isError = showError,
                modifier = Modifier
                    .width(200.dp)
                    .weight(1f)
                    .fillMaxSize()
                    .padding(end = 8.dp)
                    .align(Alignment.Bottom),
                label = { Text(stringResource(id = R.string.text_nameHint)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    capitalization = KeyboardCapitalization.Sentences
                ),
                trailingIcon = {
                    if (showError)
                        Icon(
                            Icons.Filled.Warning, "error",
                            tint = MaterialTheme.colorScheme.error
                        )
                    else
                        Icon(
                            Icons.Default.Edit, contentDescription = "add",
                            tint = colorHighlightRose
                        )
                },
                supportingText = { ShowSupportText(showError) }
            )
            Spacer(modifier = Modifier.padding(start = 25.dp))
            TypePicker(
                onAmountChange = { cardViewModel.type = it },
                inputValue = cardViewModel.type
            )
        }
        OutlinedTextField(
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorHighlightRose,
                unfocusedBorderColor = colorStandardFawn,
                focusedTextColor = colorHighlightPink,
                unfocusedTextColor = colorStandardFawn,
            ),
            value = cardViewModel.description,
            onValueChange = {
                cardViewModel.description = it
                showError = false
            },
            isError = showError,
            modifier = Modifier.fillMaxSize(),
            label = { Text(stringResource(id = R.string.text_descriptionHint)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Sentences
            ),
            trailingIcon = {
                Icon(
                    Icons.Default.Edit, contentDescription = "add",
                    tint = colorHighlightRose
                )
            },
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            contentAlignment = Alignment.Center
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_crystal),
                    contentDescription = "Cost",
                    modifier = Modifier.size(32.dp),
                    contentScale = ContentScale.Fit,
                )
                Spacer(modifier = Modifier.padding(start = 5.dp))
                AmountPicker(
                    onAmountChange = { cardViewModel.cost = it },
                    "eleven",
                    colorResource(R.color.colorCrystal),
                    0
                )
                Image(
                    painter = painterResource(id = R.drawable.icon_hp),
                    contentDescription = "HP",
                    modifier = Modifier.size(32.dp),
                    contentScale = ContentScale.Fit,
                )
                Spacer(modifier = Modifier.padding(start = 5.dp))
                AmountPicker(
                    onAmountChange = { cardViewModel.hp = it },
                    "twentyFour",
                    colorResource(R.color.colorHP),
                    0
                )
                Spacer(modifier = Modifier.padding(start = 5.dp))
                Image(
                    painter = painterResource(id = R.drawable.icon_ap),
                    contentDescription = "HP",
                    modifier = Modifier.size(32.dp),
                    contentScale = ContentScale.Fit,
                )
                Spacer(modifier = Modifier.padding(start = 5.dp))
                AmountPicker(
                    onAmountChange = { cardViewModel.ap = it },
                    "",
                    colorResource(R.color.highlight_sky_blue),
                    0
                )
                Spacer(modifier = Modifier.padding(start = 5.dp))
                Image(
                    painter = painterResource(id = R.drawable.icon_mp),
                    contentDescription = "HP",
                    modifier = Modifier.size(32.dp),
                    contentScale = ContentScale.Fit,
                )
                Spacer(modifier = Modifier.padding(start = 5.dp))
                AmountPicker(
                    onAmountChange = { cardViewModel.mp = it },
                    "",
                    colorResource(R.color.colorMP),0
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn( max = if (cardViewModel.modifiers.size > 1) (150 * cardViewModel.modifiers.size).dp else 210.dp )
                .wrapContentHeight()
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.Center,
        )
        {
            Column(
                modifier = modifier
                    .weight(1f)
                    .padding(end = 5.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Start
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = colorResource(R.color.colorHP)
                    ),
                ) {
                    DamageModifierList(
                        cardViewModel.modifiers,
                        // Remove mod from list: Assistance provided by Gemini AI, 15/12/2024 (required many prompts so I can't put in the prompt)
                        onSwipeDelete = { mod: DamageModifierModel ->
                            cardViewModel.deleteModifier(mod)
                        }
                    )
                }
            }
            Column(
                modifier = modifier
                    .weight(1f)
                    .padding(start = 5.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                DamageModifier(cardViewModel.tempMod) { }
                AddModifierButton(onAdd = { cardViewModel.addModifier() })
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            AddCardButton(
                onInputError = {
                    Toast.makeText(context,message,
                        Toast.LENGTH_SHORT).show()
                },
                onAdd = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
fun ShowSupportText(isError : Boolean)
{
    if (isError)
        Text(
            text = "This Field is Required",
            style = MaterialTheme.typography.labelMedium,
            color = colorResource(id = R.color.highlight_rose),
        )
    else Text(text = "")
}