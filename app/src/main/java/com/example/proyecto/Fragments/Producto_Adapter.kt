package com.example.proyecto.Fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.R

class Producto_Adapter (

    private val productos: List<Prodcuto>,
    private val onProductoClick: (Prodcuto) -> Unit
    ) : RecyclerView.Adapter<Producto_Adapter.ProductoViewHolder>() {

        class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imagenProducto: ImageView = itemView.findViewById(R.id.img_product_item)
            val nombreProducto: TextView = itemView.findViewById(R.id.nombre_product_item)
            val precioProducto: TextView = itemView.findViewById(R.id.pres_product_item)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]

        holder.imagenProducto.setImageResource(producto.img)
        holder.nombreProducto.text = producto.name
        holder.precioProducto.text = "$${producto.precio}"

        holder.itemView.setOnClickListener {
            onProductoClick(producto)
        }
    }

    override fun getItemCount() = productos.size

}

