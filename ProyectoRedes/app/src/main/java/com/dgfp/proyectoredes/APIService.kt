package com.dgfp.proyectoredes

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface APIService {
    @POST("login")
    fun loginUsuario(@Body usuario: DCUsuarioLlaves): Call<DCLoginResponse>

    @POST("usuarios/crear")
    fun crearUsuario(@Body usuario: DCUsuario): Call<DCRegistrarResponse>

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

    @POST("pedido/cancelado")
    fun pedidoCancelado(@Body orden: DCOrden): Call<Boolean>

    @POST("pedido/agregar")
    fun setPedido(@Body pedido: DCPostPedido): Call<DCPedidoResponse>

    @GET("telefonos/encargados")
    fun getTelefonosCafeterias(): Call<List<DCTelefonoEncargado>>

    @GET("/v2/directions/driving-car")
    suspend fun getRoute(
        @Query("api_key") key: String,
        @Query("start", encoded = true) start: String,
        @Query("end", encoded = true) end: String
    ): Response<RouteResponse>

    @POST("usuario/info")
    fun getUsuarios(@Body user: DCUser): Call<List<DCUsuarioDatos>>

    @GET("cafeusu/cafeterias")
    fun getSucursalesCaf(): Call<List<DCSucCaf>>

    @POST("cafeusu/Sucursal")
    fun getSucursales(@Body user: DCUser): Call<List<DCSucursal>>

    @POST("usuario/cambiar")
    fun cambiarUsu(@Body usuario: DCUserTipo ) :Call<DCResponse>

    @POST("sucursal/cafeterias")
    fun getCafeteriasSucursal(@Body suc: DCUser) : Call<DCCafeteriaNombre>
}