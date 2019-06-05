package com.example.studentcity.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import com.example.studentcity.ML
import com.example.studentcity.R
import kotlinx.android.synthetic.main.activity_camera.*
import java.io.File

class CameraActivity : AppCompatActivity() {

    //initialization camera button
    lateinit var camera_button: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

//        camera_button = findViewById(R.id.camera_button)
//        camera_button.setOnClickListener { dispatchTakePictureIntent() }
    }



    val REQUEST_IMAGE_CAPTURE = 1

    //выбор камеры
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    //получаем из Интента bitmap изображения и отправляем его на обработку
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            ML(imageBitmap)
        }
    }
}
