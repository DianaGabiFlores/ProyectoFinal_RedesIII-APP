package com.dgfp.proyectoredes

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface APIServiceUsuario {
    @POST("login")
    fun loginUsuario(@Body usuario: DCUsuarioLlaves): Call<DCLoginResponse>

    @POST("usuario/nuevo")
    fun crearUsuario(@Body usuario: DCUsuario): Call<DCUsuario>
}