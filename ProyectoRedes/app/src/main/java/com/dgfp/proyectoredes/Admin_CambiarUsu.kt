package com.dgfp.proyectoredes

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Vector

class Admin_CambiarUsu : AppCompatActivity() {
    private var toast: Toast? = null
    private lateinit var btnCambiarUsu: Button
    private lateinit var spinnerUsu: Spinner
    private lateinit var spinnerTipo: Spinner
    private lateinit var spinnerSuc: Spinner
    private lateinit var spinnerCaf: Spinner
    private lateinit var txtEnc: TextView
    var db: DBSQLite = DBSQLite(this) //Base de Datos
    private lateinit var baseURL: String
    private var Nombres: ArrayList<String> = ArrayList()
    private var Id_Usuario:ArrayList<String> = ArrayList()
    private var CafeteriaNom:ArrayList<String> = ArrayList()
    private var CafeteriaId:ArrayList<String> = ArrayList()
    private var SucursalNombre:ArrayList<String> = ArrayList()
    private var SucursalId:ArrayList<String> = ArrayList()
    private var Sucursal:ArrayList<List<String>> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.admin_agregarusuario)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        spinnerUsu = findViewById(R.id.spnUsuarios)
        spinnerTipo = findViewById(R.id.spnTipo)
        spinnerSuc = findViewById(R.id.spnSucursal)
        spinnerCaf = findViewById(R.id.spnCafeteria)
        txtEnc = findViewById(R.id.txtEnc)
        baseURL = baseContext.getString(R.string.baseURL)

        //Verificar que se obtuviron los datos del usuario
        var datosUsuario: Vector<String> = db.obtenerUsuario()
        if(datosUsuario != null) {
            Toast.makeText(this, "Bienvenido "+datosUsuario[1], Toast.LENGTH_LONG).show()
        }

        TraerUsuarios(datosUsuario[0].toInt())

        TraerCafeteria()

        val TipoUsu = ArrayAdapter.createFromResource(this, R.array.tipoCliente, android.R.layout.simple_spinner_item)
        TipoUsu.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinnerTipo.setAdapter(TipoUsu)

//        val Usuarios: ArrayAdapter<String> =
//            ArrayAdapter<String>(
//                applicationContext,
//                android.R.layout.simple_spinner_dropdown_item,
//                Nombres
//            )
//        Usuarios.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        spinnerUsu.setAdapter(Usuarios)

//        val cafeteriaAdp: ArrayAdapter<String> =
//            ArrayAdapter<String>(
//                applicationContext,
//                android.R.layout.simple_spinner_dropdown_item,
//                CafeteriaNom
//            )
//        cafeteriaAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        spinnerSuc.setAdapter(cafeteriaAdp)

//        val sucursalAdp: ArrayAdapter<String> =
//            ArrayAdapter<String>(
//                applicationContext,
//                android.R.layout.simple_spinner_dropdown_item,
//                SucursalNombre
//            )
//        sucursalAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        spinnerSuc.setAdapter(sucursalAdp)

        btnCambiarUsu = findViewById(R.id.btnCambiar)

        //Listener
        btnCambiarUsu.setOnClickListener(evento)
        spinnerUsu.setOnItemSelectedListener(evento1)
        spinnerTipo.setOnItemSelectedListener(evento2)
        spinnerCaf.setOnItemSelectedListener(evento3)
    }


    private val evento1: AdapterView.OnItemSelectedListener =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
//                mostrarToast(Nombres.get(i))
                mostrarToast(spinnerUsu.selectedItem.toString())
                val sucursalAdp: ArrayAdapter<String> =
                    ArrayAdapter<String>(
                        applicationContext,
                        android.R.layout.simple_spinner_dropdown_item,
                        SucursalNombre
                    )
                sucursalAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerSuc.setAdapter(sucursalAdp)
            }
            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }
        }

    private val evento3: AdapterView.OnItemSelectedListener =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
//                mostrarToast(spinnerUsu.selectedItem.toString())
                TraerSucursales(CafeteriaId.get(i))

            }
            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }
        }

    private val evento2: AdapterView.OnItemSelectedListener =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
