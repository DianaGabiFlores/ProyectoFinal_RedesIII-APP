package com.dgfp.proyectoredes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PedidosCAdapter (private val pedidos: List<PedidosInfo> ):
    RecyclerView.Adapter<PedidosCAdapter.PedidosViewHolder>() {

    var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(item: PedidosInfo)
    }

    class PedidosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindPedidos(pedido: PedidosInfo, listener: OnItemClickListener?) {
            val imgqr: ImageView = itemView.findViewById(R.id.QR)
            val txtComida : TextView = itemView.findViewById(R.id.Comida)
            val txtCafeteria : TextView = itemView.findViewById(R.id.Cafeteria)
            val txtSucursal : TextView = itemView.findViewById(R.id.Edificio)
            val txtTipoP : TextView = itemView.findViewById(R.id.TipoP)
            val txtTiempo : TextView = itemView.findViewById(R.id.Tiempo)
            val txtFecha : TextView = itemView.findViewById(R.id.FechaP)
            val txtPrecio : TextView = itemView.findViewById(R.id.PrecioP)

            imgqr.setImageResource( R.drawable.ic_launcher_background)
            txtComida.setText(pedido.Comida)
            txtSucursal.append(pedido.Sucursal)
            txtCafeteria.append(pedido.Cafeteria)
            if(pedido.Tipo_pago == 'T'){
                txtTipoP.append("Tarjeta")
            }else{
                txtTipoP.append("Efectivo")
            }
            txtTiempo.setText(pedido.Tiempo)
            txtFecha.setText(pedido.Fecha.toString())
            txtPrecio.setText(pedido.Precio.toString())

            itemView.setOnClickListener {
                listener!!.onItemClick(pedido)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidosViewHolder {
        val item: View = LayoutInflater.from(parent.context).inflate(R.layout.pedidos_layout, parent, false)
        return PedidosViewHolder(item)
    }

    override fun onBindViewHolder(holder: PedidosViewHolder, position: Int) {
        val contacto: PedidosInfo = pedidos[position]
        holder.bindPedidos(contacto, listener)
    }

    override fun getItemCount(): Int {
        return pedidos.size
    }
}