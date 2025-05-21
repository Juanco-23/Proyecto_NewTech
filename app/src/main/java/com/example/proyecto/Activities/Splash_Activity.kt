package com.example.proyecto.Activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto.R

class Splash_Activity : AppCompatActivity(){

    //Temporalizador para el activity
    private val SPLASH_TIME_OUT : Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splahs)

        //Funcion para redireccionar los activities
        Handler(Looper.getMainLooper()).postDelayed({

            val intent= Intent( this, Home_Activity::class.java )

            //Inicia el redireccionamiento
            startActivity(intent)
            //Lo finalize
            finish()

        } , SPLASH_TIME_OUT )

    }
}