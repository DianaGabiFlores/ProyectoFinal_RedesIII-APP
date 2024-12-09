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


class PedidosCActivity : AppCompatActivity() {
    private var toast: Toast? = null
    var adaptador: PedidosCAdapter? = null
    var datos: ArrayList<Pedidos> = ArrayList()
    var db: DBSQLite = DBSQLite(this) //Base de Datos
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

        //Verificar que se obtuviron los datos del usuario
        var datosUsuario: Vector<String> = db.obtenerUsuario()
        if(datosUsuario != null) {
            Toast.makeText(this, "Bienvenido "+datosUsuario[1], Toast.LENGTH_LONG).show()
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        //Prueba
        obtenerPedidos(datosUsuario[0])
        /*
        val n = 50
        for (i in 0 until n) {
            datos.add(Cafeteria())
        }
        */
        //Listener
        adaptador = PedidosCAdapter(datos, object : PedidosCAdapter.OnItemClickListener {
            override fun onItemClick(item: Pedidos) {
                Toast.makeText(applicationContext, "Cafetería: " + item.getNombreCaferia(), Toast.LENGTH_SHORT).show()
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
    fun obtenerPedidos(user: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(APIService::class.java)
        val id_usuario = DCUser(user.toInt())
        apiService.getPedidos(id_usuario).enqueue(object : Callback<List<PedidosInfo>> {
            override fun onResponse(call: Call<List<PedidosInfo>>, response: Response<List<PedidosInfo>>) {
                if (response.isSuccessful) {
                    val pedidos = response.body()

                    if (pedidos != null) {
                        datos.clear()
                        for (pedido in pedidos) {
                            val objPedido = Pedidos(
                                R.drawable.ic_launcher_background,
                                pedido.Cafeteria,
                                pedido.Sucursal,
                                pedido.Comida,
                                pedido.Tipo_pago,
                                pedido.Precio.toString(),
                                pedido.Tiempo.toString(),
                                pedido.Pagado,
                                pedido.Fecha
                            )
                            datos.add(objPedido)
                            runOnUiThread {
                                adaptador?.notifyDataSetChanged()
                            }
                        }
                    }
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

    fun cerarActividad() {
        try {
            val activity = this as Activity
            activity.finish()
        }
        catch (e: Exception) {
        }
    }
}