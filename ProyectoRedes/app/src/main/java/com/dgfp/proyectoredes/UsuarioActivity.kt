package com.dgfp.proyectoredes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Vector

class UsuarioActivity: AppCompatActivity() {

    var Nombre: TextView? = null
    var ApellidoP: TextView? = null
    var ApellidoM: TextView? = null
    var Email: TextView? = null
    var Telefono: TextView? = null
    var Tipo: TextView? = null

    var db: DBSQLite = DBSQLite(this) //Base de Datos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.usuario)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        Nombre = findViewById(R.id.name)
        Email = findViewById(R.id.email)
        Telefono = findViewById(R.id.telefono)
        Tipo = findViewById(R.id.tipoUser)
//        ApellidoP = findViewById(R.id.btnLogin)
//        ApellidoM = findViewById(R.id.btnLogin)
//
        var datosUsuario: Vector<String> = db.obtenerUsuario()
        if(datosUsuario != null) {
            Toast.makeText(this, "Bienvenido "+datosUsuario[1], Toast.LENGTH_LONG).show()
            Nombre?.setText(datosUsuario[1])
            Email?.setText(datosUsuario[6])
            Telefono?.setText(datosUsuario[5])
            Tipo?.append(datosUsuario[7])

        }


    }

}

