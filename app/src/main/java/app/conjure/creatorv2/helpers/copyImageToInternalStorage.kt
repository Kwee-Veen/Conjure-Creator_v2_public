package app.conjure.creatorv2.helpers

import android.content.Context
import android.net.Uri
import app.conjure.creatorv2.helpers.ImageDirectoryHelper.imageDirectory
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

fun copyImageToInternalStorage(context: Context, sourceURI: Uri, id: String): String {
    val inputStream: InputStream? = context.contentResolver.openInputStream(sourceURI)
    val newFileName = "${id}.jpg"
    val file = File(imageDirectory, newFileName)

    inputStream?.use { input ->
        FileOutputStream(file).use { output ->
            input.copyTo(output)
        }
    }
    return newFileName
}