package com.dgfp.proyectoredes

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.Vector

class DBSQLite(context: Context?) :
    SQLiteOpenHelper(context, TABLE_NAME, null, DATABASE_VERSION) {

    companion object {
        //Métodos de SQLiteOpenHelper
        //Si se cambia la estructura de la BD, se debe de incrementar la version de la BD.
        val DATABASE_VERSION: Int = 1
        val TABLE_NAME: String = "usuario"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE $TABLE_NAME (Id_Usuario TEXT PRIMARY KEY, Nombre TEXT, PrimerApellido TEXT, SegundoApellido TEXT, Contrasena TEXT, Telefono TEXT, Email TEXT, Tipo TEXT)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //En caso de una nueva versión habría que actualizar las tablas
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //En caso de regresar a una versión anterior habría que actualizar las tablas
    }

    fun insertarUsuario(Id_Usuario: String, Nombre: String, PrimerApellido: String, SegundoApellido: String, Contrasena: String, Telefono: String, Email: String, Tipo: String) {
        borrarUsuario()
        val db = writableDatabase
        db.execSQL(
            "INSERT INTO $TABLE_NAME VALUES ('$Id_Usuario', '$Nombre', '$PrimerApellido', '$SegundoApellido', '$Contrasena', '$Telefono', '$Email', '$Tipo')"
        )
    }

    fun obtenerUsuario(): Vector<String> {
        val result = Vector<String>()
        val db = readableDatabase
        var cursor = db.rawQuery(
            "SELECT * FROM $TABLE_NAME",
            null
        )
        while(cursor.moveToNext()) {
            result.add(cursor.getString(0)) //Id_Usuario
            result.add(cursor.getString(1)) //Nombre
            result.add(cursor.getString(2)) //PrimerApellido
            result.add(cursor.getString(3)) //SegundoApellido
            result.add(cursor.getString(4)) //Contrasena
            result.add(cursor.getString(5)) //Telefono
            result.add(cursor.getString(6)) //Email
            result.add(cursor.getString(7)) //Tipo
        }
        cursor.close()
        return result
    }

    fun borrarUsuario() {
        val db = writableDatabase
        db.execSQL("DELETE FROM $TABLE_NAME")
    }
}