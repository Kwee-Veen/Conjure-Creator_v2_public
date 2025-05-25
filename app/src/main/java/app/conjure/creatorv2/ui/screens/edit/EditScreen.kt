package app.conjure.creatorv2.ui.screens.edit

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import app.conjure.creatorv2.data.CardModel
import app.conjure.creatorv2.data.DamageModifierModel
import app.conjure.creatorv2.data.MoveModel
import app.conjure.creatorv2.helpers.copyImageToInternalStorage
import app.conjure.creatorv2.helpers.DisplayImage
import app.conjure.creatorv2.helpers.deleteImage
import app.conjure.creatorv2.helpers.ImageDirectoryHelper.imageDirectory
import app.conjure.creatorv2.helpers.generateQRCode
import app.conjure.creatorv2.ui.components.create_edit.AddModifierButton
import app.conjure.creatorv2.ui.components.create_edit.AddMoveSection
import app.conjure.creatorv2.ui.components.create_edit.DamageModifier
import app.conjure.creatorv2.ui.components.create_edit.DamageModifierList
import app.conjure.creatorv2.ui.components.create_edit.EditCardButton
import app.conjure.creatorv2.ui.components.create_edit.MoveCardList
import app.conjure.creatorv2.ui.components.general.AmountPicker
import app.conjure.creatorv2.ui.components.general.ImagePickerButton
import app.conjure.creatorv2.ui.components.general.TypePicker
import java.io.File

