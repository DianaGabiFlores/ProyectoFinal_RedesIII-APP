package com.dgfp.proyectoredes

import android.content.Intent
import android.os.Bundle
import android.view.View
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
    private lateinit var txtApellidos: EditText
    private lateinit var txtTelefono: EditText
    private lateinit var txtCorreo: EditText
    private lateinit var txtContrasena: EditText
    private lateinit var btnCrearCuenta: EditText
    var db: DBSQLite = DBSQLite(this) //Base de Datos
    private var baseURL = "http://192.168.1.163:3000/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.crear_cuenta)

        txtNombre = findViewById(R.id.txtNombre)
        txtApellidos = findViewById(R.id.txtApellidos)
        txtTelefono = findViewById(R.id.txtTelefono)
        txtCorreo = findViewById(R.id.txtEmail)
        txtContrasena = findViewById(R.id.txtContrasena)
        btnCrearCuenta = findViewById(R.id.btnSingIn)

        //Listener
        btnCrearCuenta.setOnClickListener(evento)
    }


    private val evento = View.OnClickListener { v ->
        if(v == btnCrearCuenta) {
            val correo = txtCorreo.text.toString().trim()
            val contrasena = txtContrasena.text.toString().trim()

            if(txtNombre.text.toString().isNotEmpty() && txtApellidos.text.isNotEmpty() && txtTelefono.text.isNotEmpty() &&
               correo.isNotEmpty() && contrasena.isNotEmpty()) {
                //registrarUsuario(correo, contrasena)
            }
            else {
                if(txtNombre.text.toString().isEmpty()) mostrarToast("Ingresar Nombre.")
                else if(txtApellidos.text.toString().isEmpty()) mostrarToast("Ingresar Apellidos.")
                else if(txtTelefono.text.toString().isEmpty()) mostrarToast("Ingresar Teléfono.")
                else if(correo.isEmpty()) mostrarToast("Ingresar Correo.")
                else if(contrasena.isEmpty()) mostrarToast("Ingresar Contraseña.")
            }
        }
    }
    /*
    fun registrarUsuario(correo: String, contrasena: String) {
        //Obtener todos los usuarios
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(APIServiceUsuario::class.java)
        apiService.getPostsUsuarios().enqueue(object : Callback<List<DCUsuario>> {
            override fun onResponse(call: Call<List<DCUsuario>>, response: Response<List<DCUsuario>>) {
                if(response.isSuccessful) {
                    val usuarios = response.body()
                    if(usuarios != null) {
                        var existeUsuario = false
                        for(usuario in usuarios) {
                            if(correo == usuario.Email) {
                                existeUsuario = true
                            }
                        }
                        if(existeUsuario) {
                            txtNombre.setText("")
                            txtApellidos.setText("")
                            txtTelefono.setText("")
                            txtCorreo.setText("")
                            txtContrasena.setText("")
                            mostrarToast("El correo ya está registrado.")
                        }
                        else {
                            //Registrar
                            val usuarioNuevo = DCUsuario(
                                Id_Usuario = "5",
                                Nombre = txtNombre.text.toString().trim(),
                                Primer_Apellido = txtApellidos.text.toString().trim(),
                                Segundo_Apellido = txtApellidos.text.toString().trim(),
                                Contrasena = contrasena,
                                Email = correo,
                                Telefono = txtTelefono.text.toString().trim(),
                                Tipo = "Cliente"
                            )

                            apiService.crearUsuario(usuarioNuevo).enqueue(object : Callback<DCUsuario> {
                                override fun onResponse(call: Call<DCUsuario>, response: Response<DCUsuario>) {
                                    if(response.isSuccessful) {
                                        mostrarToast("Usuario registrado.")
                                        val intent = Intent(this@RegistrarActivity, MainActivity::class.java)
                                        startActivity(intent)
                                        this@RegistrarActivity.finish()
                                    }
                                    else {
                                        mostrarToast("Error al registrar el usuario: " + response.code())
                                    }
                                }

                                override fun onFailure(call: Call<DCUsuario>, t: Throwable) {
                                    mostrarToast("Error de conexión: ${t.message}")
                                }
                            })
                        }
                    }
                }
            }
            override fun onFailure(call: Call<List<DCUsuario>>, t: Throwable) {
                //Manejo de error
                mostrarToast("Error de conexión: " + t.message)
            }
        })
    }
    */

    fun mostrarToast(mensaje: String) {
        if(toast != null) toast!!.cancel()
        toast = Toast.makeText(this@RegistrarActivity, mensaje, Toast.LENGTH_LONG)
        toast!!.show()
    }
}