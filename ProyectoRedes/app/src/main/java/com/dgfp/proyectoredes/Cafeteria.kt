package com.dgfp.proyectoredes

class Cafeteria {
    private var imagen: Int
    private var idCafeteria: String
    private var idSucursal: String
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
        this.idCafeteria = nCafeteria.toString()
        this.idSucursal = nCafeteria.toString()
        this.nombreCafeteria = "Nombre Cafetería" + nCafeteria
        this.nombreSucursal = "Nombre Sucursal" + nCafeteria
        this.edificio = "Edificio" + nCafeteria
        this.local = "Local" + nCafeteria
        this.horario = "Horario" + nCafeteria
    }

    constructor(imagen: Int, idCafeteria: String, idSucursal: String, nombreCafeteria: String, nombreSucursal: String?, edificio: String, local: String, horario: String) {
        nCafeteria++
        this.idCafeteria = idCafeteria
        this.idSucursal = idSucursal
        this.imagen = obtenerImagen(idCafeteria)
        this.nombreCafeteria = nombreCafeteria
        if(nombreSucursal != null) {
            this.nombreSucursal = nombreSucursal
        }
        this.edificio = edificio
        this.local = local
        this.horario = horario
    }

    fun obtenerImagen(idCafeteria: String): Int {
        return when (idCafeteria) {
            "1" -> R.drawable.la_gula
            "2" -> R.drawable.cafe_origenal_de_la_taza
            "3" -> R.drawable.las_exquisitas
            "4" -> R.drawable.el_alamo
            "5" -> R.drawable.yosakura
            "6" -> R.drawable.finca_santa_veracruz
            "7" -> R.drawable.comedor_universitario
            "8" -> R.drawable.comedor_universitario
            "9" -> R.drawable.cerezza
            "10" -> R.drawable.chocos_de_la_puri
            "11" -> R.drawable.nutriletas
            "12" -> R.drawable.klauck
            "13" -> R.drawable.cafeteria_norte
            "14" -> R.drawable.cafeteria_norte
            else -> R.drawable.casa
        }
    }

    fun getImagen(): Int {
        return imagen
    }
    fun setImagen(imagen: Int) {
        this.imagen = imagen
    }

    fun getIDCafeteria(): String {
        return idCafeteria
    }
    fun setIDCafeteria(idCafeteria: String) {
        this.idCafeteria = idCafeteria
    }

    fun getIDSucursal(): String {
        return idSucursal
    }
    fun setIDSucursal(idSucursal: String) {
        this.idSucursal = idSucursal
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

    fun getNCafeteria(): Int {
        return nCafeteria
    }
}