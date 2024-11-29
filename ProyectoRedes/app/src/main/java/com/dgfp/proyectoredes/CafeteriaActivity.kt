package com.dgfp.proyectoredes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CafeteriaActivity : AppCompatActivity() {
    var adaptador: CafeteriaAdapter? = null
    var datos: ArrayList<Cafeteria> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cafeteria)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        val n = 50
        for (i in 0 until n) {
            datos.add(Cafeteria())
        }

        //Listener //Ayuda aquí Claude, me da error
        adaptador = CafeteriaAdapter(datos, object : CafeteriaAdapter.OnItemClickListener {
            override fun onItemClick(item: Cafeteria) {
                Toast.makeText(applicationContext, "Cafetería: " + item.getNombre(), Toast.LENGTH_SHORT).show()
                val intent = Intent(baseContext, ComidaActivity::class.java)
                intent.putExtra("cafeteria", ""+item.getNombre())
                startActivity(intent)
            }
        })

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        //recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL))

        recyclerView.adapter = adaptador
    }

    fun cerarActividad() {
        try {
            val activity = this as Activity
            activity.finish()
        }
        catch (e: Exception) {
        }
    }
}