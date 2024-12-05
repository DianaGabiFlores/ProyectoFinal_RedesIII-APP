package com.dgfp.proyectoredes

class Cafeteria {
    private var imagen: Int
    private var nombreCafeteria: String
    private var nombreSucursal: String? = null //Norte, Sur, Central
    private var horario: String
    private var local: String
    private var edificio: String
    companion object { //Static en Java
        var nCafeteria = 0
    }


    constructor() {
        nCafeteria++
        this.imagen = R.drawable.ic_launcher_background
        this.nombreCafeteria = "Nombre Cafetería" + nCafeteria
        this.nombreSucursal = "Nombre Sucursal" + nCafeteria
        this.edificio = "Edificio" + nCafeteria
        this.local = "Local" + nCafeteria
        this.horario = "Horario" + nCafeteria
    }

    constructor(imagen: Int, nombreCafeteria: String, nombreSucursal: String?, edificio: String, local: String, horario: String) {
        this.imagen = imagen
        this.nombreCafeteria = nombreCafeteria
        if(nombreSucursal != null) {
            this.nombreSucursal = nombreSucursal
        }
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

    fun getNombreCaferia(): String {
        return nombreCafeteria
    }
    fun setNombreCafeteria(nombreCafeteria: String) {
        this.nombreCafeteria = nombreCafeteria
    }

    fun getNombreSucursal(): String? {
        return nombreSucursal
    }
    fun setNombreSucursal(nombreSucursal: String) {
        this.nombreSucursal = nombreSucursal
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