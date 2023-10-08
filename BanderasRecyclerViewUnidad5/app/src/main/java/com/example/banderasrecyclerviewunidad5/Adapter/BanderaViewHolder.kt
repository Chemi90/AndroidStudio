package com.example.banderasrecyclerviewunidad5.Adapter

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.banderasrecyclerviewunidad5.Bandera
import com.example.banderasrecyclerviewunidad5.databinding.ItemBanderaBinding

class BanderaViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = ItemBanderaBinding.bind(view)

    fun render(banderaModel: Bandera) {
        binding.tvBandera.text = banderaModel.nombre
        Glide.with(binding.ivBandera.context).load(banderaModel.imagen).into(binding.ivBandera)

        itemView.setOnClickListener {
            Toast.makeText(
                binding.ivBandera.context,
                "Yo soy de ${banderaModel.nombre}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}