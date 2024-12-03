package com.dgfp.proyectoredes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
    private var baseURL = "http://192.168.100.53:3000/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.crear_cuenta)

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
                registrarUsuario(correo, contrasena)
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

    fun registrarUsuario(correo: String, contrasena: String) {
        //Obtener todos los usuarios
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(APIServiceUsuario::class.java)
        apiService.getPostsUsuarios().enqueue(object : Callback<List<Usuario>> {
            override fun onResponse(call: Call<List<Usuario>>, response: Response<List<Usuario>>) {
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
                            txtApellidoP.setText("")
                            txtApellidoM.setText("")
                            txtTelefono.setText("")
                            txtCorreo.setText("")
                            txtContrasena.setText("")
                            mostrarToast("El correo ya está registrado.")
                        }
                        else {
                            //Registrar
                            val usuarioNuevo = Usuario(
                                Id_Usuario = "5",
                                Nombre = txtNombre.text.toString().trim(),
                                Primer_Apellido = txtApellidoP.text.toString().trim(),
                                Segundo_Apellido = txtApellidoM.text.toString().trim(),
                                Contrasena = contrasena,
                                Email = correo,
                                Telefono = txtTelefono.text.toString().trim(),
                                Tipo = "Cliente"
                            )
                            Log.d("Usuario", "$usuarioNuevo")

                            apiService.crearUsuario(usuarioNuevo).enqueue(object : Callback<Usuario> {
                                override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
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

                                override fun onFailure(call: Call<Usuario>, t: Throwable) {
                                    mostrarToast("Error de conexión: ${t.message}")
                                }
                            })
                        }
                    }
                }
            }
            override fun onFailure(call: Call<List<Usuario>>, t: Throwable) {
                //Manejo de error
                mostrarToast("Error de conexión: " + t.message)
            }
        })
    }

    fun mostrarToast(mensaje: String) {
        if(toast != null) toast!!.cancel()
        toast = Toast.makeText(this@RegistrarActivity, mensaje, Toast.LENGTH_LONG)
        toast!!.show()
    }
}