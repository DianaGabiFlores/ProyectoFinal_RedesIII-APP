package com.dgfp.proyectoredes

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PedidosCAdapter : RecyclerView.Adapter<PedidosCAdapter.PedidosViewHolder> {
    var datos: ArrayList<Pedidos>

    var listener: OnItemClickListener? = null
    interface OnItemClickListener {
        fun onItemClick(item: Pedidos)
    }

    constructor(datos: ArrayList<Pedidos>, listener: OnItemClickListener?) : super() {
        this.datos = datos
        this.listener = listener
    }

    class PedidosViewHolder : RecyclerView.ViewHolder {

        var imgqr: ImageView
        var txtComida : TextView
        var txtCafeteria : TextView
        var txtSucursal : TextView
        var txtTipoP : TextView
        var txtTiempo : TextView
        var txtFecha : TextView
        var txtPrecio : TextView

        constructor(itemView: View) : super(itemView) {
            imgqr = itemView.findViewById(R.id.QR)
            txtComida = itemView.findViewById(R.id.Comida)
            txtCafeteria = itemView.findViewById(R.id.Cafeteria)
            txtSucursal = itemView.findViewById(R.id.Edificio)
            txtTipoP = itemView.findViewById(R.id.TipoP)
            txtTiempo = itemView.findViewById(R.id.Tiempo)
            txtFecha = itemView.findViewById(R.id.FechaP)
            txtPrecio = itemView.findViewById(R.id.PrecioP)
        }

        fun bindPedidos(pedido: Pedidos, listener: OnItemClickListener?) {
            imgqr.setImageResource( R.drawable.ic_launcher_background)
            txtComida.setText(pedido.getNombreComida())
            txtSucursal.append(pedido.getNombreSucursal())
            txtCafeteria.append(pedido.getNombreCaferia())
            if(pedido.getTipoP().equals('T')){
                txtTipoP.append("Tarjeta")
            }else{
                txtTipoP.append("Efectivo")
            }
            txtTiempo.setText(pedido.getTiempo()+" min")
            txtFecha.setText(pedido.getfechaP())
            txtPrecio.setText("$"+pedido.getPrecio())

            itemView.setOnClickListener {
                listener!!.onItemClick(pedido)
            }
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
            holder.itemView.setBackgroundColor(Color.parseColor("#FF4362")) //Fondo Rosa
        }else{
            holder.itemView.setBackgroundColor(Color.parseColor("#5F5F5F")) //Fondo Gris
            holder.txtComida.setTextColor(Color.WHITE)
            holder.txtComida.setTextColor(Color.WHITE)
            holder.txtCafeteria.setTextColor(Color.WHITE)
            holder.txtSucursal.setTextColor(Color.WHITE)
            holder.txtTipoP.setTextColor(Color.WHITE)
            holder.txtTiempo.setTextColor(Color.WHITE)
            holder.txtFecha.setTextColor(Color.WHITE)
            holder.txtPrecio.setTextColor(Color.WHITE)

        }
    }

    override fun getItemCount(): Int {
        return datos.size
    }
}