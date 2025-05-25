package app.conjure.creatorv2.ui.screens.scan

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.zxing.integration.android.IntentIntegrator

// ChatGPT (22/12/2024) was a big help implementing this one, but couldn't solve the bug mentioned in comments below.
// To get around it I introduced the onScan logic, which repeats the BrowseScreen strategy for navigating to cards: (String) -> Unit

@Composable
fun QRCodeScanner(navController: NavController, onScan: (String) -> Unit) {
    val context = LocalContext.current
    val qrCodeResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    )
    { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intentResult = IntentIntegrator.parseActivityResult(result.resultCode, result.data)
            intentResult?.contents?.let { qrCode ->

                // Extracts the card id from the end of the QR code, then returns to the Nav Host and runs NavHostController.navigateToCard with the card id.
                // This ensures the back stack stays clear - earlier implementations would get the app 'stuck' on a single card after running Scan once,
                // such that scanning a different card wouldn't navigate to it (and instead keep returning to the first card scanned)
                onScan(qrCode.substringAfterLast("/"))
            }
        } else {
            // If you press back while the scanner is running, brings you back to Browse screen rather than showing a blank screen
            navController.popBackStack()
        }
    }

    LaunchedEffect(Unit) {
        val integrator = IntentIntegrator(context as Activity)
        // The 'beep' sound when it found a match was kind of annoying
        integrator.setBeepEnabled(false)
        qrCodeResultLauncher.launch(integrator.createScanIntent())
    }
}