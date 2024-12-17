package com.dgfp.proyectoredes

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PedidosCAdapter : RecyclerView.Adapter<PedidosCAdapter.PedidosViewHolder> {
    var datos: ArrayList<Pedidos>
    private lateinit var baseURL: String
    private var toast: Toast? = null

    var listener: OnItemClickListener? = null
    interface OnItemClickListener {
        fun onItemClick(item: Pedidos)
    }

    constructor(datos: ArrayList<Pedidos>, listener: OnItemClickListener?) : super() {
        this.datos = datos
        this.listener = listener
        baseURL = R.string.baseURL.toString()
    }

    class PedidosViewHolder : RecyclerView.ViewHolder {
        private var toast: Toast? = null
        private val context: Context? = null

        var imgqr: ImageView
        var txtComida : TextView
        var txtCafeteria : TextView
        var txtSucursal : TextView
        var txtTipoP : TextView
        var txtTiempo : TextView
        var txtFecha : TextView
        var txtPrecio : TextView
        var btnCancelado: Button

        var txtPedido : TextView

        constructor(itemView: View) : super(itemView) {
            imgqr = itemView.findViewById(R.id.QR)
            txtComida = itemView.findViewById(R.id.Comida)
            txtCafeteria = itemView.findViewById(R.id.Cafeteria)
            txtSucursal = itemView.findViewById(R.id.Edificio)
            txtTipoP = itemView.findViewById(R.id.TipoP)
            txtTiempo = itemView.findViewById(R.id.Tiempo)
            txtFecha = itemView.findViewById(R.id.FechaP)
            txtPrecio = itemView.findViewById(R.id.PrecioP)
            btnCancelado = itemView.findViewById(R.id.btnEnt)
            txtPedido = itemView.findViewById(R.id.orden)
        }

        fun bindPedidos(pedido: Pedidos, listener: OnItemClickListener?) {
            imgqr.setImageResource(pedido.getImagen())
            txtComida.setText(pedido.getNombreComida())
            txtSucursal.append(pedido.getNombreSucursal())
            txtCafeteria.append(pedido.getNombreCaferia())
            if(pedido.getTipoP().equals('T')){
                txtTipoP.append("Tarjeta")
            }else{
                txtTipoP.append("Efectivo")
            }

            if(pedido.getPagado().equals('N')){
                txtPedido.append(" (No pagado)")
            }else if(pedido.getPagado().equals('S')){
                txtPedido.append(" (Pagado)")
            } else{
                txtPedido.append(" (Cancelado)")
            }

            txtTiempo.setText(pedido.getTiempo()+" min")
            txtFecha.setText(pedido.getfechaP())
            txtPrecio.setText("$"+pedido.getPrecio())

            itemView.setOnClickListener {
                listener!!.onItemClick(pedido)
            }

            btnCancelado.setOnClickListener{
                cambiarEstado(pedido.getIdOrden(), itemView)
            }
        }
        private fun cambiarEstado(orden: Int, view: View) {
            var baseURL = "http://10.0.0.10:3000/"
//            mostrarToast(baseURL, view)
            val retrofit = Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val apiService = retrofit.create(APIService::class.java)
            val ordenP = DCOrden(orden)
            apiService.pedidoCancelado(ordenP).enqueue(object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if (response.isSuccessful) {
                        val resp = response.body()
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        }
        fun mostrarToast(mensaje: String, view: View) {
            if(toast != null) toast!!.cancel()
            toast = Toast.makeText(view.getContext() , mensaje, Toast.LENGTH_LONG)
            toast!!.show()
        }

    }

    constructor(datos: ArrayList<Pedidos>) : super() {
        this.datos = datos
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidosViewHolder {
        val item: View = LayoutInflater.from(parent.context).inflate(R.layout.pedidos_layout, parent, false)
        return PedidosViewHolder(item)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: PedidosViewHolder, position: Int) {
        val pedidoI: Pedidos = datos[position]
        holder.bindPedidos(pedidoI, listener)

        if(pedidoI.getPagado().equals('N')){
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF")) //Fondo Blanco
            holder.btnCancelado.visibility = View.VISIBLE
        }else if(pedidoI.getPagado().equals('C')){
            holder.itemView.setBackgroundColor(Color.parseColor("#a4d6d4")) //Fondo verde bajito
            holder.btnCancelado.visibility = View.GONE
            holder.txtPedido.setTextColor(Color.WHITE)
        }else{
            holder.itemView.setBackgroundColor(Color.parseColor("#82abaa")) //Fondo verde gris
            holder.txtPedido.setTextColor(Color.WHITE)
            holder.txtComida.setTextColor(Color.WHITE)
            holder.txtComida.setTextColor(Color.WHITE)
            holder.txtCafeteria.setTextColor(Color.WHITE)
            holder.txtSucursal.setTextColor(Color.WHITE)
            holder.txtTipoP.setTextColor(Color.WHITE)
            holder.txtTiempo.setTextColor(Color.WHITE)
            holder.txtFecha.setTextColor(Color.WHITE)
            holder.txtPrecio.setTextColor(Color.WHITE)
            holder.btnCancelado.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return datos.size
    }
}