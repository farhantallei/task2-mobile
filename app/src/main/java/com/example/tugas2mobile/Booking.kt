package com.example.tugas2mobile

class Booking(var studio: Studio?, var hours: Int) {
    val instruments = LinkedHashMap<Instrument, Int>()

    fun addInstrument(i: Instrument) {
        instruments[i] = (instruments[i] ?: 0) + 1
    }

    fun removeInstrument(i: Instrument) {
        val q = instruments[i] ?: return
        if (q > 1) instruments[i] = q - 1 else instruments.remove(i)
    }

    fun getQty(i: Instrument) = instruments[i] ?: 0

    fun getTotal(): Int {
        var t = 0
        studio?.let { t += it.pricePerHour * hours }
        for ((i, q) in instruments) t += i.price * q * hours
        return t
    }

    fun isEmpty() = studio == null && instruments.isEmpty()
}
