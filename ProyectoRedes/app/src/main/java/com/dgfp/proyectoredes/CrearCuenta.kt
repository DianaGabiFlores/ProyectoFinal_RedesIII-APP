package com.dgfp.proyectoredes

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.dgfp.practica14kt_controlp3.OnSelectedTexto

class CrearCuenta: LinearLayout {
    var btnCrearCuenta: ImageButton? = null
    var Nombre: TextView? = null
    var Apellidos: TextView? = null
    var Email: TextView? = null
    var Telefono: TextView? = null
    var Contrasena: TextView? = null

    private var listener: OnSelectedTexto? = null
    fun setOnDateChangeListener(l: OnSelectedTexto){
        listener = l
    }

    constructor(context: Context? ) : super(context){
        inicializar()
    }

    constructor(context: Context?, attrs: AttributeSet? ) : super(context, attrs){
        inicializar()
    }

    fun inicializar(){
        val li = LayoutInflater.from(context)
        li.inflate(R.layout.crear_cuenta,this,true)
        btnCrearCuenta = findViewById(R.id.btnSingIn)
        Nombre = findViewById(R.id.txtUsuario)
        Apellidos = findViewById(R.id.txtApellidos)
        Email = findViewById(R.id.txtEmail)
        Telefono = findViewById(R.id.txtTelefono)
        Contrasena = findViewById(R.id.txtContrasena)

        btnCrearCuenta!!.setOnClickListener(evento)
    }

    private val evento = OnClickListener { v ->
        if(v == btnCrearCuenta){
            if(Nombre?.getText().toString().isNotEmpty() && Apellidos?.getText().toString().isNotEmpty() &&
                Email?.getText().toString().isNotEmpty() && Telefono?.getText().toString().isNotEmpty() && Contrasena?.getText().toString().isNotEmpty()){

            }

        } else if(v == btnCrearCuenta){

        }
    }
}