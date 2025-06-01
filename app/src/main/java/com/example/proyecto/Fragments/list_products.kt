package com.example.proyecto.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.R

class list_products : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Producto_Adapter
    private val listaProductos = mutableListOf<Prodcuto>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragments_products, container, false)

        recyclerView = view.findViewById(R.id.recycle_products)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        // Agregar productos de ejemplo
        cargarProductos()

        adapter = Producto_Adapter(listaProductos) { producto ->
            // Navegar al detalle cuando se hace clic en un producto
            val detalleFragment = fracment_descrip_product.newInstance(producto)
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, detalleFragment)
                .addToBackStack(null)
                .commit()
        }

        recyclerView.adapter = adapter

        return view
    }

    private fun cargarProductos() {
        // Productos de ejemplo (normalmente vendrían de una API o base de datos)
        listaProductos.add(Prodcuto(1, "iPhone", "Diseñamos una arquitectura completamente nueva y giramos los lentes 45 " +
                "grados. ", 2579010.00, R.drawable.iphone_12_5766344_640))

        listaProductos.add(Prodcuto(2, "Computador Portátil LENOVO IdeaPad Slim 3 - 15.3\" Pulgadas - Intel Core i5 - RAM 24GB - Disco SSD 512GB - Gris.",
            "Descubre el LENOVO IdeaPad Slim 3, el compañero perfecto para llevar tu día a día al siguiente nivel. Con un rendimiento excepcional, has tus" +
                    " tareas diarias más rapidas y eficientes. ", 2399000.00, R.drawable.lenovo))

        listaProductos.add(Prodcuto(3, "Consola PS5 Digital 1TB Slim Blanco|Negro + 1 Control inalámbrico + Juego PS5 Returnal (Voucher) + Juego PS5 Ratchet & " +
                "Clank: Rift Apart (Voucher)", "Vive el poder del PlayStation 5 Slim Digital, una consola que combina rendimiento y diseño compacto.",
            2339900.00, R.drawable.play))

        listaProductos.add(Prodcuto(4, "Silla HP Omen Gaming Citadel Negro", "Compite durante horas con máxima comodidad. Conoce la Silla OMEN CITADEL, " +
                "creada para que te sumerjas en largas batallas sin preocuparte por estar cómodo. ", 2099000.00, R.drawable.silla))

        listaProductos.add(Prodcuto(5, "Computador Portátil Gamer ASUS TUF 15.6\" Pulgadas FX507ZC4 - Intel Core i5 - RAM 16GB - Disco SSD 512 GB - Gris", "La ASUS TUF Gaming F15 es un " +
                "resistente portátil gamer que te entregará todo lo que necesitas para ganar, gracias a su pantalla de alta velocidad, sistema térmico mejorado y batería de alta duración.", 3749000.00, R.drawable.asus))

        listaProductos.add(Prodcuto(6, "Reloj HUAWEI GT5 Pro 46mm Negro", "La Serie GT 5 persigue la Innovación técnica de vanguardia. ", 1199900.00, R.drawable.smart))
    }
}