package com.dgfp.proyectoredes

import retrofit2.Call
import retrofit2.http.GET

interface APIServiceUsuario {
    @GET("usuarios")
    fun getPostsUsuarios(): Call<List<Usuario>>
}