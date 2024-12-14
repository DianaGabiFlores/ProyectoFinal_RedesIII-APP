package com.dgfp.proyectoredes

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Admin_CambiarUsu : AppCompatActivity() {
    private var toast: Toast? = null
    private lateinit var btnCambiarUsu: Button
    private lateinit var txtNombre: EditText
    private lateinit var spinnerUsu: Spinner
    private lateinit var spinnerTipo: Spinner
    var db: DBSQLite = DBSQLite(this) //Base de Datos
    private lateinit var baseURL: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.admin_agregarusuario)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        val TipoUsu = ArrayAdapter.createFromResource(this, R.array.tipoCliente, android.R.layout.simple_spinner_item)
        TipoUsu.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinnerTipo.setAdapter(TipoUsu)

        val Usuarios = ArrayAdapter.createFromResource(this, R.array.tipoCliente, android.R.layout.simple_spinner_item)
        Usuarios.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinnerUsu.setAdapter(Usuarios)

        baseURL = baseContext.getString(R.string.baseURL)
        btnCambiarUsu = findViewById(R.id.btnCambiar)
        txtNombre = findViewById(R.id.nombre)

        //Listener
        btnCambiarUsu.setOnClickListener(evento)
        spinnerUsu.setOnItemSelectedListener(evento1)
        spinnerTipo.setOnItemSelectedListener(evento2)
    }


    private val evento1: AdapterView.OnItemSelectedListener =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {

            }
            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }
        }
    private val evento2: AdapterView.OnItemSelectedListener =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {

            }
            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }
        }

    private val evento = View.OnClickListener { v ->
        if(v == btnCambiarUsu) {

        }
    }

    fun mostrarToast(mensaje: String) {
        if(toast != null) toast!!.cancel()
        toast = Toast.makeText(this@Admin_CambiarUsu, mensaje, Toast.LENGTH_LONG)
        toast!!.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        db.borrarUsuario()
    }
}