package com.dgfp.proyectoredes

import android.R
import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InfoWindowAdapter(mContext: Context) : GoogleMap.InfoWindowAdapter {
    var mWindow: View = LayoutInflater.from(mContext).inflate(com.dgfp.proyectoredes.R.layout.infowindow_layout, null)
    var baseURL: String = ""
    private lateinit var cafe: Array<String>

    private fun setInfoWindowText(marker: Marker) {
        val title = marker.title
        val snippet = marker.snippet

        if (title != null) {
            cafeterias(title)
        }

        val tvTitle = mWindow.findViewById<TextView>(com.dgfp.proyectoredes.R.id.info_window_nombre)
        val tvLocal = mWindow.findViewById<TextView>(com.dgfp.proyectoredes.R.id.info_window_local)
        val tvCafe = mWindow.findViewById<TextView>(com.dgfp.proyectoredes.R.id.info_window_cafe)

        tvCafe.setText("")
        tvTitle.setText(title)
        tvLocal.setText("Local: " + snippet)


        for(i in 0..cafe.size-1){
            tvCafe.append(cafe[i]+ "\n")
        }

//        if (!TextUtils.isEmpty(title)) {
//            tvTitle.text = title
//        }
    }

    override fun getInfoWindow(p0: Marker): View {
        setInfoWindowText(p0)
        return mWindow
    }

    override fun getInfoContents(p0: Marker): View {
        setInfoWindowText(p0)
        return mWindow
    }

    fun cafeterias(sucursal: String) {
        when (sucursal) {
            "Cafetería Oriente" -> cafe = arrayOf("La Gula", "Café Original de la taza", "Las Exquisitas", "El Álamo")
            "Cafetería Sur" -> cafe = arrayOf("Las Exquisitas","Cerezza")
            "Comedor Universitario" -> cafe = arrayOf( "La Gula", "Yosakura", "Gamebar")
            "Cafetería de Ciencias de la Salud" -> cafe = arrayOf("Chocos de la Puri", "Klauc´k")
            "Cafetería Norte" -> cafe = arrayOf("La Gula", "Corazón", "Cetriolo")
        }

//        baseURL = com.dgfp.proyectoredes.R.string.baseURL.toString()
//        baseURL = "http:// 10.0.0.10:3000/"
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl(baseURL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        val apiService = retrofit.create(APIService::class.java)
//
//        val suc = DCUser(sucursal)
//        apiService.getCafeteriasSucursal(suc).enqueue(object : Callback<DCCafeteriaNombre> {
//            override fun onResponse(
//                call: Call<DCCafeteriaNombre>,
//                response: Response<DCCafeteriaNombre>
//            ) {
//                val cafeList = response.body()
//                if (cafeList != null) {
//                    Log.i("cafeterias", cafeList.toString())
//                }
//
//            }
//
//            override fun onFailure(call: Call<DCCafeteriaNombre>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//        })
    }

}
