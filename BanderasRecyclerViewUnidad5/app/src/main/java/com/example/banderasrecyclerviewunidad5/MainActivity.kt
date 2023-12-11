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

/*
 * Clase MainActivity, que extiende AppCompatActivity.
 * Esta clase es la actividad principal de la aplicación y se encarga de manejar la interfaz de usuario y la interacción con el usuario.
 */
class MainActivity : AppCompatActivity() {

    // Variables para el binding, acceso a datos, la lista de banderas, el adaptador del RecyclerView y el lanzador de actividad.
    private lateinit var binding: ActivityMainBinding
    private lateinit var banderaDAO: BanderaDAO
    private lateinit var listaBandera: MutableList<Bandera>
    private lateinit var adapter: BanderaAdapter
    private lateinit var intentLaunch: ActivityResultLauncher<Intent>

    /*
     * Método onCreate que se llama cuando se crea la actividad.
     * Aquí se inicializa la interfaz de usuario y se configuran los componentes.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflar la vista usando el binding y establecer el contenido de la actividad.
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar el DAO, cargar la lista de banderas y configurar el RecyclerView.
        banderaDAO = BanderaDAO()
        listaBandera = banderaDAO.cargarLista(this)
        initRecyclerView()
        recargar()

        // Configurar el adaptador del RecyclerView con un listener para los elementos seleccionados.
        adapter = BanderaAdapter(listaBandera) { bandera ->
            onItemSelected(bandera.nombre)
        }
        binding.rvBanderas.adapter = adapter
        invalidateOptionsMenu()

        // Registrar un lanzador de actividad para obtener resultados de otra actividad.
        intentLaunch = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            // Procesar el resultado de la actividad lanzada.
            if (result.resultCode == RESULT_OK) {
                // Obtener los datos devueltos por la actividad y actualizar la lista y el adaptador.
                val posicion = result.data?.extras?.getInt("posicion", 0) ?: 0
                listaBandera[posicion].nombre = result.data?.extras?.getString("nombre").toString()
                adapter.notifyItemChanged(posicion)
                binding.rvBanderas.adapter = adapter
            }
        }
    }

    /*
     * Método para manejar la selección de un elemento en la lista.
     * Muestra un Toast con el nombre de la bandera seleccionada.
     * @param nombre El nombre de la bandera seleccionada.
     */
    private fun onItemSelected(nombre: String) {
        Toast.makeText(this, "Yo soy de $nombre.", Toast.LENGTH_SHORT).show()
    }

    /*
     * Método para inicializar el RecyclerView.
     * Configura el LayoutManager, el ItemDecoration y el adaptador.
     */
    private fun initRecyclerView() {
        // Configurar el LayoutManager y el ItemDecoration para el RecyclerView.
        val manager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(this, manager.orientation)
        binding.rvBanderas.layoutManager = manager
        binding.rvBanderas.addItemDecoration(decoration)

        // Configurar el adaptador del RecyclerView.
        binding.rvBanderas.adapter = BanderaAdapter(BanderaProvider.banderaList.toMutableList()) { bandera ->
            onItemSelected(bandera.nombre)
        }
    }

    /*
     * Método para inflar el menú de opciones.
     * @param menu El menú de opciones.
     * @return Boolean Indicando si el menú se ha inflado correctamente.
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflar el menú desde el recurso XML.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    /*
     * Método para manejar la selección de ítems en el menú de opciones.
     * @param item El ítem del menú seleccionado.
     * @return Boolean Indicando si la selección se ha manejado correctamente.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Procesar la selección de ítems del menú de opciones.
        when (item.itemId) {
            R.id.action_clear -> {
                // Limpiar la lista y notificar al adaptador.
                listaBandera.clear()
                adapter.notifyDataSetChanged()
                return true
            }

            R.id.action_reload -> {
                // Recargar la lista y notificar al adaptador.
                recargar()
                adapter.notifyDataSetChanged()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    /*
     * Método para recargar la lista de banderas.
     * Utiliza la lista proporcionada por BanderaProvider.
     */
    private fun recargar() {
        // Llenar la listaBandera con los datos de BanderaProvider.
        listaBandera.clear()
        listaBandera.addAll(BanderaProvider.banderaList)
    }

    /*
     * Método para manejar la selección de ítems en el menú contextual.
     * @param item El ítem del menú contextual seleccionado.
     * @return Boolean Indicando si la selección se ha manejado correctamente.
     */
    override fun onContextItemSelected(item: MenuItem): Boolean {
        // Procesar la selección de ítems del menú contextual.
        lateinit var banderaAfectada: Bandera
        lateinit var miIntent: Intent
        banderaAfectada = listaBandera[item.groupId]
        when (item.itemId) {
            0 -> {
                // Crear y mostrar un diálogo de alerta para confirmar la eliminación.
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
                // Lanzar una nueva actividad para editar la bandera seleccionada.
                val intent = Intent(this, MainActivityDos::class.java)
                intent.putExtra("nombre", banderaAfectada.nombre)
                intent.putExtra("imagen", banderaAfectada.imagen)
                intent.putExtra("posicion", item.groupId)
                intentLaunch.launch(intent)
            }
        }
        return true
    }

    /*
     * Método para mostrar un mensaje en pantalla usando Snackbar.
     * @param message El mensaje a mostrar.
     */
    private fun display(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}