package com.example.tugas2mobile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tugas2mobile.databinding.ItemCardBinding

data class CardUi(
    val name: String,
    val price: Int,
    val desc: String,
    val imageRes: Int,
    val onAdd: () -> Unit
)

class CardAdapter(var items: List<CardUi>) : RecyclerView.Adapter<CardAdapter.VH>() {

    class VH(val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.binding.imgCard.setImageResource(item.imageRes)
        holder.binding.txtName.text = item.name
        holder.binding.txtDesc.text = item.desc
        holder.binding.txtPrice.text = "Rp " + item.price
        holder.binding.btnAdd.setOnClickListener { item.onAdd() }
    }

    override fun getItemCount(): Int = items.size

    fun submit(list: List<CardUi>) {
        items = list
        notifyDataSetChanged()
    }
}
