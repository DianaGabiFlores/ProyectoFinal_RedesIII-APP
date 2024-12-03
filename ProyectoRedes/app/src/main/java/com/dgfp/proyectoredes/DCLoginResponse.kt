package com.dgfp.proyectoredes

data class DCLoginResponse(
    val success: Boolean,
    val message: String,
    val usuario: DCUsuario? = null
)