package com.dgfp.proyectoredes

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.UiSettings
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class PruebaMapa : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mapactivity)
        createMapFragment()
    }
    private fun createMapFragment() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.setMinZoomPreference(6.0f)
        map.setMaxZoomPreference(25.0f)
        createMarker()
        enableMyLocation()

    }

    private fun createMarker(){
        val cafeteriaSurLL = LatLng(21.91000751748219, -102.31502264641846)
        val cafeteriaSur = map.addMarker(
            MarkerOptions()
                .position(cafeteriaSurLL)
                .title("Cafetería Sur")
                .snippet("Lista de cafeterias \n Hola \n asdfsa \n asdfsfd")
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.iconcafe))
        )
        cafeteriaSur?.showInfoWindow()


        val cafeteriaOrienteLL = LatLng(21.913336757793253, -102.31773449396881)
        val cafeteriaOriente = map.addMarker(
            MarkerOptions()
                .position(cafeteriaOrienteLL)
                .title("Cafetería Oriente")
                .snippet("Lista de cafeterias \n Hola \n asdfsa \n asdfsfd")
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.iconcafe))
        )

        val comedorUniLL = LatLng(21.914137570362808, -102.31462639530848)
        val comedorUni = map.addMarker(
            MarkerOptions()
                .position(comedorUniLL)
                .title("Comedor Universitario")
                .snippet("Lista de cafeterias \n Hola \n asdfsa \n asdfsfd")
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.iconcafe))
        )

        val cafeteriaCSLL = LatLng(21.91710769143836, -102.31514356066208)
        val cafeteriaCS = map.addMarker(
            MarkerOptions()
                .position(cafeteriaCSLL)
                .title("Cafetería Área Médica")
                .snippet("Lista de cafeterias \n Hola \n asdfsa \n asdfsfd")
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.iconcafe))
        )
        val cafeteriaNorteLL = LatLng(21.917169969739, -102.319348465737)
        val cafeteriaNorte = map.addMarker(
            MarkerOptions()
                .position(cafeteriaNorteLL)
                .title("Cafetería Norte")
                .snippet("Lista de cafeterias \n Hola \n asdfsa \n asdfsfd")
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.iconcafe))
        )


        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(cafeteriaSurLL, 18f),
            4000,
            null
        )
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

//    fun calcRoute() {
//        var start = " "
//        var end = " "
//        var request = {
//                origin: start,
//                destination: end,
//                travelMode: 'DRIVING'
//        };
//        directionsService.route(request, function(result, status) {
//            if (status == 'OK') {
//                directionsRenderer.setDirections(result);
//            }
//        });
//    }

}