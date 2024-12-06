package com.dgfp.proyectoredes

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIService {
    @POST("login")
    fun loginUsuario(@Body usuario: DCUsuarioLlaves): Call<DCLoginResponse>

    @POST("usuario/nuevo")
    fun crearUsuario(@Body usuario: DCUsuario): Call<DCUsuario>

    @POST("pedidos")
    fun getPedidos(@Body user: DCUser): Call<List<PedidosInfo>>

    @GET("cafeterias")
    fun getCafeterias(): Call<List<DCCafeteria>>

    @GET("menus")
    fun getMenus(): Call<List<DCMenu>>
}