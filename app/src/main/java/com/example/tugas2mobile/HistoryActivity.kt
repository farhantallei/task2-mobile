package com.example.tugas2mobile

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugas2mobile.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbarHistory) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(v.paddingLeft, systemBars.top, v.paddingRight, v.paddingBottom)
            insets
        }

        setSupportActionBar(binding.toolbarHistory)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.recyclerHistory.layoutManager = LinearLayoutManager(this)

        val list = DbHelper(this).getAllBookings()
        if (list.isEmpty()) {
            binding.txtEmpty.visibility = View.VISIBLE
            binding.recyclerHistory.visibility = View.GONE
        } else {
            binding.txtEmpty.visibility = View.GONE
            binding.recyclerHistory.visibility = View.VISIBLE
            binding.recyclerHistory.adapter = HistoryAdapter(list)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
