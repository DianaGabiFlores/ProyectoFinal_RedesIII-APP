package com.dgfp.proyectoredes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
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


class PedidosCActivity : AppCompatActivity() {
    private var toast: Toast? = null
    var adaptador: PedidosCAdapter? = null
    var adaptador1: PedidosEncAdapter? = null
    var datos: ArrayList<Pedidos> = ArrayList()
    var datos1: ArrayList<PedidosEnc> = ArrayList()
    var db: DBSQLite = DBSQLite(this) //Base de Datos
    private lateinit var baseURL: String

    private var TxtN: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pedidos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        baseURL = baseContext.getString(R.string.baseURL)

        //Verificar que se obtuviron los datos del usuario
        var datosUsuario: Vector<String> = db.obtenerUsuario()
        if(datosUsuario != null) {
            Toast.makeText(this, "Bienvenido "+datosUsuario[1], Toast.LENGTH_LONG).show()
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        TxtN = findViewById(R.id.NoH)

        // Comprobar si es encargado o cliente
        if(datosUsuario[7] == "Encargado"){
            obtenerPedidosEng(datosUsuario[0])

            //Listener
            adaptador1 = PedidosEncAdapter(datos1, object : PedidosEncAdapter.OnItemClickListener {
                override fun onItemClick(item: PedidosEnc) {
                    obtenerPedidosEng(datosUsuario[0])
                    Toast.makeText(applicationContext, "Cliente: " + item.getNombreUsuario(), Toast.LENGTH_SHORT).show()
                }
            })

            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            //recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

            recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
            recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL))

            recyclerView.adapter = adaptador1

        }else {
            obtenerPedidos(datosUsuario[0])

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


    }

    //Obtener todas las cafeterías
    fun obtenerPedidos(user: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(APIService::class.java)
        val id_usuario = DCUser(user)
        apiService.getPedidos(id_usuario).enqueue(object : Callback<List<PedidosInfo>> {
            override fun onResponse(call: Call<List<PedidosInfo>>, response: Response<List<PedidosInfo>>) {
                if (response.isSuccessful) {
                    val pedidos = response.body()
                    var num = 0
                    if (pedidos != null) {
                        if(pedidos is List<PedidosInfo>){
                            datos.clear()
                            for (pedido in pedidos) {
                                num ++
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
                        if(num == 0 ){
                            TxtN?.visibility = View.VISIBLE
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<PedidosInfo>>, t: Throwable) {
                mostrarToast("Error de conexión: " + t.message)
            }
        })
    }


    //Obtener todas los pedidos del
    fun obtenerPedidosEng(user: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(APIService::class.java)
        val id_usuario = DCUser(user)
        apiService.getPedidosEnc(id_usuario).enqueue(object : Callback<List<DCPedidosEnc>> {
            override fun onResponse(call: Call<List<DCPedidosEnc>>, response: Response<List<DCPedidosEnc>>) {
                if (response.isSuccessful) {
                    val pedidos = response.body()
                    var num = 0
                    if (pedidos != null) {
                        if(pedidos is List<DCPedidosEnc>){
                            datos1.clear()
                            for (pedido in pedidos) {
                                num ++
                                val objPedido = PedidosEnc(
                                    R.drawable.ic_launcher_background,
                                    pedido.Orden,
                                    pedido.NombreUsuario,
                                    pedido.Comida,
                                    pedido.Tipo_pago,
                                    pedido.Precio.toString(),
                                    pedido.Tiempo.toString(),
                                    pedido.Pagado,
                                    pedido.Fecha
                                )
                                datos1.add(objPedido)
                                runOnUiThread {
                                    adaptador1?.notifyDataSetChanged()
                                }
                            }
                        }
                        if(num == 0 ){
                            TxtN?.visibility = View.VISIBLE
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<DCPedidosEnc>>, t: Throwable) {
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