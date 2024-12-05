package com.dgfp.proyectoredes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
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

class CafeteriaActivity : AppCompatActivity() {
    private var toast: Toast? = null
    var adaptador: CafeteriaAdapter? = null
    var datos: ArrayList<Cafeteria> = ArrayList()
    var db: DBSQLite = DBSQLite(this) //Base de Datos
    private var baseURL = "http://192.168.1.163:3000/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cafeteria)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Verificar que se obtuviron los datos del usuario
        var datosUsuario: Vector<String> = db.obtenerUsuario()
        if(datosUsuario != null) {
           Toast.makeText(this, "Bienvenido "+datosUsuario[1], Toast.LENGTH_LONG).show()
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        obtenerCafeterias()
        /*
        val n = 50
        for (i in 0 until n) {
            datos.add(Cafeteria())
        }
        */

        //Listener
        adaptador = CafeteriaAdapter(datos, object : CafeteriaAdapter.OnItemClickListener {
            override fun onItemClick(item: Cafeteria) {
                Toast.makeText(applicationContext, "Cafetería: " + item.getNombreCaferia(), Toast.LENGTH_SHORT).show()
                val intent = Intent(baseContext, ComidaActivity::class.java)
                intent.putExtra("cafeteria", ""+item.getNombreCaferia())
                startActivity(intent)
            }
        })

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        //recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL))

        recyclerView.adapter = adaptador
    }

    //Obtener todas las cafeterías
    fun obtenerCafeterias() {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(APIServiceCafeteria::class.java)

        apiService.getCafeterias().enqueue(object : Callback<List<DCCafeteria>> {
            override fun onResponse(call: Call<List<DCCafeteria>>, response: Response<List<DCCafeteria>>) {
                if(response.isSuccessful) {
                    val cafeterias = response.body()
                    if(cafeterias != null) {
                        datos.clear()

                        for(cafeteria in cafeterias) {
                            for(sucursal in cafeteria.Sucursales) {
                                datos.add(
                                    Cafeteria(
                                        R.drawable.ic_launcher_background,
                                        cafeteria.Nombre,
                                        sucursal.Nombre,
                                        sucursal.Edificio,
                                        sucursal.Numero_Local,
                                        sucursal.Horario
                                    )
                                )
                            }
                            runOnUiThread {
                                adaptador?.notifyDataSetChanged()
                            }
                        }
                    }
                }
            }
            override fun onFailure(call: Call<List<DCCafeteria>>, t: Throwable) {
                //Manejo de error
                mostrarToast("Error de conexión: " + t.message)
            }
        })
    }

    fun mostrarToast(mensaje: String) {
        if(toast != null) toast!!.cancel()
        toast = Toast.makeText(this@CafeteriaActivity, mensaje, Toast.LENGTH_LONG)
        toast!!.show()
    }

    fun cerarActividad() {
        try {
            val activity = this as Activity
            activity.finish()
        }
        catch (e: Exception) {
        }
    }
}