package com.example.tugas2mobile

object CartManager {
    val booking = Booking(null, 0)

    fun setStudio(s: Studio) {
        booking.studio = s
    }

    fun getExternalUsage(): Int = (0..2).random()

    fun tryAddInstrument(i: Instrument): Boolean {
        val used = booking.getQty(i) + getExternalUsage()
        if (used >= i.stock) return false
        booking.addInstrument(i)
        return true
    }

    fun removeStudio() {
        booking.studio = null
    }

    fun removeInstrument(i: Instrument) {
        booking.removeInstrument(i)
    }

    fun setHours(h: Int) {
        booking.hours = h
    }

    fun total() = booking.getTotal()

    fun itemCount(): Int {
        var c = 0
        if (booking.studio != null) c++
        for ((_, q) in booking.instruments) c += q
        return c
    }

    fun isEmpty() = booking.isEmpty()

    fun clear() {
        booking.studio = null
        booking.instruments.clear()
        booking.hours = 0
    }
}
