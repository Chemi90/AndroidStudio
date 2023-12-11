package com.example.banderasrecyclerviewunidad5.Adapter

import android.view.ContextMenu
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.banderasrecyclerviewunidad5.Bandera
import com.example.banderasrecyclerviewunidad5.databinding.ItemBanderaBinding

/*
 * Clase BanderaViewHolder, diseñada para ser usada con un RecyclerView.
 * Implementa View.OnCreateContextMenuListener para manejar menús contextuales.
 */
class BanderaViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {

    /*
     * binding se utiliza para acceder y manipular las vistas definidas en el layout item_bandera.xml.
     */
    private val binding = ItemBanderaBinding.bind(view)
    /*
     * bandera es una variable que almacenará un objeto Bandera. Se inicializa más tarde.
     */
    private lateinit var bandera: Bandera

    /*
     * La función render se llama para actualizar los contenidos de la vista con un objeto Bandera.
     * Recibe un objeto Bandera y un lambda onClickListener que se invoca al hacer clic en el elemento.
     */
    fun render(item: Bandera, onClickListener: (Bandera)->Unit) {
        bandera=item
        binding.tvBandera.text = item.nombre
        //Usa Glide para cargar una imagen en un ImageView. Glide es una biblioteca para la gestión de imágenes.
        Glide.with(binding.ivBandera.context).load(item.imagen).into(binding.ivBandera)

        //Configura un onClickListener para el itemView. Al hacer clic, se invoca el lambda con el objeto Bandera.
        itemView.setOnClickListener {
            onClickListener(item)
        }
        // Establece el listener de menú contextual para el itemView.
        itemView.setOnCreateContextMenuListener(this)
    }

    /*
     * Sobrescribe el método onCreateContextMenu para configurar el menú contextual.
     */
    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menu!!.setHeaderTitle(bandera.nombre)
        menu.add(this.adapterPosition,0,0,"Eliminar")
        menu.add(this.adapterPosition,1,1,"Editar")
    }
}