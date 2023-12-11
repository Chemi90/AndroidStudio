package com.example.banderasrecyclerviewunidad5.DataBase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.banderasrecyclerviewunidad5.Bandera
import com.example.banderasrecyclerviewunidad5.BanderaContract
import com.example.banderasrecyclerviewunidad5.R

/*
 * Clase DBOpenHelper para manejar la base de datos SQLite de banderas.
 * Hereda de SQLiteOpenHelper y proporciona métodos para crear, actualizar y manejar la base de datos.
 * @param context Contexto de la aplicación.
 */
class DBOpenHelper private constructor(context: Context?) : SQLiteOpenHelper(context, BanderaContract.NOMBRE_BD, null, BanderaContract.VERSION) {

    /*
     * Método llamado cuando se crea la base de datos por primera vez.
     * @param sqLiteDatabase La base de datos.
     */
    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        try {
            // Creación de la tabla de banderas en la base de datos.
            sqLiteDatabase.execSQL(
                "CREATE TABLE ${BanderaContract.Companion.Entrada.NOMBRE_TABLA}"
                        + "(${BanderaContract.Companion.Entrada.COLUMNA_ID} int NOT NULL"
                        + ",${BanderaContract.Companion.Entrada.COLUMNA_NOMBRE} NVARCHAR(20) NOT NULL"
                        + ",${BanderaContract.Companion.Entrada.COLUMNA_IMAGEN} int NOT NULL);"
            )
            inicializarBBDD(sqLiteDatabase) // Llamada a método para inicializar la base de datos con datos predefinidos.
        } catch (e: Exception) {
            e.printStackTrace() // Imprime la pila de errores en caso de excepción.
        }
    }

    /*
     * Método llamado cuando se actualiza la base de datos.
     * @param sqLiteDatabase La base de datos.
     * @param i Versión antigua de la base de datos.
     * @param i1 Nueva versión de la base de datos.
     */
    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
        // Elimina la tabla si existe y crea una nueva.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS ${BanderaContract.Companion.Entrada.NOMBRE_TABLA};")
        onCreate(sqLiteDatabase)
    }

    /*
     * Método privado para inicializar la base de datos con un conjunto de banderas predefinidas.
     * @param db La base de datos SQLite.
     */
    private fun inicializarBBDD(db: SQLiteDatabase) {
        val lista = cargarBanderas() // Carga la lista de banderas predefinidas.
        for (bandera in lista) {
            // Inserta cada bandera en la base de datos.
            db.execSQL(
                ("INSERT INTO ${BanderaContract.Companion.Entrada.NOMBRE_TABLA}(" +
                        "${BanderaContract.Companion.Entrada.COLUMNA_ID}," +
                        "${BanderaContract.Companion.Entrada.COLUMNA_NOMBRE}," +
                        "${BanderaContract.Companion.Entrada.COLUMNA_IMAGEN})" +
                        " VALUES (${bandera.id},'${bandera.nombre}',${bandera.imagen});")
            )
        }
    }

    /*
     * Método privado para cargar una lista de banderas predefinidas.
     * @return MutableList<Bandera> Lista de objetos Bandera.
     */
    private fun cargarBanderas(): MutableList<Bandera> {
        // Lista de banderas con datos de ejemplo.
        return mutableListOf(
            Bandera(0,"Andalucía", "https://www.shutterstock.com/image-vector/flag-andalusia-brush-strokes-autonomous-260nw-2266268593.jpg"),
            Bandera(1,"Aragón", "https://www.shutterstock.com/image-vector/flag-aragon-brush-strokes-autonomous-260nw-2284490043.jpg"),
            Bandera(2,"Asturias", "https://www.shutterstock.com/image-vector/flag-asturias-brush-strokes-autonomous-260nw-2199965061.jpg"),
            Bandera(3,"Cantabria", "https://www.shutterstock.com/image-vector/flag-cantabria-brush-strokes-autonomous-260nw-2201097103.jpg"),
            Bandera(4,"Castilla y León", "https://www.shutterstock.com/image-vector/flag-castile-leon-brush-strokes-260nw-2288530651.jpg"),
            Bandera(6,"Castillas-La Mancha", "https://www.shutterstock.com/image-vector/flag-mancha-brush-strokes-autonomous-260nw-2285871205.jpg"),
            Bandera(7,"Cataluña", "https://www.shutterstock.com/image-vector/flag-catalonia-autonomous-community-spain-260nw-2078148553.jpg"),
            Bandera(8,"Ceuta", "https://www.shutterstock.com/image-vector/ceuta-national-flag-autonomous-city-260nw-1028445967.jpg"),
            Bandera(9,"Comunidad de Madrid", "https://www.shutterstock.com/image-vector/flag-madrid-brush-strokes-community-260nw-2290247609.jpg"),
            Bandera(10,"Comunidad Foral de Navarra", "https://www.shutterstock.com/image-vector/flag-navarre-brush-strokes-autonomous-260nw-2282158559.jpg"),
            Bandera(11,"Comunidad Valenciana", "https://www.shutterstock.com/image-vector/valencian-community-flag-spain-spanish-260nw-2036916737.jpg"),
            Bandera(12,"Extremadura", "https://www.shutterstock.com/image-vector/flag-extremadura-brush-strokes-autonomous-260nw-2285004337.jpg"),
            Bandera(13,"Galicia", "https://www.shutterstock.com/image-vector/galicia-region-flag-spain-spanish-260nw-2030898209.jpg"),
            Bandera(14,"Islas Baleares", "https://www.shutterstock.com/image-vector/flag-balearic-islands-brush-strokes-260nw-2294925957.jpg"),
            Bandera(15,"Islas Canarias", "https://www.shutterstock.com/image-vector/flag-canary-islands-brush-strokes-260nw-2292552451.jpg"),
            Bandera(16,"La Rioja", "https://www.shutterstock.com/image-vector/flag-la-rioja-brush-strokes-260nw-2286902015.jpg"),
            Bandera(17,"Melilla", "https://www.shutterstock.com/image-illustration/horizontal-illustration-flag-melilla-autonomous-260nw-2336659363.jpg"),
            Bandera(18,"País Vasco", "https://www.shutterstock.com/image-vector/flag-basque-country-brush-strokes-260nw-1975957139.jpg"),
            Bandera(19,"Región de Murcia", "https://www.shutterstock.com/image-vector/flag-region-murcia-brush-strokes-260nw-2286308439.jpg")
        )
    }

    /*
     * Companion object para implementar el patrón Singleton y asegurar una única instancia de DBOpenHelper.
     */
    companion object {
        private var dbOpen: DBOpenHelper? = null

        /*
         * Método estático para obtener la instancia única de DBOpenHelper.
         * @param context Contexto de la aplicación.
         * @return DBOpenHelper? Instancia única de DBOpenHelper.
         */
        fun getInstance(context: Context?): DBOpenHelper? {
            if (dbOpen == null) dbOpen = DBOpenHelper(context)
            return dbOpen
        }
    }
}