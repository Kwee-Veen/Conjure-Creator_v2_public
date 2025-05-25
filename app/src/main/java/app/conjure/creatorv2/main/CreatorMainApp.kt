package app.conjure.creatorv2.main

import android.app.Application
import app.conjure.creatorv2.helpers.ImageDirectoryHelper
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import java.io.File

@HiltAndroidApp
class CreatorMainApp: Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.i("Conjure initialised")
        setupImagesFolder()
    }

    // Locating or creating a directory for image storage, and saving that location into the globally accessible ImageDirectoryHelper class
    private fun setupImagesFolder() {
        ImageDirectoryHelper.imageDirectory = File(filesDir, "CardImages")

        if (!ImageDirectoryHelper.imageDirectory.exists()) {
            val created = ImageDirectoryHelper.imageDirectory.mkdir()
            if (created) {
                Timber.i("Made a new directory at ${ImageDirectoryHelper.imageDirectory.absolutePath}")
            } else {
                Timber.i("Failed to create directory at ${ImageDirectoryHelper.imageDirectory.absolutePath}")
            }
        } else {
            Timber.i("Image directory found: ${ImageDirectoryHelper.imageDirectory.absolutePath}")
        }
    }
}