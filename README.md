# Sistem Manajemen Klinik Gigi

## 1. Deskripsi Singkat Program
Sistem Informasi Manajemen Klinik Gigi berbasis **Java Console (CLI)** yang terintegrasi langsung dengan database **MySQL**. Aplikasi ini dirancang untuk mengelola data operasional klinik secara profesional meliputi pengelolaan data pasien, dokter penanggung jawab, dan rekam medis. 

Pembangunan sistem ini menerapkan arsitektur **Model-View-Controller (MVC)**, pola desain **DAO (Data Access Object)**, serta pengamanan kueri menggunakan **JDBC Prepared Statement** guna mencegah celah *SQL Injection*. Selain itu, program ini diatur agar *dummy data* awal dimuat langsung melalui file `.sql` di database, sehingga data langsung tampil tanpa perlu diinput manual dari awal.

---

## 2. Penjelasan Struktur Package
Seluruh kode sumber terorganisasi ke dalam beberapa *package*:

* **`config`**: Menampung kelas `klinikgigidb.java` yang bertanggung jawab penuh dalam membangun koneksi komunikasi antara aplikasi Java dan database MySQL menggunakan JDBC.
* **`model`**: Berisi cetak biru objek (*data model*) dan entitas bisnis, seperti superclass `Orang`, subclass `DokterGigi` & `Pasien`, entitas `RekamMedis`, serta *interface* `StatusLayanan`.
* **`model.dao`**: Berisi lapisan akses data (*Data Access Object*). Terdiri dari *interface* `PasienDAO` sebagai kontrak kerja dan kelas `PasienDAOImpl` yang berisi implementasi query SQL.
* **`controller`**: Berisi kelas `KlinikManager.java` yang bertindak sebagai jembatan pengelola logika bisnis antara lapisan *View* dan lapisan *DAO*.
* **`view`**: Berisi kelas `KlinikGigiManajemen4.java` yang bertindak sebagai antarmuka pengguna berbasis teks (*Console/CLI*) untuk menangani navigasi menu dan input interaktif.

---

## 3. Penjelasan Alur Program
Alur kerja aplikasi berjalan secara sistematis melalui tahapan berikut:

1. **Inisialisasi & Koneksi:** Saat program dijalankan, kelas *View* menginisialisasi *Controller* dan terhubung ke database MySQL melalui kelas konfigurasi koneksi.
2. **Menu Interaktif (Looping):** Sistem menampilkan 5 pilihan menu utama melalui perulangan `while`:
   * **Tambah Pasien Baru (Menu 1):** Pengguna menginputkan data pasien dan rekam medis. Sistem menampilkan daftar dokter yang diambil langsung dari database (`.sql`) untuk dipilih sebagai Dokter. Data kemudian disimpan secara aman ke database menggunakan *Prepared Statement*.
   * **Tampilkan Semua Pasien (Menu 2):** Sistem melakukan query *JOIN* pada tabel database untuk mengambil dan menampilkan data lengkap pasien, dokter, rekam medis, serta mencetak kartu berobat aktif.
   * **Ubah & Hapus Data (Menu 3 & 4):** Memperbarui atau menghapus record data pasien dan rekam medis secara *real-time* di database.
   * **Keluar (Menu 5):** Menghentikan program.

---

## 4. Penjelasan Penerapan Encapsulation dan Inheritance

### A. Encapsulation (Pengapsulan)
* **Konsep:** Menyembunyikan atribut internal kelas agar tidak dapat diakses secara langsung dari luar, melainkan melalui perantara method *Getter* dan *Setter*.
* **Penerapan:** Pada kelas-kelas di package `model` (seperti `RekamMedis.java` dan `Pasien.java`), seluruh atribut dideklarasikan dengan modifier `private` atau `protected`. Akses pembacaan dan perubahan nilai wajib melalui method mutator/accessor berstandar *CamelCase* (contoh: `getDiagnosa()` dan `setDiagnosa()`).

### B. Inheritance (Pewarisan)
* **Konsep:** Memungkinkan kelas anak (*subclass*) mewarisi atribut dan perilaku dari kelas induk (*superclass*).
* **Penerapan:** Kelas abstrak `Orang.java` bertindak sebagai *superclass* yang memiliki atribut umum berupa `id` dan `nama`. Kelas `DokterGigi.java` dan `Pasien.java` mewarisi sifat dari `Orang` menggunakan kata kunci `extends`, serta menambahkan atribut spesifik masing-masing.

