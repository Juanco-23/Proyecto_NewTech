package com.example.proyecto.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto.Activities.Login_Antivity
import com.example.proyecto.R


class Home_Activity : AppCompatActivity() {

    private lateinit var bttn_comenzar_acthome: Button


    //Constructor del Activity_Home
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bttn_comenzar_acthome = findViewById(R.id.bttn_comenzar_acthome)

        bttn_comenzar_acthome.setOnClickListener{

            val intent = Intent(this, Login_Antivity::class.java)
            startActivity(intent)

        }


    }

}