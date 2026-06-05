package com.example.tugas2mobile

import com.example.tugas2mobile.R

object DummyData {
    val studios = listOf(
        Studio("Studio A (Standard)", 50000),
        Studio("Studio B (Recording)", 75000),
        Studio("VIP Studio (Full Set)", 150000)
    )

    val instruments = listOf(
        Instrument("Gitar Elektrik Fender", 40000, 5),
        Instrument("Gitar Akustik Yamaha", 20000, 8),
        Instrument("Drum Set Pearl", 80000, 3),
        Instrument("Mic Shure SM58", 15000, 5),
        Instrument("Bass Ibanez", 35000, 5)
    )

    fun studioFeature(name: String) = when {
        name.contains("Studio A") -> "Fitur: 1 Gitar, 1 Drum, 1 Mic"
        name.contains("Studio B") -> "Fitur: 2 Gitar, 1 Drum, Set Recording"
        name.contains("VIP") -> "Fitur: Alat Lengkap + AC + Sound Engineer"
        else -> "Fitur standar"
    }

    fun studioImage(name: String) = when {
        name.contains("Studio A") -> R.drawable.studio_a
        name.contains("Studio B") -> R.drawable.studio_b
        name.contains("VIP") -> R.drawable.studio_vip
        else -> R.mipmap.ic_launcher
    }

    fun instrumentImage(name: String) = when {
        name.contains("Elektrik") -> R.drawable.gitar_elektrik
        name.contains("Akustik") -> R.drawable.gitar_akustik
        name.contains("Drum") -> R.drawable.drum
        name.contains("Mic") -> R.drawable.mic
        name.contains("Bass") -> R.drawable.bass
        else -> R.mipmap.ic_launcher
    }
}
