package com.example.banderasrecyclerviewunidad5

import android.provider.BaseColumns

/*
 * Clase BanderaContract, que define la estructura de la base de datos para la tabla de banderas.
 * Esta clase es un contenedor para constantes que definen los nombres de tablas y columnas.
 */
class BanderaContract {
    /*
     * Companion object para definir constantes accesibles a nivel de clase.
     */
    companion object {
        // Nombre de la base de datos.
        const val NOMBRE_BD = "comunidades"

        // Versión de la base de datos.
        const val VERSION = 1

        /*
         * Clase anidada Entrada que implementa BaseColumns.
         * BaseColumns es una interfaz de Android que facilita el uso de ID como clave primaria.
         */
        class Entrada : BaseColumns {
            /*
             * Companion object para definir constantes relacionadas con la tabla de banderas.
             */
            companion object {
                // Nombre de la tabla.
                const val NOMBRE_TABLA = "comunidades"

                // Nombre de la columna para ID.
                const val COLUMNA_ID = "id"

                // Nombre de la columna para el nombre de la bandera.
                const val COLUMNA_NOMBRE = "nombre"

                // Nombre de la columna para la imagen de la bandera.
                const val COLUMNA_IMAGEN = "imagen"
            }
        }
    }
}
