package com.example.tugas2mobile

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/** Satu baris riwayat penyewaan (pengganti Room BookingEntity). */
data class BookingRecord(
    val id: Long = 0,
    val timestampPesan: String,
    val namaItem: String,
    val tanggalSewa: String,
    val jamMulai: String,
    val durasi: Int,
    val total: Int
)

/**
 * SQLite native (SQLiteOpenHelper) — pengganti Room.
 * Tanpa plugin/annotation processor, jadi bebas dari masalah versi KSP/Kotlin.
 * Tabel: bookings (riwayat) + schedule_slots (slot terkunci untuk anti-bentrok).
 */
class DbHelper(context: Context) :
    SQLiteOpenHelper(context.applicationContext, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE bookings (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "timestampPesan TEXT, " +
                "namaItem TEXT, " +
                "tanggalSewa TEXT, " +
                "jamMulai TEXT, " +
                "durasi INTEGER, " +
                "total INTEGER)"
        )
        db.execSQL(
            "CREATE TABLE schedule_slots (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "namaStudio TEXT, " +
                "tanggal TEXT, " +
                "jam TEXT, " +
                "UNIQUE(namaStudio, tanggal, jam))"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS bookings")
        db.execSQL("DROP TABLE IF EXISTS schedule_slots")
        onCreate(db)
    }

    fun insertBooking(b: BookingRecord): Long {
        val cv = ContentValues().apply {
            put("timestampPesan", b.timestampPesan)
            put("namaItem", b.namaItem)
            put("tanggalSewa", b.tanggalSewa)
            put("jamMulai", b.jamMulai)
            put("durasi", b.durasi)
            put("total", b.total)
        }
        return writableDatabase.insert("bookings", null, cv)
    }

    fun getAllBookings(): List<BookingRecord> {
        val list = mutableListOf<BookingRecord>()
        val c = readableDatabase.rawQuery(
            "SELECT id, timestampPesan, namaItem, tanggalSewa, jamMulai, durasi, total " +
                "FROM bookings ORDER BY id DESC",
            null
        )
        c.use {
            while (it.moveToNext()) {
                list.add(
                    BookingRecord(
                        id = it.getLong(0),
                        timestampPesan = it.getString(1),
                        namaItem = it.getString(2),
                        tanggalSewa = it.getString(3),
                        jamMulai = it.getString(4),
                        durasi = it.getInt(5),
                        total = it.getInt(6)
                    )
                )
            }
        }
        return list
    }

    /** Kunci satu slot jam. Abaikan bila bentrok dengan UNIQUE constraint. */
    fun insertSlot(namaStudio: String, tanggal: String, jam: String) {
        val cv = ContentValues().apply {
            put("namaStudio", namaStudio)
            put("tanggal", tanggal)
            put("jam", jam)
        }
        writableDatabase.insertWithOnConflict(
            "schedule_slots", null, cv, SQLiteDatabase.CONFLICT_IGNORE
        )
    }

    /** > 0 berarti slot sudah dipesan (bentrok). */
    fun countSlot(namaStudio: String, tanggal: String, jam: String): Int {
        val c = readableDatabase.rawQuery(
            "SELECT COUNT(*) FROM schedule_slots WHERE namaStudio = ? AND tanggal = ? AND jam = ?",
            arrayOf(namaStudio, tanggal, jam)
        )
        var count = 0
        c.use { if (it.moveToFirst()) count = it.getInt(0) }
        return count
    }

    companion object {
        private const val DB_NAME = "rafh_studio.db"
        private const val DB_VERSION = 1
    }
}
