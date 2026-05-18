# Sistem Manajemen Hotel Nusantara
**Praktikum Pemrograman Berorientasi Objek | IF-A | TA. 2025/2026**

---

## Struktur Folder

```
SistemHotel/
├── lib/
│   └── mysql-connector-j-x.x.x.jar       ← Letakkan JAR di sini
├── src/
│   ├── model/
│   │   ├── ItemHotel.java                 ← Kelas abstrak
│   │   ├── Kamar.java                     ← Turunan ItemHotel
│   │   ├── RuangMeeting.java              ← Turunan ItemHotel
│   │   ├── Bookable.java                  ← Interface
│   │   ├── User.java                      ← Data user
│   │   ├── ItemDAO.java                   ← Query CRUD item + implement Bookable
│   │   └── UserDAO.java                   ← Query user
│   ├── view/
│   │   ├── LoginFrame.java                ← Halaman login
│   │   ├── RegisterFrame.java             ← Halaman register
│   │   ├── MainFrame.java                 ← Halaman utama
│   │   └── FormDialog.java                ← Dialog tambah/edit
│   ├── controller/
│   │   ├── AuthController.java            ← Logika autentikasi
│   │   └── ItemController.java            ← Logika CRUD & event
│   ├── util/
│   │   └── DatabaseConnection.java        ← Koneksi MySQL (Singleton)
│   └── Main.java                          ← Entry point
└── schema.sql                             ← Script database MySQL
```

---

## Cara Setup

### 1. Setup Database MySQL
```sql
-- Jalankan file schema.sql di MySQL Workbench atau phpMyAdmin
source /path/to/schema.sql;
```

### 2. Konfigurasi Koneksi Database
Edit file `src/util/DatabaseConnection.java`:
```java
private static final String URL      = "jdbc:mysql://localhost:3306/hotel_nusantara";
private static final String USER     = "root";       // sesuaikan username MySQL Anda
private static final String PASSWORD = "";           // sesuaikan password MySQL Anda
```

### 3. Download MySQL Connector/J
- Download dari: https://dev.mysql.com/downloads/connector/j/
- Letakkan file `.jar` di folder `lib/`

### 4. Setup di Apache NetBeans
1. Buka NetBeans → File → New Project → Java with Ant → Java Application
2. Copy semua file source ke `src/`
3. Klik kanan project → Properties → Libraries → Add JAR/Folder
4. Pilih file `mysql-connector-j-x.x.x.jar` dari folder `lib/`
5. Set Main Class: `Main`
6. Run!

---

## Akun Default (dari sample data)

| Username | Password  | Nama          |
|----------|-----------|---------------|
| admin    | admin123  | Administrator |
| staff    | staff123  | Staff Hotel   |

---

## Fitur Lengkap

### Autentikasi
- ✅ Login dengan validasi database
- ✅ Maksimal 3 kali percobaan login, lalu program tutup otomatis
- ✅ Register dengan validasi username unik
- ✅ Redirect ke halaman yang sesuai setelah login/register

### Manajemen Kamar
- ✅ Tambah kamar baru dengan ID otomatis
- ✅ Tampil semua kamar dari database
- ✅ Edit data kamar yang dipilih
- ✅ Hapus kamar dengan konfirmasi
- ✅ Cari kamar berdasarkan nama atau tipe

### Manajemen Ruang Meeting
- ✅ Tambah ruang meeting baru
- ✅ Tampil semua ruang meeting
- ✅ Edit data ruang meeting
- ✅ Hapus ruang meeting dengan konfirmasi
- ✅ Cari ruang meeting berdasarkan nama atau fasilitas

### OOP & Arsitektur
- ✅ Kelas abstrak `ItemHotel`
- ✅ `Kamar` dan `RuangMeeting` sebagai turunan
- ✅ Interface `Bookable` dengan method `booking()` dan `batalBooking()`
- ✅ Enkapsulasi dengan getter/setter
- ✅ Arsitektur MVC (Model-View-Controller)
- ✅ Singleton pattern untuk DatabaseConnection

---

## Teknologi
- **Bahasa**: Java (JDK 8+)
- **GUI**: Java Swing
- **Database**: MySQL
- **Driver**: MySQL Connector/J
- **IDE**: Apache NetBeans
