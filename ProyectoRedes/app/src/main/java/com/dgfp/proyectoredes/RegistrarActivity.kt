package com.dgfp.proyectoredes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegistrarActivity : AppCompatActivity() {
    private var toast: Toast? = null
    private lateinit var txtNombre: EditText
    private lateinit var txtApellidoP: EditText
    private lateinit var txtApellidoM: EditText
    private lateinit var txtTelefono: EditText
    private lateinit var txtCorreo: EditText
    private lateinit var txtContrasena: EditText
    private lateinit var btnCrearCuenta: Button
    var db: DBSQLite = DBSQLite(this) //Base de Datos
    private lateinit var baseURL: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.crear_cuenta)

        baseURL = baseContext.getString(R.string.baseURL)
        txtNombre = findViewById(R.id.txtNombre)
        txtApellidoP = findViewById(R.id.txtApellidoP)
        txtApellidoM = findViewById(R.id.txtApellidoM)
        txtTelefono = findViewById(R.id.txtTelefono)
        txtCorreo = findViewById(R.id.txtEmail)
        txtContrasena = findViewById(R.id.txtContrasena)
        btnCrearCuenta = findViewById(R.id.btnSingIn)

//        //Listener
        btnCrearCuenta.setOnClickListener(evento)
    }


    private val evento = View.OnClickListener { v ->
        if(v == btnCrearCuenta) {
            val correo = txtCorreo.text.toString().trim()
            val contrasena = txtContrasena.text.toString().trim()

            if(txtNombre.text.toString().isNotEmpty() && txtApellidoP.text.isNotEmpty() && txtApellidoM.text.isNotEmpty() && txtTelefono.text.isNotEmpty() &&
               correo.isNotEmpty() && contrasena.isNotEmpty()) {
                registrarUsuario()
            }
            else {
                if(txtNombre.text.toString().isEmpty()) mostrarToast("Ingresar Nombre.")
                else if(txtApellidoP.text.toString().isEmpty()) mostrarToast("Ingresar Apellido Paterno.")
                else if(txtApellidoM.text.toString().isEmpty()) mostrarToast("Ingresar Apellidos Materno.")
                else if(txtTelefono.text.toString().isEmpty()) mostrarToast("Ingresar Teléfono.")
                else if(correo.isEmpty()) mostrarToast("Ingresar Correo.")
                else if(contrasena.isEmpty()) mostrarToast("Ingresar Contraseña.")
            }
        }
    }

    fun registrarUsuario() {
        //Obtener todos los usuarios
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(APIService::class.java)

        val usuarioNuevo = DCUsuario(
            Id_Usuario = "0",
            Nombre = txtNombre.text.toString().trim(),
            Primer_Apellido = txtApellidoP.text.toString().trim(),
            Segundo_Apellido = txtApellidoM.text.toString().trim(),
            Contrasena = txtContrasena.text.toString().trim(),
            Email = txtCorreo.text.toString().trim(),
            Telefono = txtTelefono.text.toString().trim(),
            Tipo = "Cliente"
        )

        apiService.crearUsuario(usuarioNuevo).enqueue(object : Callback<DCRegistrarResponse> {
            override fun onResponse(call: Call<DCRegistrarResponse>, response: Response<DCRegistrarResponse>) {
                if(response.isSuccessful) {
                    val body = response.body()

                    if(body?.success == true) {
                        val idUsuario = body.usuario
                        mostrarToast("Usuario registrado.")
                    }
                    else {
                        val mensaje = body?.message ?: "Error al registrar usuario"
                        mostrarToast(mensaje)
                    }
                }
                else {
                    if(response.code() == 400) mostrarToast("El correo ya está registrado")
                    else mostrarToast("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<DCRegistrarResponse>, t: Throwable) {
                mostrarToast("Error de red: ${t.message}")
            }
        })
    }

    fun mostrarToast(mensaje: String) {
        if(toast != null) toast!!.cancel()
        toast = Toast.makeText(this@RegistrarActivity, mensaje, Toast.LENGTH_LONG)
        toast!!.show()
    }
}