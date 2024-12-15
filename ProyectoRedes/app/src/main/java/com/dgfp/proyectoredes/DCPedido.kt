package com.dgfp.proyectoredes

data class DCPedido(
    val Id_Usuario: Int,
    val Id_Cafeteria: Int,
    val Id_Sucursal: Int,
    val Orden: Int,
    val Pagado: String,
    val Tiempo: Int,
    val Tipo_pago: String,
    val Fecha: String
)