package com.example.proyecto.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.proyecto.R

class fracment_descrip_product : Fragment() {

    private lateinit var producto : Prodcuto

    companion object{

        private const val ARG_PRODUCTO= "producto"

        fun newInstance(producto: Prodcuto): fracment_descrip_product{

            val fragment = fracment_descrip_product()
            val args= Bundle()

            //Usamos put parceable para serializar el objeto Producto
            args.putParcelable(ARG_PRODUCTO,producto)
            fragment.arguments= args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Verficacion de los argumentos
        if (arguments != null){

            //A partir de Android 13 (API 33) hay que usar getPaceable()
            producto = if (android.os.Build.VERSION.SDK_INT >= 33){
                arguments?.getParcelable(ARG_PRODUCTO, Prodcuto::class.java)
                    ?: Prodcuto(0, "Sin Nombre", "Sin Descripcion",0.0, R.drawable.placeholder_removebg_preview)
            }
            else{
                @Suppress("DEPRECATION")
                arguments?.getParcelable(ARG_PRODUCTO)
                    ?: Prodcuto(0, "Sin Nombre", "Sin Descripcion",0.0, R.drawable.placeholder_removebg_preview)
            }

        } else{

            //Si no hay argumentos asignamos un producto vacio
            producto= Prodcuto(0, "Sin Nombre", "Sin Descripcion",0.0, R.drawable.placeholder_removebg_preview)
        }
    }

    //Funcion para controlar las vistas
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.frangment_descrip_product,container,false)

        val img_producto= view.findViewById<ImageView>(R.id.img_descrip_product)
        val name_producto= view.findViewById<TextView>(R.id.name_descrip_product)
        val prescio_descrip_producto= view.findViewById<TextView>(R.id.text_precio_descrip_product)
        val descrip_producto= view.findViewById<TextView>(R.id.text_descrip_product)
        val bttn_comprar_descrp_product= view.findViewById<Button>(R.id.bttn_comprar_descrp_product)

        img_producto.setImageResource(producto.img)
        name_producto.text= producto.name
        descrip_producto.text= producto.descripcion
        prescio_descrip_producto.text= "Precio: $${producto.precio}"

        bttn_comprar_descrp_product.setOnClickListener{
        //Logica para agregar al carrito
            //Mensaje
            Toast.makeText(requireContext(), "Prodcuto agregado al carrito", Toast.LENGTH_SHORT).show()

        }

        return view

    }


}