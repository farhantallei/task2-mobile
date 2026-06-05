package com.example.tugas2mobile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tugas2mobile.databinding.ItemCartBinding

data class CartLine(val text: String, val onDelete: () -> Unit)

class CartAdapter(var items: List<CartLine>) : RecyclerView.Adapter<CartAdapter.VH>() {

    inner class VH(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemCartBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val line = items[position]
        holder.binding.txtCartItem.text = line.text
        holder.binding.btnDelete.setOnClickListener { line.onDelete() }
    }

    override fun getItemCount(): Int = items.size

    fun submit(list: List<CartLine>) {
        items = list
        notifyDataSetChanged()
    }
}
