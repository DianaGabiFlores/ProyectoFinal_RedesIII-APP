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
    private var mapa: Drawable? = null

    //App
    private var menuTipo: Int = 0

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
        val a = getContext().obtainStyledAttributes(attrs, R.styleable.menu)
        menuTipo = a.getString(R.styleable.menu_tipo)!!.toInt()
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
        mapa!!.draw(canvas)

        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val alto = measuredHeight.toFloat()
        val ancho = measuredWidth.toFloat()

        if(event.y >= 25 && event.y <= 175){
            if(event.x >= (ancho/8-75) && event.x <= (ancho/8+75) && menuTipo != 1){
                val intent = Intent(context, CafeteriaActivity::class.java)
                context.startActivity(intent)
            }else if(event.x >= (ancho/8*3-75) && event.x <=  (ancho/8*3+75) && menuTipo != 2){
                val intent = Intent(context, PedidosCActivity::class.java)
                context.startActivity(intent)
            }else if(event.x >= (ancho/8*5-75) && event.x <= (ancho/8*5+75) && menuTipo != 3){
                val intent = Intent(context, PruebaMapa::class.java)
                context.startActivity(intent)
            }else if(event.x >= (ancho/8*7-75) && event.x <= (ancho/8*7+75) && menuTipo != 4){
                val intent = Intent(context, UsuarioActivity::class.java)
                context.startActivity(intent)
            }
        }

        return true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val alto = measuredHeight.toFloat()
        val ancho = measuredWidth.toFloat()

        home = AppCompatResources.getDrawable(getContext(), R.drawable.baseline_home_24)
        home!!.setBounds((ancho/8-75).toInt(), 25, (ancho/8+75).toInt(), 175)

        pedidos = AppCompatResources.getDrawable(getContext(), R.drawable.baseline_fastfood_24)
        pedidos!!.setBounds((ancho/8*3-75).toInt(), 25, (ancho/8*3+75).toInt(), 175)

        user = AppCompatResources.getDrawable(getContext(), R.drawable.baseline_map_24)
        user!!.setBounds((ancho/8*5-75).toInt(), 25, (ancho/8*5+75).toInt(), 175)

        mapa = AppCompatResources.getDrawable(getContext(), R.drawable.baseline_person_pin_24)
        mapa!!.setBounds((ancho/8*7-75).toInt(), 25, (ancho/8*7+75).toInt(), 175)


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