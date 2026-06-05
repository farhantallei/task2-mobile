package com.example.tugas2mobile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tugas2mobile.databinding.ItemHistoryBinding

class HistoryAdapter(var items: List<BookingRecord>) :
    RecyclerView.Adapter<HistoryAdapter.VH>() {

    inner class VH(val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemHistoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val b = items[position]
        holder.binding.txtHistItem.text = b.namaItem
        holder.binding.txtHistSchedule.text =
            "📅 " + b.tanggalSewa + "  •  🕐 " + b.jamMulai + "  •  " + b.durasi + " jam"
        holder.binding.txtHistTotal.text = "Rp " + b.total
        holder.binding.txtHistTimestamp.text = "Dipesan: " + b.timestampPesan
    }

    override fun getItemCount(): Int = items.size

    fun submit(list: List<BookingRecord>) {
        items = list
        notifyDataSetChanged()
    }
}
