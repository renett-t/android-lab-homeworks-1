package ru.itis.renett.testapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI
import androidx.core.content.FileProvider
import com.google.android.material.snackbar.Snackbar
import ru.itis.renett.testapp.databinding.ActivityMainBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

const val REQUEST_IMAGE_CAPTURE = 1

class MainActivity : AppCompatActivity() {

    lateinit var currentPhotoPath: String

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        with(binding) {
            btnCameraIntent.setOnClickListener {
                getPhotoFromCamera()
            }
        }
    }

    private fun getPhotoFromCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (takePictureIntent.resolveActivity(packageManager) != null) {
            // creating a file where to save picture
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                Snackbar.make(
                    binding.root,
                    "Error occurred while creating the File",
                    Snackbar.LENGTH_LONG
                ).show()
                null
            }

            // if the file was created
            photoFile?.also {
                val photoURI: Uri = FileProvider.getUriForFile(
                    this,
                    "com.example.android.fileprovider",
                    it
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        var message: String = ""
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val imageBitmapThumbnail = data?.extras?.get("data") as Bitmap

                    with(binding) {
                        ivAvatar.setImageBitmap(imageBitmapThumbnail)
                    }
                    message = "The picture has been updated ^^"
                }
                else -> {
                    message = "Wrong intent call"
                }
            }
        } else {
            message = "Picture wasn't set as your avatar. Try again!"
        }

        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

}
