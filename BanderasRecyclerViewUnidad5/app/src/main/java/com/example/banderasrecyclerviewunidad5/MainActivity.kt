package com.example.banderasrecyclerviewunidad5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banderasrecyclerviewunidad5.Adapter.BanderaAdapter
import com.example.banderasrecyclerviewunidad5.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var banderaAdapter: BanderaAdapter
    private var originalDataList: List<Bandera> = BanderaProvider.banderaList
    private lateinit var listaBandera: MutableList<Bandera>
    private lateinit var adapter: BanderaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        // Usando BanderaProvider.banderaList para inicializar
        banderaAdapter = BanderaAdapter(BanderaProvider.banderaList.toMutableList()) { bandera ->
            onContextItemSelected(bandera as MenuItem)
        }
        binding.rvBanderas.adapter = banderaAdapter // Cambio aquí
        invalidateOptionsMenu()
    }

    private fun initRecyclerView() {
        val manager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(this, manager.orientation)
        binding.rvBanderas.layoutManager = manager
        binding.rvBanderas.adapter = BanderaAdapter(BanderaProvider.banderaList.toMutableList()) { bandera ->
            onContextItemSelected(bandera as MenuItem)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_clear -> {
                // Limpiar la lista (vaciar) O cualquier otra forma de obtener una lista vacía
                val emptyList: List<Bandera> = emptyList()
                banderaAdapter.updateData(emptyList)
                return true
            }

            R.id.action_reload -> {
                // Recargar la lista (asumiendo que tienes una lista original)
                banderaAdapter.updateData(originalDataList)
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        lateinit var banderaAfectada: Bandera
        lateinit var miIntent: Intent
        banderaAfectada = listaBandera[item.groupId]
        when (item.itemId) {
            0 -> {
                val alert =
                    AlertDialog.Builder(this).setTitle("Eliminar ${banderaAfectada.nombre}")
                        .setMessage("Estás seguro que quieres eliminar ${banderaAfectada.nombre}?")
                        .setNeutralButton("Cerrar", null).setPositiveButton("Aceptar") { _, _ ->
                        display("Se ha eliminado ${banderaAfectada.nombre}")
                        listaBandera.removeAt(item.groupId)
                        adapter.notifyItemRemoved(item.groupId)
                        adapter.notifyItemRangeChanged(item.groupId, listaBandera.size)
                        binding.rvBanderas.adapter = BanderaAdapter(listaBandera) { bandera ->
                            onContextItemSelected(item)
                        }
                    }.create()
                alert.show()
            }

            else -> return super.onContextItemSelected(item)
        }
        return true
    }

    private fun display(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}