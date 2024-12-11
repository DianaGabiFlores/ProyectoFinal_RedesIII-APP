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

    @POST("pedidos/getEnc")
    fun getPedidosEnc(@Body user: DCUser): Call<List<DCPedidosEnc>>

    @GET("cafeterias")
    fun getCafeterias(): Call<List<DCCafeteria>>

    @GET("comidasxingredientes")
    fun getMenus(): Call<List<DCMenu>>

    @POST("usuario/getsuyca")
    fun getSucursalEnc(@Body email: DCEmail): Call<DCEncargadoSucursal>

    @POST("pedido/entregado")
    fun pedidoEntregado(@Body orden: DCOrden): Call<Boolean>

}