package com.dgfp.proyectoredes

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class OrdenarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ordenar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val nombre_prod = findViewById<TextView>(R.id.nombre_producto)
        val precio_prod = findViewById<TextView>(R.id.precio_producto)
        val descripcion_prod = findViewById<TextView>(R.id.detalles_producto)
        val imagen_prod = findViewById<ImageView>(R.id.imagen_producto)

        if (intent.extras != null) {
            val comida: Comida = intent.getSerializableExtra("comida") as Comida

            nombre_prod.setText(comida.getNombre())
            precio_prod.text = "$ " + comida.getPrecio()
            descripcion_prod.setText(comida.getTiempoPreparacion())
            imagen_prod.setImageResource(comida.getImagen())
        }
    }
}