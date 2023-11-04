package com.example.banderasrecyclerviewunidad5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banderasrecyclerviewunidad5.Adapter.BanderaAdapter
import com.example.banderasrecyclerviewunidad5.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var banderaDAO: BanderaDAO
    private lateinit var listaBandera: MutableList<Bandera>
    private lateinit var adapter: BanderaAdapter
    private lateinit var intentLaunch: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        banderaDAO = BanderaDAO()
        listaBandera = banderaDAO.cargarLista(this)
        initRecyclerView()
        recargar()

        // Usando BanderaProvider.banderaList para inicializar
        adapter = BanderaAdapter(listaBandera) { bandera ->
            onItemSelected(bandera.nombre)
        }
        binding.rvBanderas.adapter = adapter // Cambio aquí
        invalidateOptionsMenu()

        intentLaunch=registerForActivityResult(
            ActivityResultContracts.StartActivityForResult())
        {
            result:ActivityResult ->
            if(result.resultCode== RESULT_OK){
                val posicion = result.data?.extras?.getInt("posicion", 0)
                listaBandera[posicion!!].nombre=result.data?.extras?.getString("nombre").toString()
                adapter.notifyItemChanged(posicion)
                binding.rvBanderas.adapter = BanderaAdapter(listaBandera) { bandera ->
                    onItemSelected(bandera.nombre)
                }
            }
        }


    }

    private fun onItemSelected(nombre: String) {
        Toast.makeText(this, "Yo soy de ${nombre}.", Toast.LENGTH_SHORT).show()
    }

    private fun initRecyclerView() {
        val manager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(this, manager.orientation)
        binding.rvBanderas.layoutManager = manager
        binding.rvBanderas.adapter = BanderaAdapter(BanderaProvider.banderaList.toMutableList()) { bandera ->
            onItemSelected(bandera.nombre)
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
                listaBandera.clear()
                adapter.notifyItemRangeChanged(0,listaBandera.size)
                binding.rvBanderas.adapter = BanderaAdapter(listaBandera) { bandera ->
                    onItemSelected(bandera.nombre)
                }
                return true
            }

            R.id.action_reload -> {
                // Recargar la lista (asumiendo que tienes una lista original)
                recargar()
                adapter.notifyItemRangeChanged(0,listaBandera.size)
                binding.rvBanderas.adapter = BanderaAdapter(listaBandera) { bandera ->
                    onItemSelected(bandera.nombre)
                }
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun recargar() {
        listaBandera = mutableListOf()
        for (bandera: Bandera in BanderaProvider.banderaList) {
            listaBandera.add(bandera)
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
                            onItemSelected(bandera.nombre)
                        }
                    }.create()
                alert.show()
            }
            1 -> {
                val intent = Intent(this, MainActivityDos::class.java)
                intent.putExtra("nombre", banderaAfectada.nombre)
                intent.putExtra("imagen", banderaAfectada.imagen)
                intent.putExtra("posicion", item.groupId)
                intentLaunch.launch(intent)
            }
        }
        return true
    }

    private fun display(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}