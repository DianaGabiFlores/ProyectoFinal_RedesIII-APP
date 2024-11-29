package com.dgfp.proyectoredes

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat

class menu: View {

    //Imagen de fondo
    private var home: Drawable? = null
    private var pedidos: Drawable? = null
    private var user: Drawable? = null

    private var pausar: Drawable? = null
    private var play: Drawable? = null


    //Rectangulos
    private val cuadrado = Paint()

    //Textos
    private val textPaint = Paint()
    private val textNivel = Paint()

    constructor(context: Context?): super(context){
        inicializa()
    }
    constructor(context: Context?, attrs: AttributeSet?): super(context, attrs){
        inicializa()
//        val a = getContext().obtainStyledAttributes(attrs, R.styleable.Configuracion)
//        casaBol = a.getString(R.styleable.Configuracion_casa).toBoolean()
//        pausarBol = a.getString(R.styleable.Configuracion_pausar).toBoolean()
//        musicString = a.getString(R.styleable.Configuracion_musica).toString()
//        music = MediaPlayer.create(context, resources.getIdentifier(musicString, "raw", context?.getPackageName()))
////        music = MediaPlayer.create(context, R.raw.jojisantuary)
//        music?.start()
    }

    private fun inicializa() {
        textPaint.isAntiAlias = true
        textPaint.textSize = 40f

        cuadrado.color =  ResourcesCompat.getColor(resources, R.color.rosa, null)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val alto = measuredHeight.toFloat()
        val ancho = measuredWidth.toFloat()

        canvas.drawRect(0f, 0f, ancho, 200f,cuadrado)

        home!!.draw(canvas)
        user!!.draw(canvas)
        pedidos!!.draw(canvas)

        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val alto = measuredHeight.toFloat()
        val ancho = measuredWidth.toFloat()

        if(event.y >= 25 && event.y <= 175){
            if(event.x >= (ancho/4-75) && event.x <= (ancho/4+75) ){
//                val intent = Intent(context, CrearCuenta_Inter::class.java)
//                context.startActivity(intent)
            }else if(event.x >=(ancho/2-75) && event.x <=  (ancho/2+75) ){
//                val intent = Intent(context, CrearCuenta_Inter::class.java)
//                context.startActivity(intent)
            }else if(event.x >= (ancho/4*3-75) && event.x <= (ancho/4*3+75)){
//                val intent = Intent(context, CrearCuenta_Inter::class.java)
//                context.startActivity(intent)
            }
        }
        home!!.setBounds((ancho/4-75).toInt(), 25, (ancho/4+75).toInt(), 175)

        pedidos = AppCompatResources.getDrawable(getContext(), R.drawable.baseline_fastfood_24)
        pedidos!!.setBounds((ancho/2-75).toInt(), 25, (ancho/2+75).toInt(), 175)

        user = AppCompatResources.getDrawable(getContext(), R.drawable.baseline_person_pin_24)
        user!!.setBounds((ancho/4*3-75).toInt(), 25, (ancho/4*3+75).toInt(), 175)

        return true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val alto = measuredHeight.toFloat()
        val ancho = measuredWidth.toFloat()

        home = AppCompatResources.getDrawable(getContext(), R.drawable.baseline_home_24)
        home!!.setBounds((ancho/4-75).toInt(), 25, (ancho/4+75).toInt(), 175)

        pedidos = AppCompatResources.getDrawable(getContext(), R.drawable.baseline_fastfood_24)
        pedidos!!.setBounds((ancho/2-75).toInt(), 25, (ancho/2+75).toInt(), 175)

        user = AppCompatResources.getDrawable(getContext(), R.drawable.baseline_person_pin_24)
        user!!.setBounds((ancho/4*3-75).toInt(), 25, (ancho/4*3+75).toInt(), 175)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val ancho = calcularAncho(widthMeasureSpec)
        val alto = calcularAlto(heightMeasureSpec)
        setMeasuredDimension(ancho, alto)
    }
    private fun calcularAlto(heightMeasureSpec: Int): Int {
        var res = 100
        val modo = MeasureSpec.getMode(heightMeasureSpec)
        val limite = MeasureSpec.getSize(heightMeasureSpec)
        if(modo == MeasureSpec.AT_MOST || modo == MeasureSpec.EXACTLY){
            res = limite
        }
        return res
    }
    private fun calcularAncho(widthMeasureSpec: Int): Int {
        var res = 100
        val modo = MeasureSpec.getMode(widthMeasureSpec)
        val limite = MeasureSpec.getSize(widthMeasureSpec)
        if(modo == MeasureSpec.AT_MOST || modo == MeasureSpec.EXACTLY){
            res = limite
        }
        return res
    }


}