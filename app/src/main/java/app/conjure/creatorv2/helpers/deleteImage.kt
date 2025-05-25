package app.conjure.creatorv2.helpers

import app.conjure.creatorv2.helpers.ImageDirectoryHelper.imageDirectory
import java.io.File

fun deleteImage(id: String) {
    val file = File("$imageDirectory/$id.jpg")
    if (file.exists()) {
        file.delete()
    }
}