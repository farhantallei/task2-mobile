package com.example.tugas2mobile

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tugas2mobile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private enum class Mode { STUDIO, ALAT }

    private var mode: Mode = Mode.STUDIO
    private var keyword: String = ""

    private val adapter = CardAdapter(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.contentFrame) { _, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.toolbar.updatePadding(top = systemBars.top)
            val params = binding.fabCart.layoutParams as android.view.ViewGroup.MarginLayoutParams
            val margin = (16 * resources.displayMetrics.density).toInt()
            params.bottomMargin = margin + systemBars.bottom
            binding.fabCart.layoutParams = params
            insets
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Menu Studio"

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.app_name,
            R.string.app_name
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        // Putihin ikon hamburger ☰ biar kelihatan di atas toolbar coklat
        toggle.drawerArrowDrawable.color = getColor(R.color.white)

        binding.recyclerCards.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerCards.adapter = adapter

        binding.searchEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                keyword = s?.toString()?.lowercase() ?: ""
                refresh()
            }
        })

        binding.navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_studio -> {
                    SoundPlayer.click()
                    mode = Mode.STUDIO
                    supportActionBar?.title = "Menu Studio"
                    refresh()
                }
                R.id.nav_alat -> {
                    SoundPlayer.click()
                    mode = Mode.ALAT
                    supportActionBar?.title = "Menu Tambahan Alat Musik"
                    refresh()
                }
                R.id.nav_history -> {
                    SoundPlayer.click()
                    startActivity(Intent(this, HistoryActivity::class.java))
                }
            }
            binding.drawerLayout.closeDrawers()
            true
        }

        binding.fabCart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        refresh()
    }

    private fun refresh() {
        val list: List<CardUi> = when (mode) {
            Mode.STUDIO -> DummyData.studios
                .filter { it.name.lowercase().contains(keyword) }
                .map { s ->
                    CardUi(
                        s.name,
                        s.pricePerHour,
                        DummyData.studioFeature(s.name),
                        DummyData.studioImage(s.name)
                    ) {
                        SoundPlayer.click()
                        CartManager.setStudio(s)
                        Toast.makeText(this, "Studio dipilih: " + s.name, Toast.LENGTH_SHORT).show()
                        updateFab()
                    }
                }
            Mode.ALAT -> DummyData.instruments
                .filter { it.name.lowercase().contains(keyword) }
                .map { i ->
                    CardUi(
                        i.name,
                        i.price,
                        "Satuan",
                        DummyData.instrumentImage(i.name)
                    ) {
                        if (CartManager.tryAddInstrument(i)) {
                            SoundPlayer.click()
                            Toast.makeText(this, i.name + " ditambahkan", Toast.LENGTH_SHORT).show()
                            updateFab()
                        } else {
                            Toast.makeText(this, "Stok " + i.name + " tidak cukup!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }
        adapter.submit(list)
    }

    override fun onResume() {
        super.onResume()
        updateFab()
    }

    private fun updateFab() {
        binding.fabCart.text = "Keranjang • " + CartManager.itemCount()
    }
}
