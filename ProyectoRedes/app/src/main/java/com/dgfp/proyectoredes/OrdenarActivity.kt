package com.dgfp.proyectoredes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Calendar
import java.util.Vector

class OrdenarActivity : AppCompatActivity() {
    private var toast: Toast? = null
    private lateinit var imgComida: ImageView
    private lateinit var txtNombre: TextView
    private lateinit var txtPrecio: TextView
    private lateinit var txtTempoPreparacion: TextView
    private lateinit var txtIngredientes: TextView
    private lateinit var txtListaIngredientes: TextView
    private lateinit var btnOrdenar: Button
    var db: DBSQLite = DBSQLite(this) //Base de Datos
    private lateinit var baseURL: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ordenar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        baseURL = baseContext.getString(R.string.baseURL)
        imgComida = findViewById(R.id.imgComida)
        txtNombre = findViewById(R.id.txtNombre)
        txtPrecio = findViewById(R.id.txtPrecio)
        txtTempoPreparacion = findViewById(R.id.txtTiempoPreparacion)
        txtIngredientes = findViewById(R.id.txtIngredientes)
        txtListaIngredientes = findViewById(R.id.txtListaIngredientes)
        btnOrdenar = findViewById(R.id.btnOrdenar)

        if (intent.extras != null) {
            val comida: Comida = intent.getSerializableExtra("comida") as Comida

            imgComida.setImageResource(comida.getImagen())
            txtNombre.setText(comida.getNombre())
            txtPrecio.text = "$ " + comida.getPrecio()
            txtTempoPreparacion.setText("Tiempo de Preparación: "+comida.getTiempoPreparacion() + " minutos")

            if(comida.getIngredientes().size == 1) {
                txtIngredientes.text = "Ingrediente"
            }
            if(comida.getIngredientes().size > 1) {
                txtIngredientes.text = "Ingredientes"
            }
            val listaIngredientes = StringBuilder()
            for(ingrediente in comida.getIngredientes()) {
                listaIngredientes.append("\u2022 $ingrediente\n")
            }
            txtListaIngredientes.text = listaIngredientes.toString()

            btnOrdenar.setOnClickListener {
                mostrarDialogo(comida)
            }
        }
    }

    fun mostrarDialogo(comida: Comida) {
        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.dialog_pedido, null)
        val txtTitulo: TextView = view.findViewById(R.id.txtTitulo)
        val radioGroup: RadioGroup = view.findViewById(R.id.radioGroup)
        val rbtnOpc1: RadioButton = view.findViewById(R.id.rbtnOpc1)
        val rbtnOpc2: RadioButton = view.findViewById(R.id.rbtnOpc2)
        val btnConfirmar: Button = view.findViewById(R.id.btnConfirmar)
        builder.setView(view)
        val dialog: AlertDialog = builder.create()

        radioGroup.clearCheck()
        radioGroup.check(R.id.rbtnOpc1)
        var tipoPago = "E"

        //Efectivp
        rbtnOpc1.setOnClickListener {
            tipoPago = "E"
        }
        //Transferencia
        rbtnOpc2.setOnClickListener {
            tipoPago = "T"
        }
        //Confirmar el pedido
        btnConfirmar.setOnClickListener {
            val usuario: Vector<String> = db.obtenerUsuario()
            ordenar(usuario[0], comida.getIdCafeteria(), comida.getIdSucursal(), comida.getIdComida(), comida.getTiempoPreparacion(), tipoPago)
            dialog.dismiss()
        }

        dialog.show()
    }

    fun ordenar(idUsuario: String, idCafeteria: String, idSucursal: String, idComida:String, tiempoPreparacion: String, tipoPago: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(APIService::class.java)

        val objPedido = DCPedido(
            Id_Usuario = idUsuario.toInt(),
            Id_Cafeteria = idCafeteria.toInt(),
            Id_Sucursal = idSucursal.toInt(),
            Orden = 0,
            Pagado = "N",
            Tiempo = tiempoPreparacion.toInt(),
            Tipo_pago = tipoPago,
            Fecha = obtenerFechaActual() // Fecha actual en el formato deseado
        )

        val objComida = DCComida(
            Id_Comida = idComida
        )

        val objPostPedido = DCPostPedido(
            Pedido = objPedido,
            Comida = objComida
        )

        apiService.setPedido(objPostPedido).enqueue(object : Callback<DCPedidoResponse> {
            override fun onResponse(call: Call<DCPedidoResponse>, response: Response<DCPedidoResponse>) {

                if(response.isSuccessful) {
                    val respuesta = response.body()
                    if(respuesta != null) {
                        if(respuesta.Status) {
                            mostrarToast("Pedido agregado.")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<DCPedidoResponse>, t: Throwable) {
                //Manejo de error
                mostrarToast("Error de conexión: " + t.message)
            }
        })
    }

    fun obtenerFechaActual(): String {
        val fecha = Calendar.getInstance()
        val anio = fecha.get(Calendar.YEAR)
        val mes = fecha.get(Calendar.MONTH)+1
        val dia = fecha.get(Calendar.DAY_OF_MONTH)
        return "$anio/$mes/$dia"
    }

    fun mostrarToast(mensaje: String) {
        if(toast != null) toast!!.cancel()
        toast = Toast.makeText(this@OrdenarActivity, mensaje, Toast.LENGTH_LONG)
        toast!!.show()
    }
}