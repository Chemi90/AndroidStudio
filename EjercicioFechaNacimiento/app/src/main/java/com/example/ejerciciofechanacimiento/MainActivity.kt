package com.example.ejerciciofechanacimiento

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


/*
clase MainActivity que hereda de AppCompatActivity() e implementa View.OnClickListener
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {

    //variables para almacenar la fecha actual
    private var mYear = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0

    //referencias para la interfaz de usuario
    private lateinit var textViewFechaActual: TextView
    private lateinit var textViewCalculo: TextView

    //handler para la actualizacion periodica
    private lateinit var handler: android.os.Handler

    //para formatear fechas
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    /*
    funcion que se ejecuta cuando se crea actividad, aqui se inicializan las variables
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //configuracion interfaz de usuario
        val boton = findViewById<Button>(R.id.BotonCalendario)
        textViewFechaActual = findViewById(R.id.textViewFechaActual)
        textViewCalculo = findViewById(R.id.textViewCalculo)

        // Actualiza la fecha cada segundo
        handler = android.os.Handler(mainLooper)
        handler.post(updateCurrentDate)

        val c = Calendar.getInstance()
        mYear = c[Calendar.YEAR]
        mMonth = c[Calendar.MONTH]
        mDay = c[Calendar.DAY_OF_MONTH]
        boton.setOnClickListener(this)
    }
    /*
    Esta funcion se le llama cuando se clica el boton
     */
    override fun onClick(v: View?) {

        // Lanzar el DatePickerDialog
        val c = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(
            this,
            object : DatePickerDialog.OnDateSetListener {

                /*
                se llama a este metodo cuando el usuario selecciona una fecha
                 */
                override fun onDateSet(
                    view: DatePicker, year: Int,
                    monthOfYear: Int, dayOfMonth: Int
                ) {
                    // Crear un objeto Calendar para la fecha seleccionada
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, monthOfYear, dayOfMonth)

                    // Crear un objeto Calendar para la fecha actual
                    val currentDate = Calendar.getInstance()

                    // Calcular la diferencia en milisegundos
                    val differenceInMillis = currentDate.timeInMillis - selectedDate.timeInMillis

                    // Calcular la diferencia en años, meses y días
                    val differenceInDays = differenceInMillis / (24 * 60 * 60 * 1000)
                    val years = differenceInDays / 365
                    val months = ((differenceInDays % 365) / 30).toInt()
                    val days = (differenceInDays % 30).toInt()

                    // Actualizar el TextView con la diferencia calculada
                    val textoCalculo = "Diferencia: $years años, $months meses, $days días"
                    textViewCalculo.text = textoCalculo
                }
            }, mYear, mMonth, mDay
        )
        datePickerDialog.show()
    }

    /*
    funcion para actualizar la fecha actual cada segundo y pone el resultado en su textView
    correspondiente
     */
    private val updateCurrentDate = object : Runnable {
        override fun run() {
            // Obtiene la fecha actual
            val currentDate = dateFormat.format(Date())

            // Actualiza el TextView
            textViewFechaActual.text = "Hoy es $currentDate"

            // Vuelve a programar la actualización en 1 segundo
            handler.postDelayed(this, 1000)
        }
    }

    /*
    funcion que se llama para parar la actualizacion de la fecha constantemente cuando ya se ha
    conseguido dicho fin
     */
    override fun onDestroy() {
        super.onDestroy()
        // Detiene la actualización cuando la actividad se destruye
        handler.removeCallbacks(updateCurrentDate)
    }
}
