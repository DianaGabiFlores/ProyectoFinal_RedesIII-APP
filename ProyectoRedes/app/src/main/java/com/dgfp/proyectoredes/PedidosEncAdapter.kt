package com.dgfp.proyectoredes

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PedidosEncAdapter : RecyclerView.Adapter<PedidosEncAdapter.PedidosViewHolder> {
    var datos: ArrayList<PedidosEnc>

    var listener: OnItemClickListener? = null
    interface OnItemClickListener {
        fun onItemClick(item: PedidosEnc)
    }

    constructor(datos: ArrayList<PedidosEnc>, listener: OnItemClickListener?) : super() {
        this.datos = datos
        this.listener = listener
    }

    class PedidosViewHolder : RecyclerView.ViewHolder {
        private var toast: Toast? = null
        var imgqr: ImageView
        var txtComida : TextView
        var txtTipoP : TextView
        var txtTiempo : TextView
        var txtFecha : TextView
        var txtPrecio : TextView
        var txtUsuario : TextView
        var txtPagado : TextView
        var btnEntregado: Button

        constructor(itemView: View) : super(itemView) {
            imgqr = itemView.findViewById(R.id.QR)
            txtComida = itemView.findViewById(R.id.Comida)
            txtTipoP = itemView.findViewById(R.id.TipoP)
            txtTiempo = itemView.findViewById(R.id.Tiempo)
            txtFecha = itemView.findViewById(R.id.FechaP)
            txtPrecio = itemView.findViewById(R.id.PrecioP)
            txtUsuario = itemView.findViewById(R.id.orden)
            txtPagado = itemView.findViewById(R.id.pagado)
            btnEntregado = itemView.findViewById(R.id.btnEnt)
        }

        fun bindPedidos(pedido: PedidosEnc, listener: OnItemClickListener?) {
            imgqr.setImageResource(pedido.getImagen())
            txtUsuario.append(pedido.getNombreUsuario())
            txtComida.setText(pedido.getNombreComida())
            if(pedido.getTipoP().equals('T')){
                txtTipoP.append("Tarjeta")
            }else{
                txtTipoP.append("Efectivo")
            }
            if(pedido.getPagado().equals('N')){
                txtPagado.append(" (No pagado)")
            }else if(pedido.getPagado().equals('S')){
                txtPagado.append(" (Pagado)")
            } else{
                txtPagado.append(" (Cancelado)")
            }
            txtTiempo.setText(pedido.getTiempo()+" min")
            txtFecha.setText(pedido.getfechaP())
            txtPrecio.setText("$"+pedido.getPrecio())

            btnEntregado.setOnClickListener{
                cambiarEstado(pedido.getIdOrden(), itemView)
            }

            itemView.setOnClickListener {
                listener!!.onItemClick(pedido)
            }

        }

        private fun cambiarEstado(orden: Int, view:View) {
            var baseURL = "http://10.0.0.10:3000/"
            val retrofit = Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val apiService = retrofit.create(APIService::class.java)
            val ordenP = DCOrden(orden)
            apiService.pedidoEntregado(ordenP).enqueue(object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if (response.isSuccessful) {
                        val resp = response.body()
                        mostrarToast(resp.toString(), view)
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

    constructor(datos: ArrayList<PedidosEnc>) : super() {
        this.datos = datos
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidosViewHolder {
        val item: View = LayoutInflater.from(parent.context).inflate(R.layout.pedidosencargado_layout, parent, false)
        return PedidosViewHolder(item)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: PedidosViewHolder, position: Int) {
        val pedidoI: PedidosEnc = datos[position]
        holder.bindPedidos(pedidoI, listener)

        if(pedidoI.getPagado().equals('N')){
            holder.btnEntregado.visibility = View.VISIBLE
        }else{
            holder.btnEntregado.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return datos.size
    }



}