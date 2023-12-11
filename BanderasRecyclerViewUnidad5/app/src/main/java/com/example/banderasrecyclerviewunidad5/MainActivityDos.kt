package com.example.banderasrecyclerviewunidad5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide

/*
 * Clase MainActivityDos, que extiende AppCompatActivity.
 * Esta clase representa una actividad secundaria en la aplicación, utilizada para editar o visualizar detalles de una bandera.
 */
class MainActivityDos : AppCompatActivity() {
    /*
     * Método onCreate que se llama cuando se crea la actividad.
     * Inicializa la interfaz de usuario y configura los componentes y eventos.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Establecer el contenido de la actividad desde un layout XML.
        setContentView(R.layout.activity_main_dos)

        // Recuperar datos pasados a esta actividad.
        var nombre = intent.getStringExtra("nombre") // Nombre de la bandera.
        var posicion = intent.getIntExtra("posicion", 0) // Posición de la bandera en la lista.
        var imagen = intent.getStringExtra("imagen") // URL de la imagen de la bandera.

        // Encontrar vistas en el layout.
        val etNombre = findViewById<EditText>(R.id.etNombre) // Campo de texto para el nombre.
        val imgBandera = findViewById<ImageView>(R.id.imgBandera) // Vista de imagen para la bandera.

        // Cargar la imagen de la bandera usando Glide.
        Glide.with(imgBandera.context).load(imagen).into(imgBandera)

        // Establecer el nombre actual de la bandera como sugerencia en el campo de texto.
        etNombre.hint = nombre

        // Encontrar y configurar los botones para cambiar y cancelar.
        val btnCambiar = findViewById<Button>(R.id.btnCambiar)
        val btnCancelar = findViewById<Button>(R.id.btnCancelar)

        // Configurar el listener para el botón de cambiar.
        btnCambiar.setOnClickListener {
            // Crear un intent para devolver el resultado a la actividad principal.
            val intent = Intent()
            intent.putExtra("posicion", posicion) // Pasar la posición de la bandera.
            intent.putExtra("nombre", etNombre.text.toString()) // Pasar el nuevo nombre de la bandera.
            setResult(RESULT_OK, intent) // Establecer el resultado de la actividad.
            finish() // Finalizar y cerrar la actividad.
        }

        // Configurar el listener para el botón de cancelar.
        btnCancelar.setOnClickListener {
            // Simplemente cerrar la actividad sin enviar resultados.
            this.finish()
        }
    }
}