//                mostrarToast(spinnerTipo.selectedItem.toString())
                if(spinnerTipo.selectedItem.toString().equals("Encargado")){
                    spinnerSuc.visibility = View.VISIBLE
                    spinnerCaf.visibility = View.VISIBLE
                    txtEnc.visibility = View.VISIBLE
                }else{
                    txtEnc.visibility = View.INVISIBLE
                    spinnerSuc.visibility = View.INVISIBLE
                    spinnerCaf.visibility = View.INVISIBLE
                }

            }
            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }
        }

    private val evento = View.OnClickListener { v ->
        if(v == btnCambiarUsu) {
            val id = Nombres.indexOf(spinnerUsu.selectedItem.toString())
            val tipo = spinnerTipo.selectedItem.toString()
            val cafe = CafeteriaNom.indexOf(spinnerCaf.selectedItem.toString())
            val suc = SucursalNombre.indexOf(spinnerSuc.selectedItem.toString())

            CambiarUsu(Id_Usuario.get(id).toInt(), tipo, CafeteriaId.get(cafe).toInt(), SucursalId.get(suc).toInt())
        }
    }

    fun mostrarToast(mensaje: String) {
        if(toast != null) toast!!.cancel()
        toast = Toast.makeText(this@Admin_CambiarUsu, mensaje, Toast.LENGTH_LONG)
        toast!!.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        db.borrarUsuario()
    }

    fun TraerUsuarios(user: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(APIService::class.java)

        val userId = DCUser(user.toString())
        apiService.getUsuarios(userId).enqueue(object : Callback<List<DCUsuarioDatos>> {
            override fun onResponse(
                call: Call<List<DCUsuarioDatos>>,
                response: Response<List<DCUsuarioDatos>>
            ) {
                if (response.isSuccessful) {
                    val Usuarios = response.body()
                    if (Usuarios != null) {
                        for(usuario in Usuarios) {
                            Nombres.add(usuario.Nombre_Completo)
                            Id_Usuario.add(usuario.Id_Usuario)
                        }
                        val Usuarios: ArrayAdapter<String> =
                            ArrayAdapter<String>(
                                applicationContext,
                                android.R.layout.simple_spinner_dropdown_item,
                                Nombres
                            )
                        Usuarios.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinnerUsu.setAdapter(Usuarios)
                        spinnerUsu.textAlignment = View.TEXT_ALIGNMENT_CENTER
                    }
                }
            }

            override fun onFailure(call: Call<List<DCUsuarioDatos>>, t: Throwable) {
                mostrarToast("Error: " + t)
            }

        })
    }

    fun TraerCafeteria() {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(APIService::class.java)
        apiService.getSucursalesCaf().enqueue(object : Callback<List<DCSucCaf>> {
            override fun onResponse(
                call: Call<List<DCSucCaf>>,
                response: Response<List<DCSucCaf>>
            ) {
                if (response.isSuccessful) {
                    val cafeteria = response.body()
                    if (cafeteria != null) {
                        for(cafe in cafeteria) {
                            CafeteriaNom.add(cafe.Nombre_Cafeteria)
                            CafeteriaId.add(cafe.Id_Cafeteria)
                        }
                        TraerSucursales(CafeteriaId.get(0))
                        val cafeteriaAdp: ArrayAdapter<String> =
                            ArrayAdapter<String>(
                                applicationContext,
                                android.R.layout.simple_spinner_dropdown_item,
                                CafeteriaNom
                            )
                        cafeteriaAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinnerCaf.setAdapter(cafeteriaAdp)
                    }
                }
            }

            override fun onFailure(call: Call<List<DCSucCaf>>, t: Throwable) {
                mostrarToast("Error: " + t)
            }

        })
    }

    fun TraerSucursales(cafe: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(APIService::class.java)
        val cafeteria = DCUser(cafe)
        apiService.getSucursales(cafeteria).enqueue(object : Callback<List<DCSucursal>> {
            override fun onResponse(
                call: Call<List<DCSucursal>>,
                response: Response<List<DCSucursal>>
            ) {
                if (response.isSuccessful) {
                    val sucursal = response.body()
                    if (sucursal != null) {
                        SucursalId.clear()
                        SucursalNombre.clear()
                        for(suc in sucursal) {
                            SucursalNombre.add(suc.Nombre_Sucursal)
                            SucursalId.add(suc.Id_Sucursal)
                        }

                        val sucursalAdp: ArrayAdapter<String> =
                            ArrayAdapter<String>(
                                applicationContext,
                                android.R.layout.simple_spinner_dropdown_item,
                                SucursalNombre
                            )
                        sucursalAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinnerSuc.setAdapter(sucursalAdp)
                    }
                }
            }

            override fun onFailure(call: Call<List<DCSucursal>>, t: Throwable) {
                mostrarToast("Error: " + t)
            }

        })
    }

    fun CambiarUsu(id:Int, tipo:String, cafe:Int, suc:Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(APIService::class.java)
        val usuario = DCUserTipo(id, tipo, cafe, suc)
        apiService.cambiarUsu(usuario).enqueue(object : Callback<DCResponse> {
            override fun onResponse(
                call: Call<DCResponse>,
                response: Response<DCResponse>
            ) {
                if (response.isSuccessful) {
                    val respuesta = response.body()
                    if (respuesta != null) {
                        mostrarToast(respuesta.message)
                    }
                }
            }

            override fun onFailure(call: Call<DCResponse>, t: Throwable) {
                mostrarToast("Error: " + t)
            }

        })
    }

}