@Composable
fun ScreenEdit(
    modifier: Modifier = Modifier,
    editViewModel: EditViewModel = hiltViewModel(),
    navController: NavController,
) {
    var showError by remember { mutableStateOf(false) }
    val colorStandardFawn = colorResource(id = R.color.standard_fawn)
    val colorHighlightPink = colorResource(id = R.color.highlight_pink)
    val colorHighlightRose = colorResource(id = R.color.highlight_rose)
    val context = LocalContext.current
    val message = stringResource(R.string.error_addCardInput)
    var showImagePicker by remember { mutableStateOf(false) }

    // Looks for an image with the same name as the card's id in the files/CardImages folder
    var imageFile by remember { mutableStateOf(File(imageDirectory, "${editViewModel.id}.jpg")) }
    // Looks for a QR image file with the same name as the card's id in the files/CardImages folder
    var qrFile by remember { mutableStateOf(File(imageDirectory, "${editViewModel.id}_QR.png")) }

    if (editViewModel.card.id == editViewModel.id) {
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
                horizontalArrangement = Arrangement.End
            )
            {
                OutlinedTextField(
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorHighlightRose,
                        unfocusedBorderColor = colorStandardFawn,
                        focusedTextColor = colorHighlightPink,
                        unfocusedTextColor = colorStandardFawn,
                    ),
                    value = editViewModel.name,
                    onValueChange = {
                        editViewModel.name = it
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
                    supportingText = { ShowEditSupportText(showError) },
                )
                TypePicker(
                    onAmountChange = { editViewModel.type = it },
                    inputValue = editViewModel.type
                )
                Spacer(modifier = Modifier.padding(start = 8.dp))
                if (!qrFile.exists()) {
                    generateQRCode(context, editViewModel.id)
                    qrFile = File(imageDirectory, "QR_${editViewModel.id}.png")
                } else {
                    DisplayImage(qrFile.toString(), true)
                }
            }
            OutlinedTextField(
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorHighlightRose,
                    unfocusedBorderColor = colorStandardFawn,
                    focusedTextColor = colorHighlightPink,
                    unfocusedTextColor = colorStandardFawn,
                ),
                value = editViewModel.description,
                onValueChange = {
                    editViewModel.description = it
                },
                modifier = Modifier.fillMaxSize(),
                label = { Text(stringResource(id = R.string.text_descriptionHint)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    capitalization = KeyboardCapitalization.Sentences
                ),
            )
            Box(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .clickable { showImagePicker = true },
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // If there's no image associated with the card, show a button to select one, else show the image.
                    if (!imageFile.exists() || showImagePicker) {
                        ImagePickerButton { uri ->
                            if (imageFile.exists()) {
                                deleteImage(editViewModel.id)
                            }
                            copyImageToInternalStorage(context, uri, editViewModel.id)
                            showImagePicker = false
                            imageFile = File(imageDirectory, "${editViewModel.id}.jpg")
                        }
                    } else {
                        DisplayImage(imageFile.toString())
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp),
                contentAlignment = Alignment.Center
            )
            {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
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
                        onAmountChange = { editViewModel.cost = it },
                        "eleven",
                        colorResource(R.color.colorCrystal),
                        editViewModel.cost
                    )
                    Image(
                        painter = painterResource(id = R.drawable.icon_hp),
                        contentDescription = "HP",
                        modifier = Modifier.size(32.dp),
                        contentScale = ContentScale.Fit,
                    )
                    Spacer(modifier = Modifier.padding(start = 5.dp))
                    AmountPicker(
                        onAmountChange = { editViewModel.hp = it },
                        "twentyFour",
                        colorResource(R.color.colorHP),
                        editViewModel.hp
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
                        onAmountChange = { editViewModel.ap = it },
                        "",
                        colorResource(R.color.highlight_sky_blue),
                        editViewModel.ap
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
                        onAmountChange = { editViewModel.mp = it },
                        "",
                        colorResource(R.color.colorMP),
                        editViewModel.mp
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    // I'm dynamically setting the max height of this row based on the size of the damage modifiers list, but it'll be a minimum of 200.dp
                    // I tried this a billion (~ish) different ways and only this yielded a) an expandable, but bounded (non-infinite) height, and b) a good minimum height.
                    .heightIn(max = if (editViewModel.modifiers.size > 1) (150 * editViewModel.modifiers.size).dp else 205.dp)
                    .wrapContentHeight()
                    .padding(bottom = 30.dp),
                horizontalArrangement = Arrangement.Center
            )
            {
                Column(
                    modifier = modifier
                        .weight(1f)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = colorResource(R.color.standard_grey)
                        ),
                    ) {
                        DamageModifierList(
                            editViewModel.modifiers,
                            // Remove mod from list: Assistance provided by Gemini AI, 15/12/2024 (required many prompts so I can't put in the prompt)
                            onSwipeDelete = { mod: DamageModifierModel ->
                                editViewModel.deleteModifier(mod)
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
                )
                {
                    DamageModifier(editViewModel.tempMod) { }
                    AddModifierButton(onAdd = { editViewModel.addModifier() })
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 25.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End

            ) {
                Card(
                    colors = CardDefaults.cardColors( containerColor = colorResource(R.color.standard_rose_quartz) ),
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 2.dp)
                ) {
                    MoveCardList(
                        editViewModel.moves,
                        onSwipeDelete = { move: MoveModel ->
                            editViewModel.deleteMove(move)
                        },
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding( horizontal = 2.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center

            ) {
                AddMoveSection(
                    onAddMove = { move: MoveModel ->
                        editViewModel.addMove(move)
                     },
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center

            ) {
                EditCardButton(
                    card = CardModel(
                        name = editViewModel.name,
                        description = editViewModel.description,
                        hp = editViewModel.hp,
                        ap = editViewModel.ap,
                        mp = editViewModel.mp,
                        type = editViewModel.type,
                        cost = editViewModel.cost,
                        moves = editViewModel.moves
                    ),
                    onInputError = {
                        Toast.makeText(
                            context, message,
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    onEdit = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

@Composable
fun ShowEditSupportText(isError : Boolean)
{
    if (isError)
        Text(
            text = "This Field is Required",
            style = MaterialTheme.typography.labelMedium,
            color = colorResource(id = R.color.highlight_rose),
        )
    else Text(text = "")
}