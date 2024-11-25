package com.dgfp.proyectoredes

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.dgfp.practica14kt_controlp3.OnSelectedTexto

class Login: LinearLayout {
    var btnLogin: ImageButton? = null
    var btnCrearCuenta: ImageButton? = null
    var Usuario: TextView? = null
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
        li.inflate(R.layout.activity_main,this,true)
        btnLogin = findViewById(R.id.btnLogin)
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta)
        Usuario = findViewById(R.id.txtUsuario)
        Contrasena = findViewById(R.id.txtContrasena)

        asignarEventos()
    }

    fun asignarEventos(){
        btnLogin!!.setOnClickListener(evento)
        btnCrearCuenta!!.setOnClickListener(evento)
    }

    private val evento = OnClickListener { v ->
        if(v == btnLogin){
            if(Usuario?.getText().toString().isNotEmpty() && Contrasena?.getText().toString().isNotEmpty()){


            }

        } else if(v == btnCrearCuenta){

        }
    }
}