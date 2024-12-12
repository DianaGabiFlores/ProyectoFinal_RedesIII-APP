package com.dgfp.proyectoredes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Vector

class UsuarioActivity: AppCompatActivity() {

    var Nombre: TextView? = null
    var Apellidos: TextView? = null
    var Email: TextView? = null
    var Telefono: TextView? = null
    var Tipo: TextView? = null
    var Cafeteria: TextView? = null
    var Sucursal: TextView? = null
    var imagenC: ImageView? = null
    private var toast: Toast? = null
    private lateinit var baseURL: String

    var db: DBSQLite = DBSQLite(this) //Base de Datos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.usuario)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        baseURL = baseContext.getString(R.string.baseURL)
        Nombre = findViewById(R.id.name)
        Email = findViewById(R.id.email)
        Telefono = findViewById(R.id.telefono)
        Tipo = findViewById(R.id.tipoUser)
        Apellidos = findViewById(R.id.apellidos)
        Cafeteria = findViewById(R.id.cafeteria)
        Sucursal = findViewById(R.id.sucursal)
        imagenC = findViewById(R.id.imagenC)


        var datosUsuario: Vector<String> = db.obtenerUsuario()
        if(datosUsuario != null) {
            Toast.makeText(this, "Bienvenido "+datosUsuario[1], Toast.LENGTH_LONG).show()
            Nombre?.setText(datosUsuario[1])
            Apellidos?.setText(datosUsuario[2] + " " + datosUsuario[3])
            Email?.setText(datosUsuario[6])
            Telefono?.setText(datosUsuario[5])
            Tipo?.append(datosUsuario[7])
        }

        if(datosUsuario[7] == "Encargado"){
            obtenerInfo(datosUsuario[6])
        }


    }

    //Obtener todas las cafeterías
    fun obtenerInfo(email: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(APIService::class.java)
        val emailInfo = DCEmail(email)
        apiService.getSucursalEnc(emailInfo).enqueue(object : Callback<DCEncargadoSucursal> {
            override fun onResponse(call: Call<DCEncargadoSucursal>, response: Response<DCEncargadoSucursal>) {
                if (response.isSuccessful) {
                    val info = response.body()
                    if (info != null) {
                        Cafeteria?.visibility = View.VISIBLE
                        Sucursal?.visibility = View.VISIBLE
                        imagenC?.visibility = View.VISIBLE
                        Cafeteria?.setText(info.NombreCafe)
                        Sucursal?.setText(info.NombreSucu)
                    }
                }
            }

            override fun onFailure(call: Call<DCEncargadoSucursal>, t: Throwable) {
                mostrarToast("Error de conexión: " + t.message)
            }
        })
    }

    fun mostrarToast(mensaje: String) {
        if(toast != null) toast!!.cancel()
        toast = Toast.makeText(this@UsuarioActivity, mensaje, Toast.LENGTH_LONG)
        toast!!.show()
    }


}