**Kode Pewarisan & Abstraksi (`model/Orang.java` & `model/DokterGigi.java`):**
```java
package model;

public abstract class Orang {
    protected String id;
    protected String nama;

    public Orang(String id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public abstract void tampilkanProfil();
}

package model;

public class DokterGigi extends Orang {
    private String spesialisasi;

    public DokterGigi(String idDokter, String namaDokter, String spesialisasi) {
        super(idDokter, namaDokter);
        this.spesialisasi = spesialisasi;
    }

    @Override
    public void tampilkanProfil() {
        System.out.println("[Dokter] ID: " + id + " | Nama: " + nama + " | Spesialisasi: " + spesialisasi);
    }
}
```
---

## 5. Penjelasan Penerapan Polymorphism dan Abstraction

### A. Abstraction
* **Konsep:** Menyembunyikan kompleksitas internal dan hanya menampilkan kerangka fungsional yang esensial kepada pengguna.
* **Penerapan:** 
  * **Abstract Class (`Orang`):** Memiliki *abstract method* `tampilkanProfil()` yang wajib di-*override* oleh seluruh kelas turunannya.
  * **Interface (`StatusLayanan`):** Menyediakan kontrak murni tanpa isi (*body*) berupa method `cetakKartuBerobat()`.

### B. Polymorphism
* **Konsep:** Kemampuan suatu objek untuk mengambil "banyak bentuk".
* **Penerapan:**
  * **Method Overriding (Runtime):** Terjadi saat kelas `Pasien` mengimplementasikan kembali method `tampilkanProfil()` dan `cetakKartuBerobat()` sesuai kebutuhan entitas pasien.
  * **Method Overloading (Compile-Time):** Diterapkan pada kelas `KlinikManager` melalui variasi method `tambahPasien()`, di mana satu nama method dapat menerima parameter objek utuh ataupun menerima sekumpulan parameter variabel terpisah.

**Kode Polymorphism & Abstraction (`model/StatusLayanan.java` & `model/Pasien.java`):**
```java
package model;

public interface StatusLayanan {
    void cetakKartuBerobat();
}

package model;

public class PasienGigi extends Orang implements StatusLayanan {
    private DokterGigi dokter;
    private RekamMedis rekamMedis;

    public PasienGigi(String idPasien, String namaPasien, DokterGigi dokter, RekamMedis rekamMedis) {
        super(idPasien, namaPasien);
        this.dokter = dokter;
        this.rekamMedis = rekamMedis;
    }

    @Override
    public void tampilkanProfil() {
        System.out.println("[Pasien] ID: " + id + " | Nama: " + nama + " | Dokter PJ: " + dokter.getNama());
    }

    @Override
    public void cetakKartuBerobat() {
        System.out.println("--> KARTU BEROBAT AKTIF: Pasien " + nama + " (ID: " + id + ")");
    }
}
```
---
## 6. Penjelasan Penerapan JDBC (Java Database Connectivity) dan DAO

* **Pola DAO (Data Access Object):** Digunakan untuk memisahkan logika akses database dari logika bisnis aplikasi. Antarmuka `PasienDAO` mendefinisikan operasi standar (*CRUD*), sementara `PasienDAOImpl` mengeksekusi kueri SQL yang sebenarnya.
* **Koneksi JDBC:** Menggunakan pustaka `DriverManager.getConnection()` untuk menghubungkan program Java dengan server database MySQL (`klinik_gigi_db`).
* **Prepared Statement:** Seluruh kueri SQL (`INSERT`, `SELECT`, `UPDATE`, `DELETE`) menggunakan `PreparedStatement` alih-alih *Statement* biasa. Hal ini memastikan parameter input dari pengguna dienkapsulasi dengan aman untuk **mencegah celah keamanan SQL Injection**.
* **Manajemen Transaksi:** Menerapkan `conn.setAutoCommit(false)` dan `conn.commit()` pada proses penyimpanan data yang melibatkan banyak tabel (tabel `rekam_medis` dan `pasien_gigi`) untuk menjamin konsistensi data (*atomicity*).
* **Dummy Data via SQL:** Sesuai ketentuan, data awal master dokter dan rekam medis dimasukkan langsung melalui skrip file `.sql`, sehingga saat menu *Read* dijalankan, data langsung tampil otomatis tanpa memerlukan input manual di awal program.

