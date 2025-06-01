package com.example.proyecto.Activities

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.proyecto.R
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Camera_Activity : AppCompatActivity() {

    //Declaracion de variables para la funcionalidad de la camara
    private lateinit var imageView: ImageView
    private lateinit var bttn_takePhoto: Button
    private lateinit var bttn_viewGallery: Button

    //Varibales para el funcionamiento de la camara

    private var currentPhotoPath: String? = null
    private lateinit var photoURI: Uri

    //Objeto para capturar si se dio el permiso por parte del ususario

    companion object{

        private const val REQUEST_IMAGEN_CAMPUTE = 1
        private const val REQUEST_CAMERA_PERMISSION= 100
    }

    //Inicio del ciclo dee vida del activity Camera

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        //Inicializacion de las vistas
        imageView = findViewById(R.id.imageView_camara)
        bttn_takePhoto = findViewById(R.id.bttn_takePhoto_camera)
        bttn_viewGallery = findViewById(R.id.bttn_viewGallery_camera)

        //Configuracion de los botones

        bttn_takePhoto.setOnClickListener{

            if (checkCameraPermission()){
                dispatchTakePictureIntent()
            }else{
                requestCameraPermission()
            }
        }

        bttn_viewGallery.setOnClickListener{

            openGallery()
        }
    }

    private fun openGallery() {
        val intent = Intent(this, photoGallery_Activity :: class.java)
        startActivity(intent)
    }

    //Funcion para verificar el permiso de la camara
    private fun checkCameraPermission(): Boolean {

        return ContextCompat.checkSelfPermission(this,
            android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    //creacion del estilo de imagen
    private fun createImageFile(): File? {
    val timeStamp : String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir : File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir).apply {
                currentPhotoPath = absolutePath
        }

    }

    //Manejo de exepciones de la camara
    private fun dispatchTakePictureIntent() {
        //Cuando se toma la foto
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile : File? = try {
                    createImageFile()
                }catch (ex : IOException){
                    Toast.makeText(
                        this, "Error al crear el archivo de imagen", Toast.LENGTH_SHORT
                    ).show()
                    null
                }

                //Enrutamiento de la imagen
                photoFile?.also {
                    photoURI= FileProvider.getUriForFile(
                        this, "${packageName}.fileprovider",
                         it
                )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGEN_CAMPUTE)
            }
        }
    }
    }

    private fun requestCameraPermission() {

        ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
    }


//segunda opcion de verificacion de uso de camara

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            REQUEST_CAMERA_PERMISSION ->{
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    dispatchTakePictureIntent()
                }else{
                    Toast.makeText(this, "Permiso de camara denegado", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGEN_CAMPUTE && resultCode == RESULT_OK){
            displayImage()
            galleryAddPic()
        }
    }

        private fun displayImage() {
            currentPhotoPath?.let { path ->
                val file = File(path)
                if (file.exists()){
                    val uri= Uri.fromFile(file)
                    imageView.setImageURI(uri)

                    Toast.makeText(this, "Imagen capturada en: $path", Toast.LENGTH_SHORT).show()
                }
            }
        }

    private fun galleryAddPic(){
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            currentPhotoPath?.let {
                val f = File(it)
                mediaScanIntent.data = Uri.fromFile(f)
                sendBroadcast(mediaScanIntent)
            }
        }
    }

}