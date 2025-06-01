package com.example.proyecto.Activities


import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class photoGallery_Activity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: TextView
    private lateinit var photoAdapter: PhotoAdapter
    private var photosList = mutableListOf<File>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_gallery)

        recyclerView = findViewById(R.id.recyclerView_act_photo_gallery)
        emptyView = findViewById(R.id.emptyView_photo_gallery)

        setupRecyclerView()
        loadPhotos()
    }

    private fun setupRecyclerView() {
        photoAdapter = PhotoAdapter(photosList) { photoFile ->
            // Abrir la foto en tamaño completo
            openPhoto(photoFile)
        }

        recyclerView.apply {
            layoutManager = GridLayoutManager(this@photoGallery_Activity, 3)
            adapter = photoAdapter
        }
    }

    private fun loadPhotos() {
        photosList.clear()

        // Obtener el directorio donde se guardan las fotos
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        if (storageDir != null && storageDir.exists()) {
            // Filtrar solo archivos de imagen
            val photos = storageDir.listFiles { file ->
                file.isFile && (file.extension.equals("jpg", true) ||
                        file.extension.equals("jpeg", true) ||
                        file.extension.equals("png", true))
            }

            photos?.let {
                // Ordenar por fecha de modificación (más recientes primero)
                photosList.addAll(it.sortedByDescending { file -> file.lastModified() })
            }
        }

        // Actualizar UI
        if (photosList.isEmpty()) {
            recyclerView.visibility = View.GONE
            emptyView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyView.visibility = View.GONE
            photoAdapter.notifyDataSetChanged()
        }
    }

    private fun openPhoto(photoFile: File) {
        val photoURI = FileProvider.getUriForFile(
            this,
            "${packageName}.fileprovider",
            photoFile
        )

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(photoURI, "image/*")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Recargar fotos cuando se vuelve a la actividad
        loadPhotos()
    }
}

// Adapter para el RecyclerView
class PhotoAdapter(
    private val photos: List<File>,
    private val onPhotoClick: (File) -> Unit
) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_photo, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    override fun getItemCount() = photos.size

    inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView_photo)
        private val textViewDate: TextView = itemView.findViewById(R.id.textView_Date)

        fun bind(photoFile: File) {
            // Cargar imagen
            imageView.setImageURI(Uri.fromFile(photoFile))

            // Mostrar fecha
            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            textViewDate.text = dateFormat.format(Date(photoFile.lastModified()))

            // Click listener
            itemView.setOnClickListener {
                onPhotoClick(photoFile)
            }
        }
    }

}
