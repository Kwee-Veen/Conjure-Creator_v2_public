package app.conjure.creatorv2.ui.components.general

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import app.conjure.creatorv2.R

// Both ChatGPT (19/12/2024) and Google Gemini (21/12/2024) helped with this implementation, as well as the supporting functions in the 'helpers' package.
// Neither LLM alone was able to get it working though, and How-To articles online weren't much help either, due to my specific intentions with this code:

// 1) Choose an image from your phone's gallery
// 2) Make a copy of that image in the app's internal storage - in files/CardImages (create directory if doesn't exist)
// 3) Save the path to the copied image (not the original gallery image) as a relative path, NOT an absolute path, and...
// 4) That new image's name should be the card's id, so that a separate image URI does not need to be saved (either it's present or it isn't)

// -> the rationale is I want this app to be able to prepare cards with images in an easily transferable format,
//    and absolute paths and gallery images won't be present on other media. However the R.drawable folder (where I keep my icons) wasn't an option as
//    it's immutable at runtime, and part of this app's functionality is to save to this folder. Hence I needed a new folder I could copy images to, and relative paths to access them.
//    These cards (and associated images) will resurface in my FYP, so I need to get it right now.
//    Also if I used a cloud provider, since images would need to be loaded so often, the costs could become prohibitive.
//    Internal storage works best here (and is quickest and available offline as a plus).

@Composable
fun ImagePickerButton(
    onImagePicked: (Uri) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { result: Uri? ->
        result?.let {
            onImagePicked(it)
            timber.log.Timber.i("Got URI: $it")
        }
    }

    Button(
        onClick = {
            launcher.launch("image/*")
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.colorCrystalDark)
        )
    )
    {
        Text(
            stringResource(R.string.select_card_image),
            color = Color.White
        )
    }
}