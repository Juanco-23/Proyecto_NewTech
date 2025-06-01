package com.example.proyecto.Activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.Fragments.Prodcuto
import com.example.proyecto.Fragments.Producto_Adapter
import com.example.proyecto.Fragments.list_products
import com.example.proyecto.R

class Tienda_Activity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val listaProductosFragment = list_products()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, listaProductosFragment)
                .commit()
        }
    }


    }
