package com.dgfp.proyectoredes

data class DCMenu(
    val Id_Comida: String,
    val Nombre: String,
    val Precio: String,
    val Id_Cafeteria: String,
    val Id_Sucursal: String,
    val TiempoPrepa: String,
    val Ingredientes: List<String>
)