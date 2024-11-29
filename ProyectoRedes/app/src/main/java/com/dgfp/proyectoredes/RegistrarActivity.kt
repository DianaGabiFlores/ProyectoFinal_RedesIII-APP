package com.dgfp.proyectoredes

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegistrarActivity : AppCompatActivity() {
    var btnCrearCuenta: ImageButton? = null
    var Nombre: TextView? = null
    var Apellidos: TextView? = null
    var Email: TextView? = null
    var Telefono: TextView? = null
    var Contrasena: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.crear_cuenta)

        btnCrearCuenta = findViewById(R.id.btnSingIn)
        Nombre = findViewById(R.id.txtUsuario)
        Apellidos = findViewById(R.id.txtApellidos)
        Email = findViewById(R.id.txtEmail)
        Telefono = findViewById(R.id.txtTelefono)
        Contrasena = findViewById(R.id.txtContrasena)

        //Listener
        btnCrearCuenta!!.setOnClickListener(evento)
    }


    private val evento = View.OnClickListener { v ->
        if(v == btnCrearCuenta){
            if(Nombre?.getText().toString().isNotEmpty() && Apellidos?.getText().toString().isNotEmpty() &&
                Email?.getText().toString().isNotEmpty() && Telefono?.getText().toString().isNotEmpty() && Contrasena?.getText().toString().isNotEmpty()){


                //Obtener los Usuarios y verificar que no existe
                var existeUsuario = false
                val retrofit = Retrofit.Builder()
                    .baseUrl("192.168.1.0:3000/usuarios")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val apiService = retrofit.create(APIServiceUsuario::class.java)
                apiService.getPostsUsuarios().enqueue(object : Callback<List<Usuario>> {
                    override fun onResponse(call: Call<List<Usuario>>, response: Response<List<Usuario>>) {
                        if (response.isSuccessful) {
                            val usuarios = response.body()

                            if(usuarios != null) {
                                for(usuario in usuarios) {
                                    if(Email!!.getText().toString() == usuario.Email ) {
                                        existeUsuario = true
                                        return
                                    }
                                }
                            }

                            if(!existeUsuario) {
                                //Mandar a Insertar a NodeJS

                                val intent = Intent(this@RegistrarActivity, CafeteriaActivity::class.java)
                                startActivity(intent)
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<Usuario>>, t: Throwable) {
                        // Manejo de error
                    }
                })


            }

        }
        else if(v == btnCrearCuenta){

        }
    }
}