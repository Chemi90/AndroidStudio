package com.example.banderasrecyclerviewunidad5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banderasrecyclerviewunidad5.Adapter.BanderaAdapter
import com.example.banderasrecyclerviewunidad5.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var banderaAdapter: BanderaAdapter
    private var originalDataList: List<Bandera> = BanderaProvider.banderaList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        banderaAdapter = BanderaAdapter(BanderaProvider.banderaList.toMutableList()) // Usando BanderaProvider.banderaList para inicializar
        binding.rvBanderas.adapter = banderaAdapter // Cambio aquí
        invalidateOptionsMenu()
    }

    private fun initRecyclerView(){
        val manager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(this, manager.orientation)
        binding.rvBanderas.layoutManager = manager
        binding.rvBanderas.adapter = BanderaAdapter(BanderaProvider.banderaList.toMutableList())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_clear -> {
                // Limpiar la lista (vaciar)
                val emptyList: List<Bandera> = emptyList() // O cualquier otra forma de obtener una lista vacía
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
}