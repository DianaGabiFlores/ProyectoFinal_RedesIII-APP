package com.dgfp.proyectoredes

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private var toast: Toast? = null
    private lateinit var btnLogin: Button
    private lateinit var btnCrearCuenta: Button
    private lateinit var txtCorreo: EditText
    private lateinit var txtContrasena: EditText
    var db: DBSQLite = DBSQLite(this) //Base de Datos
    private var baseURL = "http://192.168.1.163:3000/"

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
        txtCorreo = findViewById(R.id.txtCorreo)
        txtContrasena = findViewById(R.id.txtContrasena)

        //Listener
        btnLogin.setOnClickListener(evento)
        btnCrearCuenta.setOnClickListener(evento)
    }

    private val evento = View.OnClickListener { v ->
        if(v == btnLogin) {
            val correo = txtCorreo.text.toString().trim()
            val contrasena = txtContrasena.text.toString().trim()

            if(correo.isNotEmpty() && contrasena.isNotEmpty()) {
                login(correo, contrasena)
            }
            else {
                if(correo.isEmpty()) mostrarToast("Ingresar correo.")
                else if(contrasena.isEmpty()) mostrarToast("Ingresar contraseña.")
                else mostrarToast("Ingresar correo y contraseña.")
            }
        }
        else if(v == btnCrearCuenta) {
            val intent = Intent(this@MainActivity, PedidosCActivity::class.java)
            startActivity(intent)
        }
    }

    //Es inseguro recibir todos los usuarios y contraseñas, es mejor enviar el usuario y contraseña por método post
    /*
    fun login(correo: String, contrasena: String) {
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
                                if(contrasena == usuario.Contrasena) {
                                    db.insertarUsuario(usuario.Id_Usuario, usuario.Nombre, usuario.Primer_Apellido,
                                        usuario.Segundo_Apellido, usuario.Contrasena, usuario.Telefono, usuario.Email, usuario.Tipo)

                                    val intent = Intent(this@MainActivity, CafeteriaActivity::class.java)
                                    startActivity(intent)
                                }
                                else {
                                    txtContrasena.setText("")
                                    mostrarToast("Contraseña incorrecta.")
                                    return
                                }
                            }
                        }
                        if(!existeUsuario) {
                            txtCorreo.setText("")
                            txtContrasena.setText("")
                            mostrarToast("El correo no está registrado.")
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
    */

    fun login(correo: String, contrasena: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(APIService::class.java)

        val objUsuario = DCUsuarioLlaves(
            Correo = correo,
            Contrasena = contrasena
        )

        apiService.loginUsuario(objUsuario).enqueue(object : Callback<DCLoginResponse> {
            override fun onResponse(call: Call<DCLoginResponse>, response: Response<DCLoginResponse>) {
                val codigo = response.code()

                //La respuesta HTTP tiene un código en el rango 200 y 299, 404, 401
                if(response.isSuccessful || codigo == 404 || codigo == 401) {
                    val respuesta = response.body()

                    if(respuesta != null) { //Inicio de sesión exitoso
                        if(respuesta.success) {
                            //mostrarToast(respuesta.message)
                            val usuario: DCUsuario = respuesta.usuario!!

                            //Guardar en la BD
                            db.insertarUsuario(usuario.Id_Usuario, usuario.Nombre, usuario.Primer_Apellido,
                                usuario.Segundo_Apellido, contrasena, usuario.Telefono, correo, usuario.Tipo)
                            val intent = Intent(this@MainActivity, CafeteriaActivity::class.java)
                            startActivity(intent)
                        }
                    }

                    //El correo no está registrado.
                    if(codigo == 404) {
                        mostrarToast("El correo no está registrado.")
                        txtCorreo.setText("")
                        txtContrasena.setText("")
                    }
                    //Contraseña incorrecta.
                    if(codigo == 401) {
                        mostrarToast("Contraseña incorrecta.")
                        txtContrasena.setText("")
                    }
                }
            }

            override fun onFailure(call: Call<DCLoginResponse>, t: Throwable) {
                //Manejo de error
                mostrarToast("Error de conexión: " + t.message)
            }
        })
    }

    fun mostrarToast(mensaje: String) {
        if(toast != null) toast!!.cancel()
        toast = Toast.makeText(this@MainActivity, mensaje, Toast.LENGTH_LONG)
        toast!!.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        db.borrarUsuario()
    }
}