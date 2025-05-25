package app.conjure.creatorv2.helpers

import android.widget.ImageView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.ImageLoader
import coil.request.ImageRequest
import coil.size.Scale
import java.io.File

// In my first attempt I got this up and running myself with Picasso, but I was having trouble resizing it, and the native resolution images were 
// significantly slowing down the MiniCardList screen. As such I decided to from Picasso to Coil, as the latter seems to work better with Compose.
// Funnily enough I tried working with Gemini for nearly an hour to implement this on both it's 1.5 and 2.0 modes (21/12/2024) and it completely botched it.
// ChatGPT was much better by contrast, and helped me get up and running (with a few tweaks, like where commented below).

// Renders at different resolutions depending on whether you're in the card screen (normal) or MiniCardList screen (far smaller).
@Composable
fun DisplayImage(imagePath: String, miniMode: Boolean = false) {
    var height = if (miniMode) 100 else 400

    val modifier = if (miniMode) {
        Modifier.height(height.dp)
    } else {
        Modifier.fillMaxWidth().height(height.dp)
    }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            ImageView(context).apply { scaleType = ImageView.ScaleType.FIT_CENTER }
        },
        update = { imageView ->
            val file = File(imagePath)
            val imageLoader = ImageLoader.Builder(imageView.context).build()
            val request = ImageRequest.Builder(imageView.context)
                .data(file)
                .target { drawable ->
                    val targetWidth = if (miniMode) 100 else imageView.width
                    // If not a mini card, set the height here higher purely to render at higher resolution.
                    // Setting it at 400 above helped for fitting it on the card, but left it looking a bit low res.
                    height = if (miniMode) 100 else 1000
                    imageView.setImageDrawable(drawable)
                    imageLoader.enqueue(
                        ImageRequest.Builder(imageView.context)
                            .data(file)
                            .size(targetWidth, height)
                            .scale(Scale.FIT)
                            .target(imageView)
                            .build()
                    )
                }
                .build()
            imageLoader.enqueue(request)
        }
    )
}