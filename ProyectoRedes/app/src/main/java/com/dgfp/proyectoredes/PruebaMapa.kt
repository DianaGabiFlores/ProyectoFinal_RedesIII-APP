package com.dgfp.proyectoredes

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PruebaMapa : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener{

    private var toast: Toast? = null
    private lateinit var map: GoogleMap
    var poly: Polyline? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var lastKnownLocation: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.dgfp.proyectoredes.R.layout.mapactivity)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        createMapFragment()
    }

    private fun createMapFragment() {
        val mapFragment = supportFragmentManager
            .findFragmentById(com.dgfp.proyectoredes.R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    @SuppressLint("PotentialBehaviorOverride")
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.setMinZoomPreference(6.0f)
        map.setMaxZoomPreference(25.0f)
        createMarker()
        enableMyLocation()

        map.setOnMarkerClickListener { marker ->
            if (marker.isInfoWindowShown) {
                Toast.makeText(this, "Entro", Toast.LENGTH_SHORT).show()
                marker.hideInfoWindow()
            } else {
                marker.showInfoWindow()
            }

            if (lastKnownLocation != null) {
                val currentLatLng = LatLng(
                    lastKnownLocation!!.latitude,
                    lastKnownLocation!!.longitude
                )
                drawRoute(currentLatLng, marker.position)
            }
            else {
                mostrarToast("No se pudo obtener la ubicación actual.")
            }

            true
        }

    }

    private fun createMarker(){

        //Set Custom InfoWindow Adapter
        val adapter: InfoWindowAdapter = InfoWindowAdapter(this@PruebaMapa)
        map.setInfoWindowAdapter(adapter)

        val prueba = LatLng(8.687872,49.420318)
        val cafeteriaSurLL = LatLng(21.91000751748219, -102.31502264641846)
        val cafeteriaSur = map.addMarker(
            MarkerOptions()
                .position(cafeteriaSurLL)
                .title("Cafetería Sur")
                .snippet("210")
    //                .icon(BitmapDescriptorFactory.fromResource(R.drawable.iconcafe))
        )


        val cafeteriaOrienteLL = LatLng(21.913336757793253, -102.31773449396881)
        val cafeteriaOriente = map.addMarker(
            MarkerOptions()
                .position(cafeteriaOrienteLL)
                .title("Cafetería Oriente")
                .snippet("47")
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.iconcafe))
        )?.showInfoWindow()

        val comedorUniLL = LatLng(21.914137570362808, -102.31462639530848)
        val comedorUni = map.addMarker(
            MarkerOptions()
                .position(comedorUniLL)
                .title("Comedor Universitario")
                .snippet("9")
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.iconcafe))
        )

        val cafeteriaCSLL = LatLng(21.91710769143836, -102.31514356066208)
        val cafeteriaCS = map.addMarker(
            MarkerOptions()
                .position(cafeteriaCSLL)
                .title("Cafetería de Ciencias de la Salud")
                .snippet("104")
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.iconcafe))
        )
        val cafeteriaNorteLL = LatLng(21.917169969739, -102.319348465737)
        val cafeteriaNorte = map.addMarker(
            MarkerOptions()
                .position(cafeteriaNorteLL)
                .title("Cafetería Norte")
                .snippet("122")
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.iconcafe))
        )


        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(cafeteriaOrienteLL, 18f),
            4000,
            null
        )

//        createRoute()
    }

    private fun isPermissionsGranted() = ContextCompat.checkSelfPermission(
        this,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    private fun enableMyLocation() {
        if (!::map.isInitialized) return
        if (isPermissionsGranted()) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }

            map.isMyLocationEnabled = true
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    lastKnownLocation = location
                }
            }

        } else {
            requestLocationPermission()
        }
    }


    companion object {
        const val REQUEST_CODE_LOCATION = 0
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(this, "Ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                map.isMyLocationEnabled = true
            }else{
                Toast.makeText(this, "Para activar la localización ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }

    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        if (!::map.isInitialized) return
        if(!isPermissionsGranted()){
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            map.isMyLocationEnabled = false
            Toast.makeText(this, "Para activar la localización ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onMyLocationButtonClick(): Boolean {
       return false
    }

    /** Called when the user clicks a marker.  */
    override fun onMarkerClick(marker: Marker): Boolean {
        // Retrieve the data from the marker.
        val clickCount = marker.tag as? Int
        Toast.makeText(
            this,
            "${marker.title}",
            Toast.LENGTH_SHORT
        ).show()


        // Check if a click count was set, then display the click count.
        clickCount?.let {
            val newClickCount = it + 1
            marker.tag = newClickCount
            Toast.makeText(
                this,
                "${marker.title} has been clicked $newClickCount times.",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false
    }

    override fun onInfoWindowClick(marker: Marker) {
        Toast.makeText(
            this, "Info window clicked",
            Toast.LENGTH_SHORT
        ).show()
    }

    fun createRoute() {
        CoroutineScope(Dispatchers.IO).launch {

            val call = getRetrofit().create(APIService::class.java)
                .getRoute("5b3ce3597851110001cf6248822808c5ec3346d5b18e9670fff08967", "8.681495,49.41461", "8.687872,49.420318")

            Log.i("call", call.toString())
            if (call.isSuccessful) {
                //drawRoute(call.body())
            } else {
                Log.i("aris", "KO")
            }
        }
    }

    /*
    private fun drawRoute(routeResponse: RouteResponse?) {
        val polyLineOptions = PolylineOptions()
        routeResponse?.features?.first()?.geometry?.coordinates?.forEach {
            polyLineOptions.add(LatLng(it[1], it[0]))
        }
        runOnUiThread {
            poly = map.addPolyline(polyLineOptions)
        }
    }
    */
    private fun drawRoute(origin: LatLng, destination: LatLng) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java)
                .getRoute(
                    "5b3ce3597851110001cf6248822808c5ec3346d5b18e9670fff08967",
                    "${origin.longitude},${origin.latitude}",
                    "${destination.longitude},${destination.latitude}"
                )
            if (call.isSuccessful) {
                drawPolyline(call.body())
            } else {
                Log.e("RouteError", "No se pudo trazar la ruta")
            }
        }
    }
    private fun drawPolyline(routeResponse: RouteResponse?) {
        val polylineOptions = PolylineOptions().color(ContextCompat.getColor(this, android.R.color.holo_blue_light))
        routeResponse?.features?.first()?.geometry?.coordinates?.forEach {
            polylineOptions.add(LatLng(it[1], it[0]))
        }
        runOnUiThread {
            poly?.remove()
            poly = map.addPolyline(polylineOptions)
        }
    }

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openrouteservice.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun mostrarToast(mensaje: String) {
        if(toast != null) toast!!.cancel()
        toast = Toast.makeText(this@PruebaMapa, mensaje, Toast.LENGTH_LONG)
        toast!!.show()
    }
}