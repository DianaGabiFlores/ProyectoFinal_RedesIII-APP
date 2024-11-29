package com.dgfp.proyectoredes

import java.io.Serializable

class Comida : Serializable {
    private var nombre: String
    private var precio: Float
    private var tiempoPreparacion: String
    private var imagen: Int

    constructor(nombre: String, precio: Float, tiempoPreparacion: String, imagen: Int) {
        this.nombre = nombre
        this.precio = precio
        this.tiempoPreparacion = tiempoPreparacion
        this.imagen = imagen
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

    fun getImagen(): Int {
        return imagen
    }
    fun setImagen(imagen: Int) {
        this.imagen = imagen
    }
}