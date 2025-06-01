package com.example.proyecto.Activities

import android.app.ComponentCaller
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto.MainActivity
import com.example.proyecto.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Api
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class Login_Antivity: AppCompatActivity (){

    private lateinit var bttn_google: Button
    private lateinit var myGoogleSingCount : GoogleSignInClient
    private val RC_SIGN_IN= 123
    private val TAG= "GoogleSignIn"


    private lateinit var btt_whitEmail : Button

    //Constructor del Activity_Login
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        //Inicio de secion con correo y contraseña
         btt_whitEmail = findViewById(R.id.btt_whitEmail)
         btt_whitEmail.setOnClickListener{
                val intent = Intent(this, Camera_Activity::class.java)
                startActivity(intent)

            }



        //Configuracion para el Google Sign In
        val gso= GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()

        //creacion de un perfil Ususario cliente con google
        myGoogleSingCount= GoogleSignIn.getClient(this, gso)

        bttn_google = findViewById(R.id.btt_google)

        bttn_google.setOnClickListener(){
            signIn()

        }
    }

    private fun signIn(){
        val signInIntent= myGoogleSingCount.signInIntent
        startActivityForResult(signInIntent,RC_SIGN_IN)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        caller: ComponentCaller
    ) {
        super.onActivityResult(requestCode, resultCode, data, caller)

        if (requestCode == RC_SIGN_IN){
            val task= GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }

    }

    private fun handleSignInResult(completeTask: Task<GoogleSignInAccount>) {
        try{
            val account = completeTask.getResult(ApiException::class.java)

            //Inicio Exitso
            Log.d(TAG,"signSucces: ${account.email}")
            Toast.makeText(this, "Bienvenido ${account.displayName}", Toast.LENGTH_SHORT).show()

            //Redireccionamiento a main Activity
            intent = Intent(this, Maps_Activity::class.java)
            intent.putExtra("email",account.email)
            intent.putExtra("name", account.displayName)
            startActivity(intent)

        }catch (e : ApiException){
            //Si hay un ERROR al iniciar sesion
            Log.w(TAG, "signResult:failed code=" + e.status)
            Toast.makeText(this, "Error al iniciar sesion", Toast.LENGTH_SHORT).show()
        }
    }
}