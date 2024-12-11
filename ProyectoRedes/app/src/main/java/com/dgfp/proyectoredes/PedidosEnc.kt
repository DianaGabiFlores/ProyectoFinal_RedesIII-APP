package com.dgfp.proyectoredes

class PedidosEnc {
    private var imagen: Int
    private var idOrden: Int
    private var nombreUsuario: String
    private var nombreComida: String
    private var tipoP: Char
    private var pagado: Char
    private var precio: String
    private var tiempo: String
    private var fechaP: String
    companion object { //Static en Java
        var nPedidos = 0
    }

    constructor(imagen: Int, idOrden: Int, nombreUsuario: String, nombreComida: String, tipoP: Char, precio: String, tiempo: String, pagado: Char, fechaP: String) {
        nPedidos++
        this.imagen = imagen
        this.idOrden = idOrden
        this.nombreUsuario = nombreUsuario
        this.nombreComida = nombreComida
        this.tipoP = tipoP
        this.precio = precio
        this.tiempo = tiempo
        this.fechaP = fechaP
        this.pagado = pagado
    }

    fun getImagen(): Int {
        return imagen
    }
    fun setImagen(imagen: Int) {
        this.imagen = imagen
    }
    fun getIdOrden(): Int {
        return idOrden
    }
    fun setIdOrden(idOrden: Int) {
        this.idOrden = idOrden
    }

    fun getNombreUsuario(): String {
        return nombreUsuario
    }
    fun setNombreUsuario(nombreUsuario: String) {
        this.nombreUsuario = nombreUsuario
    }

    fun getNombreComida(): String {
        return nombreComida
    }
    fun setNombreComida(nombreComida: String) {
        this.nombreComida = nombreComida
    }

    fun getTipoP(): Char {
        return tipoP
    }
    fun setTipoP(tipoP: Char) {
        this.tipoP = tipoP
    }

    fun getPrecio(): String {
        return precio
    }
    fun setPrecio(precio: String) {
        this.precio = precio
    }

    fun getTiempo(): String {
        return tiempo
    }
    fun settiempo(tiempo: String) {
        this.tiempo = tiempo
    }

    fun getfechaP(): String {
        return fechaP
    }
    fun setfechaP(fechaP: String) {
        this.fechaP = fechaP
    }

    fun getNCafeteria(): Int {
        return nPedidos
    }

    fun setPagado(pagado: Char) {
        this.pagado = pagado
    }

    fun getPagado(): Char {
        return pagado
    }
}