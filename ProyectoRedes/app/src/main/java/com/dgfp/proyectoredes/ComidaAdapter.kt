package com.dgfp.proyectoredes

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView


class ComidaAdapter internal constructor(var context: Activity, datos: Array<Comida>) :
    ArrayAdapter<Any?>(context, R.layout.comida_layout, datos) {
    var datos: Array<Comida> = datos

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var item = convertView
        if (item == null) {
            val inflater = context.layoutInflater
            item = inflater.inflate(R.layout.comida_layout, null)
        }
        //Nombre
        val txtNombre = item!!.findViewById<TextView>(R.id.nombre)
        txtNombre.setText(datos[position].getNombre())
        //Precio
        val lblSubtitulo = item.findViewById<TextView>(R.id.precio)
        lblSubtitulo.setText(datos[position].getPrecio().toString())
        //Imagen
        val imagenProducto = item.findViewById<ImageView>(R.id.imagen)
        imagenProducto.setImageResource(datos[position].getImagen())

        return (item)
    }
}