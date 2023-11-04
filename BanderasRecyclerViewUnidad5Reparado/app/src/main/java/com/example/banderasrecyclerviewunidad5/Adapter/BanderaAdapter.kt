package com.example.banderasrecyclerviewunidad5.Adapter

import android.content.DialogInterface.OnClickListener
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.banderasrecyclerviewunidad5.Bandera
import com.example.banderasrecyclerviewunidad5.R

class BanderaAdapter(private val banderaList: MutableList<Bandera>, private var onClickListener: (Bandera) -> Unit) : RecyclerView.Adapter<BanderaViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BanderaViewHolder {
        val LayoutInflater = LayoutInflater.from(parent.context)
        return BanderaViewHolder(LayoutInflater.inflate(R.layout.item_bandera, parent, false))
    }

    override fun getItemCount(): Int {
        return banderaList.size
    }

    override fun onBindViewHolder(holder: BanderaViewHolder, position: Int) {
        val item = banderaList[position]
        holder.render(item, onClickListener )
    }

    fun updateData(newData: List<Bandera>) {
        banderaList.clear()
        banderaList.addAll(newData)
        notifyDataSetChanged()
    }
}
