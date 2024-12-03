package com.dgfp.proyectoredes

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIServiceUsuario {
    @GET("usuarios")
    fun getPostsUsuarios(): Call<List<Usuario>>

    @POST("usuario/nuevo")
    fun crearUsuario(@Body usuario: Usuario): Call<Usuario>
}