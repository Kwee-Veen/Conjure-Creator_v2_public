package app.conjure.creatorv2.helpers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import androidx.core.content.ContextCompat
import app.conjure.creatorv2.R
import app.conjure.creatorv2.helpers.ImageDirectoryHelper.imageDirectory
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.EnumMap

// Imported (and tweaked for JPC) from Creator V1, which itself employed the below reference:
// Reference: https://blair49.medium.com/how-to-add-qr-code-generation-and-scanning-in-your-android-app-7baff15bd7c6

fun generateQRCode(context: Context, cardId: String): Boolean? {
    val bitMatrix: BitMatrix = try {
        val hints = EnumMap<EncodeHintType, Any>(EncodeHintType::class.java)
        hints[EncodeHintType.CHARACTER_SET] = "UTF-8"
        MultiFormatWriter().encode(
            "https://app.conjure-cards/open/$cardId",
            BarcodeFormat.QR_CODE,
            120,
            120,
            hints
        )
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }

    val qrCodeWidth = bitMatrix.width
    val qrCodeHeight = bitMatrix.height
    val pixels = IntArray(qrCodeWidth * qrCodeHeight)

    // Made 3 different color styles for QR codes cause I couldn't decide which I liked best.
    // Change colorSetting to 1 (standard white / black), 2 (white / dark purple) or 3 (black / light purple) to use.
    val colorSetting = 2

    val qrColor =
        if (colorSetting == 1)
            Color.BLACK
         else if (colorSetting == 2)
            ContextCompat.getColor(context, R.color.colorCrystalDark)
         else
            ContextCompat.getColor(context, R.color.colorCrystalLight)

    val qrBackgroundColor =
        if (colorSetting == 1)
            Color.WHITE
         else if (colorSetting == 2)
            Color.WHITE
         else
            Color.BLACK

    for (y in 0 until qrCodeHeight) {
        val offset = y * qrCodeWidth
        for (x in 0 until qrCodeWidth) {
            pixels[offset + x] = if (bitMatrix[x, y]) {
                qrColor
            } else {
                qrBackgroundColor
            }
        }
    }

    val bitmap = Bitmap.createBitmap(qrCodeWidth, qrCodeHeight, Bitmap.Config.RGB_565)
    bitmap.setPixels(pixels, 0, qrCodeWidth, 0, 0, qrCodeWidth, qrCodeHeight)

    // Note: the below code overlays the summoning crystal icon on the middle of the QR code.
    // It works, and the resulting QR code is camera-readable, but I'm disabling it as I don't really like how it looks.
    // Will keep it in just in case I want it at a later date though (will have to re-add imports)

//    val canvas = Canvas(bitmap)
//    val iconSize = bitmap.width / 4 // Icon size relative to the QR code; any larger than this and it doesn't read
//    val left = (bitmap.width - iconSize) / 2
//    val top = (bitmap.height - iconSize) / 2
//    val iconBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.icon_crystal)
//    val resizedIcon = Bitmap.createScaledBitmap(iconBitmap, iconSize, iconSize, false)
//    canvas.drawBitmap(resizedIcon, left.toFloat(), top.toFloat(), null)

    val newQR = File(imageDirectory, "${cardId}_QR.png")
    // returns true if all went OK, else false
    return saveBitmapInternally(newQR, bitmap)
}

// Saves the QR code to the files/CardImages directory ((i.e. newQR, made in the previous function) (which definitely exists - see CreatorMainApp's setupImagesFolder function))
// Assisted by ChatGPT (22/12/2024), though largely modified to suit.
private fun saveBitmapInternally(newQR: File, bitmap: Bitmap): Boolean {
    try {
        FileOutputStream(newQR).use { fos ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            return true
        }
    } catch (e: IOException) {
        e.printStackTrace()
        return false
    }
}