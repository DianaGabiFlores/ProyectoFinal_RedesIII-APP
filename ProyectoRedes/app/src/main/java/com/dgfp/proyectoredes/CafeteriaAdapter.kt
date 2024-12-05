package com.dgfp.proyectoredes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CafeteriaAdapter : RecyclerView.Adapter<CafeteriaAdapter.CafeteriaViewHolder> {
    var datos: ArrayList<Cafeteria>

    var listener: OnItemClickListener? = null
    interface OnItemClickListener {
        fun onItemClick(item: Cafeteria)
    }

    constructor(datos: ArrayList<Cafeteria>, listener: OnItemClickListener?) : super() {
        this.datos = datos
        this.listener = listener
    }

    class CafeteriaViewHolder : RecyclerView.ViewHolder {
        var imgCafeteria: ImageView
        var txtNombre: TextView
        var txtEdificio: TextView
        var txtLocal: TextView
        var txtHorario: TextView

        constructor(itemView: View) : super(itemView) {
            imgCafeteria = itemView.findViewById(R.id.imgCafeteria)
            txtNombre = itemView.findViewById(R.id.txtNombre)
            txtEdificio = itemView.findViewById(R.id.txtEdificio)
            txtLocal = itemView.findViewById(R.id.txtLocal)
            txtHorario = itemView.findViewById(R.id.txtHorario)
        }

        fun bindCafeteria(cafeteria: Cafeteria, listener: OnItemClickListener?) {
            imgCafeteria.setImageResource(cafeteria.getImagen())
            txtNombre.setText(cafeteria.getNombreCaferia())
            txtEdificio.setText(cafeteria.getEdificio())
            txtLocal.setText(cafeteria.getLocal())
            txtHorario.setText(cafeteria.getHorario())

            itemView.setOnClickListener {
                listener!!.onItemClick(cafeteria)
            }
        }
    }

    constructor(datos: ArrayList<Cafeteria>) : super() {
        this.datos = datos
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CafeteriaViewHolder {
        val item: View = LayoutInflater.from(parent.context).inflate(R.layout.item_cafeteria, parent, false)
        return CafeteriaViewHolder(item)
    }

    override fun onBindViewHolder(holder: CafeteriaViewHolder, position: Int) {
        val contacto: Cafeteria = datos[position]
        holder.bindCafeteria(contacto, listener)
    }

    override fun getItemCount(): Int {
        return datos.size
    }
}