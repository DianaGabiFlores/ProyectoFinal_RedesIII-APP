package com.dgfp.proyectoredes

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
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

class ComidaActivity : AppCompatActivity() {
    private var toast: Toast? = null
    var adaptadorDatos: ComidaAdapter? = null
    var datos: ArrayList<Comida> = ArrayList()
    private lateinit var baseURL: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_comida)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        baseURL = baseContext.getString(R.string.baseURL)

        if(intent.extras != null) {
            val intent = intent
            val idCafeteria = intent.getStringExtra("id")
            val nombreCafeteria = intent.getStringExtra("cafeteria")
            obtenerMenus(idCafeteria!!, nombreCafeteria!!)
        }

        val listview = findViewById<ListView>(R.id.lvComidas)
        /*
        datos.add(Comida("Hamburguesa", 50.99f, "5 minutos", R.drawable.ic_launcher_background))
        datos.add(Comida("Quesadilla", 20.50f, "2 minutos", R.drawable.ic_launcher_background))
        datos.add(Comida("Pescado", 100.99f, "17 minutos", R.drawable.ic_launcher_background))
        */

        adaptadorDatos = ComidaAdapter(this, datos)
        listview.adapter = adaptadorDatos

        //Listener
        listview.onItemClickListener = AdapterView.OnItemClickListener { adapterView: AdapterView<*>, view: View?, i: Int, l: Long ->
            val comida: Comida = adapterView.getItemAtPosition(i) as Comida
            val intent = Intent(this@ComidaActivity, OrdenarActivity::class.java)
            intent.putExtra("comida", comida)
            startActivity(intent)
        }
    }

    //Obtener todos los menus
    fun obtenerMenus(idCafeteria: String, nombreCafeteria: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(APIService::class.java)

        apiService.getMenus().enqueue(object : Callback<List<DCMenu>> {
            override fun onResponse(call: Call<List<DCMenu>>, response: Response<List<DCMenu>>) {
                if(response.isSuccessful) {
                    val menus = response.body()
                    if(menus != null) {
                        datos.clear()

                        for(menu in menus) {
                            if(menu.Id_Cafeteria == idCafeteria) {
                                datos.add(
                                    Comida(
                                        menu.Id_Comida,
                                        menu.Id_Cafeteria,
                                        menu.Id_Sucursal,
                                        menu.Nombre,
                                        menu.Precio.toFloat(),
                                        menu.TiempoPrepa,
                                        menu.Ingredientes,
//                                        R.drawable.ic_launcher_background
                                        R.drawable.plato
                                    )
                                )
                            }
                        }
                        runOnUiThread {
                            adaptadorDatos?.notifyDataSetChanged()
                        }
                    }
                }
            }
            override fun onFailure(call: Call<List<DCMenu>>, t: Throwable) {
                //Manejo de error
                mostrarToast("Error de conexión: " + t.message)
            }
        })
    }

    fun mostrarToast(mensaje: String) {
        if(toast != null) toast!!.cancel()
        toast = Toast.makeText(this@ComidaActivity, mensaje, Toast.LENGTH_LONG)
        toast!!.show()
    }
}