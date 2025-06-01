package com.example.proyecto.Activities

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class Maps_Activity : AppCompatActivity(), OnMapReadyCallback {

    //variable del mapa
    private lateinit var mMap: GoogleMap

    //Ubicaciones respectivas a los puntos fisicos

    private val ubacion1 = LatLng(4.6097, -74.0817)//bogota
    private val ubacion2 = LatLng(51.50853, -0.12574)//londres
    private val ubacion3 = LatLng(35.6895, 139.6917)//tokio

    //Constructor de clase Maps
    override fun onCreate(savedInstanceState: Bundle?){
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_maps)

        //Lamamiento del activity maps desde layout

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.maps_location) as SupportMapFragment // Use the correct ID
        mapFragment.getMapAsync(this)
        setupButtonListeners()


    }

    //Funcion para cargar las ubicaciones en el mapa
    private fun setupButtonListeners(){

        findViewById<Button>(R.id.bttn_ubication_1).setOnClickListener{

            moveToLocation(ubacion1, "Bogota")
        }
        findViewById<Button>(R.id.bttn_ubication_2).setOnClickListener{

            moveToLocation(ubacion2, "Londres")
        }
        findViewById<Button>(R.id.bttn_ubication_3).setOnClickListener{

            moveToLocation(ubacion3, "Tokio")
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {

        mMap= googleMap

        //Habilitacion del Zoom
        mMap.uiSettings.isZoomControlsEnabled = true

        //Mover la camara a una ubicacion en singular
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubacion1, 15f))

    }

    //configuracion de Ubicacion en el mapa
    private fun moveToLocation(ubacion1: LatLng, s: String) {

        //limpieza del marcador de ubicacion anterior
        mMap.clear()

        //actualizacion del marcador de ubicacion
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubacion1, 15f))

        //agregar el marcador en la posicion actual
        mMap.addMarker(MarkerOptions().position(ubacion1).title(s))


    }

}