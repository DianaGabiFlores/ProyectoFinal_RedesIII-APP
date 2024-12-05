package com.dgfp.proyectoredes

import java.util.Date

data class PedidosInfo(
    val Cafeteria: String,
    val Sucursal: String,
    val Comida: String,
    val Precio: Float,
    val Id_Usuario: Int,
    val Id_Cafeteria: Int,
    val Id_Sucursal: Int,
    val Orden: Int,
    val Pagado: Char,
    val Tiempo: Int,
    val Tipo_pago: Char,
    val Fecha: String
)
