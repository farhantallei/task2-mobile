package com.example.tugas2mobile

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugas2mobile.databinding.ActivityCartBinding
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.math.max

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var adapter: CartAdapter

    private var duration = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        duration = max(1, CartManager.booking.hours.takeIf { it > 0 } ?: 1)
        CartManager.setHours(duration)

        // Apply top inset to toolbar
        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbarCart) { v, insets ->
            val top = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top
            v.updatePadding(top = top)
            insets
        }

        // Toolbar / up navigation
        setSupportActionBar(binding.toolbarCart)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Spinners
        setupDateSpinner()
        updateTimeSpinner()

        // Duration display
        binding.txtDuration.text = duration.toString()

        // Recycler
        adapter = CartAdapter(emptyList())
        binding.recyclerCart.layoutManager = LinearLayoutManager(this)
        binding.recyclerCart.adapter = adapter

        refreshCart()

        // Listeners
        binding.btnDurMinus.setOnClickListener {
            if (duration > 1) {
                duration--
                applyDuration()
            }
        }
        binding.btnDurPlus.setOnClickListener {
            if (duration < 24) {
                duration++
                applyDuration()
            }
        }
        binding.btnCheckout.setOnClickListener { prosesCheckout() }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun setupDateSpinner() {
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
        val dates = (0..6).map { LocalDate.now().plusDays(it.toLong()).format(formatter) }
        val dateAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dates)
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerDate.adapter = dateAdapter
        binding.spinnerDate.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateTimeSpinner()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun updateTimeSpinner() {
        var startHour = 9
        val endHour = 22
        if (binding.spinnerDate.selectedItemPosition == 0) {
            val currentHour = LocalTime.now().hour
            if (currentHour > startHour) startHour = currentHour
        }
        val list: List<String> = if (startHour > endHour) {
            listOf("Tutup")
        } else {
            (startHour..endHour).map { String.format("%02d:00", it) }
        }
        val timeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, list)
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTime.adapter = timeAdapter
    }

    private fun applyDuration() {
        binding.txtDuration.text = duration.toString()
        CartManager.setHours(duration)
        refreshCart()
    }

    private fun refreshCart() {
        val booking = CartManager.booking
        val lines = mutableListOf<CartLine>()

        booking.studio?.let { studio ->
            lines.add(CartLine("🏢 " + studio.name + " (" + duration + " Jam)") {
                SoundPlayer.click()
                CartManager.removeStudio()
                refreshCart()
            })
        }
        for ((i, q) in booking.instruments) {
            lines.add(CartLine("🎸 " + i.name + " x" + q) {
                SoundPlayer.click()
                CartManager.removeInstrument(i)
                refreshCart()
            })
        }

        binding.txtCartEmpty.visibility = if (CartManager.isEmpty()) View.VISIBLE else View.GONE

        adapter.submit(lines)
        updateTotal()
    }

    private fun updateTotal() {
        CartManager.setHours(duration)
        binding.txtTotal.text = "Total: Rp " + CartManager.total()
    }

    private fun prosesCheckout() {
        if (CartManager.isEmpty()) {
            Toast.makeText(this, "Keranjang kosong!", Toast.LENGTH_SHORT).show()
            return
        }

        val tanggal = binding.spinnerDate.selectedItem as String
        val jamMulai = binding.spinnerTime.selectedItem as String?
        if (jamMulai == null || jamMulai == "Tutup") {
            Toast.makeText(
                this,
                "Studio sudah tutup pada tanggal ini, pilih jam/tanggal lain",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        val db = DbHelper(this)
        val studio = CartManager.booking.studio

        if (studio != null) {
            val startInt = jamMulai.split(":")[0].toInt()
            for (i in 0 until duration) {
                val jam = String.format("%02d:00", startInt + i)
                if (db.countSlot(studio.name, tanggal, jam) > 0) {
                    AlertDialog.Builder(this)
                        .setTitle("Jadwal Bentrok!")
                        .setMessage(studio.name + " sudah disewa pada " + tanggal + " jam " + jam)
                        .setPositiveButton("OK", null)
                        .show()
                    return
                }
            }
            for (i in 0 until duration) {
                val jam = String.format("%02d:00", startInt + i)
                db.insertSlot(studio.name, tanggal, jam)
            }
        }

        val namaItem = studio?.name ?: "Hanya Sewa Alat"
        val total = CartManager.total()
        db.insertBooking(
            BookingRecord(
                timestampPesan = LocalDate.now().toString(),
                namaItem = namaItem,
                tanggalSewa = tanggal,
                jamMulai = jamMulai,
                durasi = duration,
                total = total
            )
        )

        SoundPlayer.success(this)

        AlertDialog.Builder(this)
            .setTitle("Pesanan Berhasil!")
            .setMessage("Total: Rp " + total)
            .setPositiveButton("OK", null)
            .show()

        CartManager.clear()
        duration = 1
        binding.txtDuration.text = "1"
        refreshCart()
    }
}
