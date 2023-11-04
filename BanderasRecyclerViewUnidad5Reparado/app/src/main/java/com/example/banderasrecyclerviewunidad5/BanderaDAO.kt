package com.example.banderasrecyclerviewunidad5

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.banderasrecyclerviewunidad5.DataBase.DBOpenHelper

class BanderaDAO {
    fun cargarLista(context: Context?): MutableList<Bandera> {
        lateinit var res: MutableList<Bandera>
        var c: Cursor? = null
        try {
            val db = DBOpenHelper.getInstance(context)!!.readableDatabase
            val sql = "SELECT * FROM comunidades;"
            c = db.rawQuery(sql, null)
            res = mutableListOf()
            while (c.moveToNext()) {
                val nueva = Bandera(
                    c.getInt(0), c.getString(1),
                    c.getString(2)
                )
                res.add(nueva)
            }
        } finally {
            c?.close()
        }
        return res
    }

    fun actualizarBBDD(context: Context?, bandera: Bandera) {
        val db = DBOpenHelper.getInstance(context)!!.writableDatabase
        db.execSQL(
            "UPDATE comunidades " +
                    "SET nombre='${bandera.nombre}', imagen='${bandera.imagen}' " +
                    "WHERE id=${bandera.id};"
        )
        val values = ContentValues()
        values.put(BanderaContract.Companion.Entrada.COLUMNA_ID, bandera.id)
        values.put(BanderaContract.Companion.Entrada.COLUMNA_NOMBRE, bandera.nombre)
        values.put(BanderaContract.Companion.Entrada.COLUMNA_IMAGEN, bandera.imagen)
        db.update(
            BanderaContract.Companion.Entrada.NOMBRE_TABLA,
            values,
            "id=?",
            arrayOf(bandera.id.toString())
        )
        db.close()
    }

    fun eliminarComunidad(context: Context, bandera: Bandera) {
        val db = DBOpenHelper.getInstance(context)!!.writableDatabase
        db.delete(
            BanderaContract.Companion.Entrada.NOMBRE_TABLA,
            "${BanderaContract.Companion.Entrada.COLUMNA_ID}=?",
            arrayOf(bandera.id.toString())
        )
        db.close()
    }
}