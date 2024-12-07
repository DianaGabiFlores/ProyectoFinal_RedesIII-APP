package com.dgfp.proyectoredes

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class OrdenarActivity : AppCompatActivity() {
    private var toast: Toast? = null
    private lateinit var imgComida: ImageView
    private lateinit var txtNombre: TextView
    private lateinit var txtPrecio: TextView
    private lateinit var txtTempoPreparacion: TextView
    private lateinit var txtIngredientes: TextView
    private lateinit var txtListaIngredientes: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ordenar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        imgComida = findViewById(R.id.imgComida)
        txtNombre = findViewById(R.id.txtNombre)
        txtPrecio = findViewById(R.id.txtPrecio)
        txtTempoPreparacion = findViewById(R.id.txtTiempoPreparacion)
        txtIngredientes = findViewById(R.id.txtIngredientes)
        txtListaIngredientes = findViewById(R.id.txtListaIngredientes)

        if (intent.extras != null) {
            val comida: Comida = intent.getSerializableExtra("comida") as Comida

            imgComida.setImageResource(comida.getImagen())
            txtNombre.setText(comida.getNombre())
            txtPrecio.text = "$ " + comida.getPrecio()
            txtTempoPreparacion.setText("Tiempo de Preparación: "+comida.getTiempoPreparacion() + " minutos")

            if(comida.getIngredientes().size == 1) {
                txtIngredientes.text = "Ingrediente:"
            }
            if(comida.getIngredientes().size >= 1) {
                txtIngredientes.text = "Ingredientes:"
            }
            val listaIngredientes = StringBuilder()
            for(ingrediente in comida.getIngredientes()) {
                listaIngredientes.append("\u2022 $ingrediente\n")
            }
            txtListaIngredientes.text = listaIngredientes.toString()
        }
    }

    fun mostrarToast(mensaje: String) {
        if(toast != null) toast!!.cancel()
        toast = Toast.makeText(this@OrdenarActivity, mensaje, Toast.LENGTH_LONG)
        toast!!.show()
    }
}