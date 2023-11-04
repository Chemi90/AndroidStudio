package com.example.banderasrecyclerviewunidad5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide

class MainActivityDos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_dos)
        var nombre=intent.getStringExtra("nombre")
        var posicion=intent.getIntExtra("posicion", 0)
        var imagen = intent.getStringExtra("imagen")
        val etNombre = findViewById<EditText>(R.id.etNombre)
        val imgBandera = findViewById<ImageView>(R.id.imgBandera)
        Glide.with(imgBandera.context).load(imagen).into(imgBandera)
        etNombre.hint=nombre
        val btnCambiar = findViewById<Button>(R.id.btnCambiar)
        val btnCancelar = findViewById<Button>(R.id.btnCancelar)
        btnCambiar.setOnClickListener{
            val intent= Intent()
            intent.putExtra("posicion", posicion)
            intent.putExtra("nombre", etNombre.text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }
        btnCancelar.setOnClickListener {
            this.finish()
        }
    }
}