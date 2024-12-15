package com.dgfp.proyectoredes

import android.R
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.model.Marker


class InfoWindowAdapter: InfoWindowAdapter {
    private var inflater: LayoutInflater? = null
    private var context: Activity? = null

    override fun getInfoContents(p0: Marker): View? {
        TODO("Not yet implemented")
    }

    override fun getInfoWindow(marker: Marker): View? {
        return null
    }


//    override fun getInfoContents(marker: Marker): View? {
//        val li = LayoutInflater.from(context)
//        li.inflate(com.dgfp.proyectoredes.R.layout.infowindow_layout,this,true)
//
//        val view = LayoutInflater.from(context)
//        view.inflate(com.dgfp.proyectoredes.R.layout.infowindow_layout,this@InfoWindowAdapter,false)
//
//        val tvTitle = view.findViewById<View>(R.id.tv_title) as TextView
//        val tvSubTitle = view.findViewById<View>(R.id.tv_subtitle) as TextView
//
//        tvTitle.text = marker.title
//        tvSubTitle.text = marker.snippet
//
//        return view
//    }
}

