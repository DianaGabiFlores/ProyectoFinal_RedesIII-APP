package com.dgfp.proyectoredes

import retrofit2.Call
import retrofit2.http.GET

interface APIServiceCafeteria {
    @GET("cafeterias")
    fun getCafeterias(): Call<List<DCCafeteria>>
}