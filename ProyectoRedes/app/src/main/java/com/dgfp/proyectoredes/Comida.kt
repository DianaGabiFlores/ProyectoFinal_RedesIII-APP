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
        this.imagen = imagen
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