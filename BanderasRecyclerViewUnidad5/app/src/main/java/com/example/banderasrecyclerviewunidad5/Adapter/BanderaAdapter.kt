package com.example.banderasrecyclerviewunidad5.Adapter

import android.content.DialogInterface.OnClickListener
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banderasrecyclerviewunidad5.Bandera
import com.example.banderasrecyclerviewunidad5.R

/*
 * Clase BanderaAdapter, un adaptador para RecyclerView que maneja una lista de objetos Bandera.
 * Este adaptador define cómo se crean y se vinculan los ViewHolder para cada ítem en la lista.
 * @param banderaList Lista mutable de objetos Bandera que el adaptador manejará.
 * @param onClickListener Función lambda que se ejecutará cuando se haga clic en un ítem.
 */
class BanderaAdapter(private val banderaList: MutableList<Bandera>, private var onClickListener: (Bandera) -> Unit) : RecyclerView.Adapter<BanderaViewHolder>(){

    /*
     * Crea y devuelve un BanderaViewHolder para un ítem.
     * @param parent El ViewGroup en el que se inflará el nuevo ítem.
     * @param viewType Tipo de vista del nuevo ítem.
     * @return Un nuevo BanderaViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BanderaViewHolder {
        val LayoutInflater = LayoutInflater.from(parent.context)
        return BanderaViewHolder(LayoutInflater.inflate(R.layout.item_bandera, parent, false))
    }

    /*
     * Retorna el tamaño de la lista de banderas.
     * @return Número total de ítems en la lista.
     */
    override fun getItemCount(): Int {
        return banderaList.size
    }

    /*
     * Vincula un objeto Bandera a un BanderaViewHolder.
     * @param holder El BanderaViewHolder que se va a actualizar.
     * @param position La posición del ítem en la lista.
     */
    override fun onBindViewHolder(holder: BanderaViewHolder, position: Int) {
        val item = banderaList[position] // Obtiene el objeto Bandera en la posición dada.
        holder.render(item, onClickListener ) // Actualiza el ViewHolder con el objeto Bandera.
    }

    /*
     * Actualiza los datos del adaptador con una nueva lista de banderas y notifica cualquier cambio.
     * @param newData La nueva lista de objetos Bandera.
     */
    fun updateData(newData: List<Bandera>) {
        banderaList.clear() // Limpia la lista actual.
        banderaList.addAll(newData) // Agrega los nuevos datos a la lista.
        notifyDataSetChanged() // Notifica al RecyclerView que los datos han cambiado.
    }
}
