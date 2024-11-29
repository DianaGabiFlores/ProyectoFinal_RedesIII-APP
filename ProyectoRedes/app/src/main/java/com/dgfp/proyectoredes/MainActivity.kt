package com.dgfp.proyectoredes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsAnimation
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dgfp.practica14kt_controlp3.OnSelectedTexto
import retrofit2.Response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    var btnLogin: Button? = null
    var btnCrearCuenta: Button? = null
    var Email: EditText? = null
    var Contrasena: EditText? = null

    var usuarios: List<Usuario>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnLogin = findViewById(R.id.btnLogin)
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta)
        Email = findViewById(R.id.txtUsuario)
        Contrasena = findViewById(R.id.txtContrasena)

        asignarEventos()

        //Obtener los Usuarios
        val retrofit = Retrofit.Builder()
            .baseUrl("http://172.16.155.1:3000/usuarios")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(APIServiceUsuario::class.java)
        apiService.getPostsUsuarios().enqueue(object : Callback<List<Usuario>> {
            override fun onResponse(call: Call<List<Usuario>>, response: Response<List<Usuario>>) {
                if (response.isSuccessful) {
                     usuarios = response.body()
//                    if(posts != null) {
//                        posts.
//                    }
                }
            }

            override fun onFailure(call: Call<List<Usuario>>, t: Throwable) {
                // Manejo de error
            }
        })
    }

    fun asignarEventos(){
        btnLogin!!.setOnClickListener(evento)
        btnCrearCuenta!!.setOnClickListener(evento)
    }

    private val evento = View.OnClickListener { v ->
        if (v == btnLogin) {
            if (Email?.getText().toString().isNotEmpty() && Contrasena?.getText().toString()
                    .isNotEmpty()) {
                for(usuario in usuarios!!){
                    if(Email?.getText().toString() == usuario.Email && Contrasena?.getText().toString() == usuario.Contrasena) {
                        Toast.makeText(applicationContext, "Entre", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@MainActivity, CafeteriaActivity::class.java)
                        this@MainActivity.startActivity(intent)
                    }
                }
            }

        } else if (v == btnCrearCuenta) {
            val intent = Intent(this, RegistrarActivity::class.java)
            this.startActivity(intent)
        }
    }

}