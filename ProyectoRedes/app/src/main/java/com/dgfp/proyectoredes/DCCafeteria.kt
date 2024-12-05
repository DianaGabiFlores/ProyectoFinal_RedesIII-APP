package com.dgfp.proyectoredes

data class DCCafeteria(
    val Id_Cafeteria: String,
    val Nombre: String,
    val Sucursales: List<DCCafeteria_Sucursal>
)