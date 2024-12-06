package com.dgfp.proyectoredes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
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


class PedidosCActivity : AppCompatActivity() {
    var adaptador: PedidosCAdapter? = null
    var datos: ArrayList<PedidosInfo> = ArrayList()
    var db: DBSQLite = DBSQLite(this) //Base de Datos
    private var toast: Toast? = null
    private var baseURL = "http://192.168.100.53:3000/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pedidos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var datosUsuario: Vector<String> = db.obtenerUsuario()
        if(datosUsuario != null) {
            Toast.makeText(this, "Bienvenido "+datosUsuario[1], Toast.LENGTH_LONG).show()
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        //Obtener todos los pedidos del usuario
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(APIServiceUsuario::class.java)
        val id_usuario = DCUser(1)
        apiService.getPedidos(id_usuario).enqueue(object : Callback<List<PedidosInfo>> {
            override fun onResponse(call: Call<List<PedidosInfo>>, response: Response<List<PedidosInfo>>) {
                if(response.isSuccessful) {
                    mostrarToast("Pedidos extraidos.")
                    val UserPedidos = response.body()
                    if(UserPedidos != null) {
//                        for(pedido in UserPedidos){
//                            datos.add(pedido)
//                        }
                        recyclerView.adapter = PedidosCAdapter(UserPedidos)
                    }
                } else {
                    mostrarToast("Error al registrar el usuario: " + response.code())
                }
            }

            override fun onFailure(call: Call<List<PedidosInfo>>, t: Throwable) {
                mostrarToast("Error de conexión: " + t.message)
            }

        })

        //Listener
//        adaptador = PedidosCAdapter(datos, object : PedidosCAdapter.OnItemClickListener {
//            override fun onItemClick(item: PedidosInfo) {
//                Toast.makeText(applicationContext, "Orden: " + item.Orden, Toast.LENGTH_SHORT).show()
////                val intent = Intent(baseContext, ComidaActivity::class.java)
////                intent.putExtra("cafeteria", ""+item.getNombre())
////                startActivity(intent)
//            }
//        })

    }

    fun cerarActividad() {
        try {
            val activity = this as Activity
            activity.finish()
        }
        catch (e: Exception) {
        }
    }

    fun getPedidos(user: String) {
        //Obtener todos los usuarios
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(APIServiceUsuario::class.java)
        val id_usuario = DCUser(1)
        apiService.getPedidos(id_usuario).enqueue(object : Callback<List<PedidosInfo>> {
            override fun onResponse(call: Call<List<PedidosInfo>>, response: Response<List<PedidosInfo>>) {
                if(response.isSuccessful) {
//                    mostrarToast("Pedidos extraidos.")
                    val UserPedidos = response.body()
                    if(UserPedidos != null) {
                        for(pedido in UserPedidos){
                            datos.add(pedido)
                        }
                    }
                } else {
                    mostrarToast("Error al registrar el usuario: " + response.code())
                }
            }

            override fun onFailure(call: Call<List<PedidosInfo>>, t: Throwable) {
                mostrarToast("Error de conexión: " + t.message)
            }

        })
    }

    fun mostrarToast(mensaje: String) {
        if(toast != null) toast!!.cancel()
        toast = Toast.makeText(this@PedidosCActivity, mensaje, Toast.LENGTH_LONG)
        toast!!.show()
    }

}