**Kode Koneksi JDBC:**
```java
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {
    private static final String URL = "jdbc:mysql://localhost:3306/klinik_gigi_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
```
**Kode Implementasi DAO & Prepared Statement (model.dao/PasienDAOImpl.java):**
```java
package model.dao;

import config.klinikgigidb;
import model.DokterGigi;
import model.Pasien;
import model.RekamMedis;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PasienDAOImpl implements PasienDAO {

    @Override
    public void tambahPasien(Pasien pasien) {
        String sqlRM = "INSERT INTO rekam_medis (id_rekam_medis, diagnosa, catatan_tindakan) VALUES (?, ?, ?)";
        String sqlPasien = "INSERT INTO pasien_gigi (id_pasien, nama, id_dokter, id_rekam_medis) VALUES (?, ?, ?, ?)";

        try (Connection conn = klinikgigidb.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtRM = conn.prepareStatement(sqlRM)) {
                stmtRM.setString(1, pasien.getRekamMedis().getIdRekamMedis());
                stmtRM.setString(2, pasien.getRekamMedis().getDiagnosa());
                stmtRM.setString(3, pasien.getRekamMedis().getCatatanTindakan());
                stmtRM.executeUpdate();
            }

            try (PreparedStatement stmtPasien = conn.prepareStatement(sqlPasien)) {
                stmtPasien.setString(1, pasien.getId());
                stmtPasien.setString(2, pasien.getNama());
                stmtPasien.setString(3, pasien.getDokter().getId());
                stmtPasien.setString(4, pasien.getRekamMedis().getIdRekamMedis());
                stmtPasien.executeUpdate();
            }

            conn.commit();
            System.out.println("--> [Database] Data pasien dan rekam medis berhasil disimpan!");
        } catch (SQLException e) {
            System.err.println("Gagal menambah pasien: " + e.getMessage());
        }
    }
```
---
## 7. Running Program
### A. Tampilan Menu
<img width="324" height="141" alt="image" src="https://github.com/user-attachments/assets/d5d0d796-ae93-49ad-baac-5e15d817686a" /><br>
Ketika pertama kali dijalankan, program akan menampilkan output tampilan awal dari menu 1 hingga 5.

### 1. Tambah Pasien Baru
<img width="429" height="200" alt="image" src="https://github.com/user-attachments/assets/5f6b42b3-b71b-4255-aa89-75b446d0956d" /><br>
<img width="505" height="98" alt="image" src="https://github.com/user-attachments/assets/4692c847-9313-4be1-8fb6-57ad526a0647" /><br>
Menu tambah pasien baru ini untuk menambahkan data baru atau pasien yang ingin berobat pada klinik tersebut menggunakan menu 1 dan data yang telah di tambahkan akan tersimpan pada database yang berada pada MySQL.

### 2. Tampilkan Semua Pasien
<img width="490" height="261" alt="image" src="https://github.com/user-attachments/assets/5b1cbd9f-1759-4c49-9a6e-6dba6576c79e" /><br>
Menu 2 ini untuk melihat semua data pasien yang sudah terdaftar pada program seperti pada pasien `P003` yang baru saja di tambahkan menggunakan menu 1 dan `P001` dan `P002` itu sebagai *dummy data* yang telah dibuat pada database.

### 3. Ubah Data Pasien
<img width="398" height="122" alt="image" src="https://github.com/user-attachments/assets/aea34a3d-bfcd-4322-a24b-ff083245d4a5" /><br>
<img width="541" height="111" alt="image" src="https://github.com/user-attachments/assets/40dc3ff0-8b13-4b78-9e1e-5cfade91dfc1" /><br>
Menu 3 yaitu mengubah data pasien misalkan ingin mengubah data pasien yang salah input, seperti gambar diatas saya mengubah `nama`, `diagnosa`, dan `catatan tindakan`. Dan juga data yang terdapat pada database juga akan terganti jika menggunakan menu 3 ini.

### 4. Hapus Data Pasien
<img width="371" height="74" alt="image" src="https://github.com/user-attachments/assets/5535ca40-6b71-4d84-82b5-86cfea993906" /><br>
<img width="558" height="84" alt="image" src="https://github.com/user-attachments/assets/09232137-ab85-426d-bd83-8bea5512def1" /><br>
Menu 4 ini untuk menghapus data pasien, seperti pada gambar saya menghapus data pasien dengan ID `P003` maka data pasien tersebut akan terhapus dari program maupun pada database.

### 5. Keluar
<img width="478" height="245" alt="image" src="https://github.com/user-attachments/assets/be93fda6-0476-4eac-8408-11a989c5b0f3" /><br>
Kemudian pada menu 5 sekaligus menu terakhir yang digunakan untuk mengakhiri seluruh program.







