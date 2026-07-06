# RAFH Studio

RAFH Studio adalah aplikasi Android untuk melakukan penyewaan studio musik dan alat musik. Pengguna dapat mencari dan memilih studio, menambahkan alat musik ke keranjang, menentukan tanggal serta durasi sewa, melihat total biaya, melakukan checkout, dan melihat riwayat penyewaan.

Aplikasi dibuat menggunakan Kotlin dan XML, dengan SQLite sebagai penyimpanan data lokal. RAFH Studio juga memiliki pemeriksaan stok alat dan validasi jadwal untuk mencegah penyewaan studio pada waktu yang sama.

## Use Case Diagram

<img src="https://github.com/user-attachments/assets/7a982d28-bf16-4fcf-84d1-d34e83f4f0c9" alt="Use Case Diagram RAFH Studio" width="700">

## Anggota Kelompok

| No. | Nama | NPM | Peran |
|---:|---|---|---|
| 1 | Adrian Zidan Ramadhan | 24552011224 | Pengembangan data dan tampilan alat musik, CardAdapter, aset, serta efek suara |
| 2 | Hafid Aliyanto | 24552011007 | Pengembangan keranjang, CartAdapter, CartManager, dan proses checkout |
| 3 | Muhamad Fadillah Suhada | 24552011018 | Pengembangan halaman dan RecyclerView riwayat penyewaan |
| 4 | Muhammad Ramdan | 24552011065 | Pengembangan model studio dan booking serta database SQLite |
| 5 | Farhan Pradana Tallei | 24552012004 | Pengembangan halaman utama, navigasi, konfigurasi project, dan integrasi fitur |

## Video Penjelasan

[![Tonton video penjelasan RAFH Studio](https://img.youtube.com/vi/rVvtkZUrYx8/0.jpg)](https://youtu.be/rVvtkZUrYx8)

[Tonton video penjelasan RAFH Studio](https://www.youtube.com/watch?v=rVvtkZUrYx8)

## Screenshot Aplikasi

### Menu Studio

<img width="575" height="1280" alt="WhatsApp Image 2026-07-07 at 00 39 48" src="https://github.com/user-attachments/assets/6b7629e2-2eec-400d-9915-c8ab1cf0d267" /> <img width="575" height="1280" alt="WhatsApp Image 2026-07-07 at 00 39 48 (1)" src="https://github.com/user-attachments/assets/91dea9c4-52d8-4111-bc7a-a22bbddfc46f" />



### Keranjang dan Jadwal Penyewaan

<img alt="WhatsApp Image 2026-07-07 at 00 29 28" src="https://github.com/user-attachments/assets/a7d20808-456b-4748-8e2f-6af715a2d9b9" width="300" />


### Riwayat Penyewaan

<img  alt="Screenshot_2026-07-07-00-30-49-218_com example tugas2mobile jpg" src="https://github.com/user-attachments/assets/1fddc683-4514-4cdc-95be-0ac5fbe26971" width="300" />



## Fitur Utama

- Daftar dan pencarian studio musik.
- Daftar dan pencarian alat musik.
- Pemeriksaan ketersediaan stok alat.
- Keranjang penyewaan.
- Pemilihan tanggal, jam mulai, dan durasi sewa.
- Perhitungan total biaya secara otomatis.
- Validasi jadwal untuk mencegah penyewaan studio yang bentrok.
- Penyimpanan transaksi menggunakan SQLite.
- Riwayat penyewaan.

## Cara Menjalankan Project

### Prasyarat

- Android Studio.
- JDK 11 atau versi yang kompatibel.
- Android SDK 36.
- Emulator atau perangkat Android dengan minimal Android 11/API 30.

### Clone dan Jalankan melalui Android Studio

1. Clone repository:

   ```bash
   git clone https://github.com/farhantallei/task2-mobile.git
   ```

2. Masuk ke direktori project:

   ```bash
   cd task2-mobile
   ```

3. Buka direktori tersebut melalui Android Studio.
4. Tunggu proses Gradle Sync dan pengunduhan dependency selesai.
5. Pilih emulator atau hubungkan perangkat Android yang sudah mengaktifkan USB debugging.
6. Tekan **Run** atau gunakan pintasan `Shift + F10`.

### Menjalankan melalui Terminal

Pada macOS atau Linux:

```bash
./gradlew installDebug
```

Pada Windows:

```powershell
gradlew.bat installDebug
```

Pastikan emulator sedang aktif atau perangkat Android sudah terhubung sebelum menjalankan perintah tersebut.
