package com.dgfp.proyectoredes

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
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
        var btnIniciar: TextView

        constructor(itemView: View) : super(itemView) {
            imgCafeteria = itemView.findViewById(R.id.imgCafeteria)
            txtNombre = itemView.findViewById(R.id.txtNombre)
            txtEdificio = itemView.findViewById(R.id.txtEdificio)
            txtLocal = itemView.findViewById(R.id.txtLocal)
            txtHorario = itemView.findViewById(R.id.txtHorario)
            btnIniciar = itemView.findViewById(R.id.btnIniciar)
        }

        fun bindCafeteria(cafeteria: Cafeteria, listener: OnItemClickListener?) {
            imgCafeteria.setImageResource(cafeteria.getImagen())
            if(cafeteria.getNombreSucursal() != null) {
                txtNombre.setText(cafeteria.getNombreCaferia() + " (" + cafeteria.getNombreSucursal() + ")")
            }
            else txtNombre.setText(cafeteria.getNombreCaferia())
            txtEdificio.setText("Edificio: " + cafeteria.getEdificio())
            txtLocal.setText("Local: " + cafeteria.getLocal())
            txtHorario.setText("Horario: " + cafeteria.getHorario())

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

        //Par
        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.parseColor("#FF4362")) //Fondo Rosa
            holder.txtNombre.setTextColor(Color.BLACK)
            holder.txtEdificio.setTextColor(Color.BLACK)
            holder.txtLocal.setTextColor(Color.BLACK)
            holder.txtHorario.setTextColor(Color.BLACK)
            holder.btnIniciar.setTextColor(Color.BLACK)
        }
        //Impar
        else {
            holder.itemView.setBackgroundColor(Color.parseColor("#5F5F5F")) //Fondo Gris
            holder.txtNombre.setTextColor(Color.WHITE)
            holder.txtEdificio.setTextColor(Color.WHITE)
            holder.txtLocal.setTextColor(Color.WHITE)
            holder.txtHorario.setTextColor(Color.WHITE)
            holder.btnIniciar.setTextColor(Color.WHITE)
        }
    }

    override fun getItemCount(): Int {
        return datos.size
    }
}