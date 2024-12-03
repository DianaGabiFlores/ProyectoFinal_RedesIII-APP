package com.dgfp.proyectoredes

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface APIServiceUsuario {
    //@GET("usuarios")
    //fun getPostsUsuarios(): Call<List<DCRegistrarUsuario>>

    @POST("login")
    fun loginUsuario(@Body usuario: DCUsuarioLlaves): Call<DCLoginResponse>

    @POST("registrar")
    fun registrarUsuario(@Body usuario: DCUsuario): Call<DCUsuario>
}