package com.example.banderasrecyclerviewunidad5

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.banderasrecyclerviewunidad5.DataBase.DBOpenHelper

/*
 * Clase BanderaDAO (Data Access Object), que proporciona métodos para interactuar con la base de datos y manejar objetos Bandera.
 * Esta clase incluye métodos para cargar, actualizar y eliminar registros de banderas en la base de datos.
 */
class BanderaDAO {
    /*
     * Método para cargar una lista de objetos Bandera desde la base de datos.
     * @param context Contexto de la aplicación, necesario para obtener una instancia de la base de datos.
     * @return MutableList<Bandera> Una lista mutable de objetos Bandera.
     */
    fun cargarLista(context: Context?): MutableList<Bandera> {
        lateinit var res: MutableList<Bandera> // Lista mutable para almacenar los resultados.
        var c: Cursor? = null // Cursor para iterar sobre los resultados de la consulta SQL.
        try {
            // Obtener una instancia legible de la base de datos.
            val db = DBOpenHelper.getInstance(context)!!.readableDatabase
            val sql = "SELECT * FROM comunidades;" // Consulta SQL para seleccionar todas las banderas.
            c = db.rawQuery(sql, null) // Ejecutar la consulta SQL.
            res = mutableListOf() // Inicializar la lista de resultados.
            // Iterar sobre los resultados y agregar cada bandera a la lista.
            while (c.moveToNext()) {
                val nueva = Bandera(
                    c.getInt(0), // ID de la bandera.
                    c.getString(1), // Nombre de la bandera.
                    c.getString(2) // Imagen de la bandera.
                )
                res.add(nueva)
            }
        } finally {
            c?.close() // Cerrar el cursor al finalizar.
        }
        return res // Devolver la lista de banderas.
    }

    /*
     * Método para actualizar un registro de bandera en la base de datos.
     * @param context Contexto de la aplicación.
     * @param bandera Objeto Bandera con los datos a actualizar.
     */
    fun actualizarBBDD(context: Context?, bandera: Bandera) {
        val db = DBOpenHelper.getInstance(context)!!.writableDatabase // Obtener una instancia escribible de la base de datos.
        // Ejecutar SQL para actualizar el registro de la bandera.
        db.execSQL(
            "UPDATE comunidades " +
                    "SET nombre='${bandera.nombre}', imagen='${bandera.imagen}' " +
                    "WHERE id=${bandera.id};"
        )
        val values = ContentValues() // Contenedor para valores de columna.
        // Asignar valores a actualizar.
        values.put(BanderaContract.Companion.Entrada.COLUMNA_ID, bandera.id)
        values.put(BanderaContract.Companion.Entrada.COLUMNA_NOMBRE, bandera.nombre)
        values.put(BanderaContract.Companion.Entrada.COLUMNA_IMAGEN, bandera.imagen)
        // Actualizar el registro utilizando ContentValues.
        db.update(
            BanderaContract.Companion.Entrada.NOMBRE_TABLA,
            values,
            "id=?",
            arrayOf(bandera.id.toString())
        )
        db.close() // Cerrar la base de datos.
    }

    /*
     * Método para eliminar un registro de bandera en la base de datos.
     * @param context Contexto de la aplicación.
     * @param bandera Objeto Bandera a eliminar.
     */
    fun eliminarComunidad(context: Context, bandera: Bandera) {
        val db = DBOpenHelper.getInstance(context)!!.writableDatabase // Obtener una instancia escribible de la base de datos.
        // Eliminar el registro de la bandera.
        db.delete(
            BanderaContract.Companion.Entrada.NOMBRE_TABLA,
            "${BanderaContract.Companion.Entrada.COLUMNA_ID}=?",
            arrayOf(bandera.id.toString())
        )
        db.close() // Cerrar la base de datos.
    }
}
