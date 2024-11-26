package com.dgfp.proyectoredes

class Cafeteria {
    private var imagen: Int
    private var nombre: String
    private var edificio: String
    private var local: String
    private var horario: String
    companion object { //Static en Java
        var nCafeteria = 0
    }


    constructor() {
        nCafeteria++
        this.imagen = R.drawable.ic_launcher_background
        this.nombre = "Nombre" + nCafeteria
        this.edificio = "Edificio" + nCafeteria
        this.local = "Local" + nCafeteria
        this.horario = "Horario" + nCafeteria
    }

    constructor(imagen: Int, nombre: String, edificio: String, local: String, horario: String) {
        this.imagen = imagen
        this.nombre = nombre
        this.edificio = edificio
        this.local = local
        this.horario = horario
    }

    fun getImagen(): Int {
        return imagen
    }
    fun setImagen(imagen: Int) {
        this.imagen = imagen
    }

    fun getNombre(): String {
        return nombre
    }
    fun setNombre(nombre: String) {
        this.nombre = nombre
    }

    fun getEdificio(): String {
        return edificio
    }
    fun setEdificio(edificio: String) {
        this.edificio = edificio
    }

    fun getLocal(): String {
        return local
    }
    fun setLocal(local: String) {
        this.local = local
    }

    fun getHorario(): String {
        return horario
    }
    fun setHorario(horario: String) {
        this.horario = horario
    }
}