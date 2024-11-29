package com.dgfp.proyectoredes

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ComidaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_comida)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val listview = findViewById<ListView>(R.id.lvComidas)
        val datos: Array<Comida> = arrayOf<Comida>(
            Comida("Hamburguesa", 50.99f, "5 minutos", R.drawable.ic_launcher_background),
            Comida("Quesadilla", 20.50f, "2 minutos", R.drawable.ic_launcher_background),
            Comida("Pescado", 100.99f, "17 minutos", R.drawable.ic_launcher_background),
        )

        val adaptadorDatos: ComidaAdapter = ComidaAdapter(this, datos)
        listview.adapter = adaptadorDatos


        //Listener
        listview.onItemClickListener = AdapterView.OnItemClickListener {
                adapterView: AdapterView<*>, view: View?, i: Int, l: Long ->

            val comida: Comida = adapterView.getItemAtPosition(i) as Comida
            val intent: Intent = Intent(this@ComidaActivity, OrdenarActivity::class.java)
            intent.putExtra("comida", comida)
            startActivity(intent)
        }
    }
}