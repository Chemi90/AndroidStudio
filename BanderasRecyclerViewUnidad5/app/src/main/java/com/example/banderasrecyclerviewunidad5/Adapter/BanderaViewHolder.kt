package com.example.banderasrecyclerviewunidad5.Adapter

import android.view.ContextMenu
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.banderasrecyclerviewunidad5.Bandera
import com.example.banderasrecyclerviewunidad5.databinding.ItemBanderaBinding

class BanderaViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {

    private val binding = ItemBanderaBinding.bind(view)
    private lateinit var bandera: Bandera

    fun render(item: Bandera, onClickListener: (Bandera)->Unit) {
        bandera=item
        binding.tvBandera.text = item.nombre
        Glide.with(binding.ivBandera.context).load(item.imagen).into(binding.ivBandera)

        itemView.setOnClickListener {
            onClickListener(item)
        }
        itemView.setOnCreateContextMenuListener(this)
    }

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