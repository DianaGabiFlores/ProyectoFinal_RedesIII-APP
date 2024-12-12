package com.dgfp.proyectoredes

data class DCPedido(
    val Id_Usuario: String,
    val Id_Cafeteria: String,
    val Id_Sucursal: String,
    val Orden: String,
    val Pagado: String,
    val Tiempo: String,
    val Tipo_pago: String,
    val Fecha: String
)