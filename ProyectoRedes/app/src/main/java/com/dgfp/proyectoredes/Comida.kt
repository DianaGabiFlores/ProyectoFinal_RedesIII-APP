package com.dgfp.proyectoredes

import java.io.Serializable

class Comida : Serializable {
    private var idCafeteria: String
    private var idSucursal: String
    private var idComida: String
    private var nombre: String
    private var precio: Float
    private var tiempoPreparacion: String
    private var ingredientes: List<String>
    private var imagen: Int

    constructor(idCafeteria: String, idSucursal: String, idComida: String, nombre: String, precio: Float, tiempoPreparacion: String, ingredientes: List<String>, imagen: Int) {
        this.idCafeteria = idCafeteria
        this.idSucursal = idSucursal
        this.idComida = idComida
        this.nombre = nombre
        this.precio = precio
        this.tiempoPreparacion = tiempoPreparacion
        this.ingredientes = ingredientes
        this.imagen = obtenerImagen(idComida)
    }

    fun obtenerImagen(idComida: String): Int {
        return when (idComida) {
            "1" -> R.drawable.comida_guajolotas
            "2" -> R.drawable.comida_chilaquiles
            "3" -> R.drawable.comida_hamburguesa
            "4" -> R.drawable.comida_ensalada
            "5" -> R.drawable.comida_quesadillas
            "6" -> R.drawable.comida_especial_de_desayuno
            "7" -> R.drawable.comida_enchiladas_verdes
            "8" -> R.drawable.comida_molletes
            "9" -> R.drawable.comida_charola
            "10" -> R.drawable.comida_ensalada1
            "11" -> R.drawable.comida_sandwich
            "12" -> R.drawable.comida_tortajamon
            "13" -> R.drawable.comida_hogdog
            "14" -> R.drawable.comida_salchipulpus
            "15" -> R.drawable.comida_americano
            "16" -> R.drawable.comida_smango
            "17" -> R.drawable.comida_chai
            "18" -> R.drawable.comida_chococ
            "19" -> R.drawable.comida_chocov
            "20" -> R.drawable.comida_bionico
            "21" -> R.drawable.comida_taco
            "22" -> R.drawable.comida_chilaquilesverdes
            "23" -> R.drawable.comida_atun
            "24" -> R.drawable.comida_milanesa
            "25" -> R.drawable.comida_nigiri_camaron
            "26" -> R.drawable.comida_dedosdequeso
            "27" -> R.drawable.comida_papas
            "28" -> R.drawable.comida_limonada
            "29" -> R.drawable.comida_nigiri_pulpo
            else -> R.drawable.plato
        }
    }

    fun getIdCafeteria(): String {
        return idCafeteria
    }
    fun setIdCafeteria(idCafeteria: String) {
        this.idCafeteria = idCafeteria
    }

    fun getIdSucursal(): String {
        return idSucursal
    }
    fun setIdSucursal(idSucursal: String) {
        this.idSucursal = idSucursal
    }

    fun getIdComida(): String {
        return idComida
    }
    fun setIdComida(idComida: String) {
        this.idComida = idComida
    }

    fun getNombre(): String {
        return nombre
    }
    fun setNombre(nombre: String) {
        this.nombre = nombre
    }

    fun getPrecio(): Float {
        return precio
    }
    fun setPrecio(precio: Float) {
        this.precio = precio
    }

    fun getTiempoPreparacion(): String {
        return tiempoPreparacion
    }
    fun setTiempoPreparacion(tiempoPreparacion: String) {
        this.tiempoPreparacion = tiempoPreparacion
    }

    fun getIngredientes(): List<String> {
        return ingredientes
    }
    fun setIngredientes(ingredientes: List<String>) {
        this.ingredientes = ingredientes
    }

    fun getImagen(): Int {
        return imagen
    }
    fun setImagen(imagen: Int) {
        this.imagen = imagen
    }
}