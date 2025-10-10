# Janji

Saya Zahran Zaidan Saputra dengan NIM 2415429 mengerjakan Tugas Praktikum 4 dalam mata kuliah Desain Pemrograman Berorientasi Objek (DPBO) untuk keberkahan-Nya, maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

# Desain

Untuk desainnya saya mengubah tema default menjadi Data Produk Game.Untuk atributnya tidak ada perubahan dan saya menambahkan satu atribut yaitu RatingUsia.

**Produk Atribut**
* **`id`**: Kode unik gamenya.
* **`nama`**: Judul gamenya.
* **`harga`**: Harga jual gamenya.
* **`kategori`**: Genre gamenya (RPG, Action, dll.).
* **`ratingUsia`**: Batas usia untuk game tersebut.

**Method**

* **`Getter`**
* **`Setter`**

**Komponen View**

* **`JPanel` (`mainPanel`)**: Panel utama yang menjadi wadah untuk semua komponen lainnya.
* **`JLabel`**: Digunakan untuk menampilkan teks statis seperti "ID Produk", "Nama", "Harga", dll.
* **`JTextField`**: Digunakan sebagai kolom input untuk ID, Nama, dan Harga game.
* **`JComboBox`**: Digunakan untuk menampilkan daftar pilihan kategori game yang bisa dipilih.
* **`JRadioButton` & `ButtonGroup`**: Tiga `JRadioButton` ("Semua Umur", "Remaja", "Dewasa") yang dikelompokkan oleh sebuah `ButtonGroup` untuk memastikan hanya satu pilihan rating usia yang bisa aktif.
* **`JButton`**: Tombol-tombol interaktif: "Add/Update", "Delete", dan "Cancel".
* **`JTable` (`productTable`)**: Komponen utama untuk menampilkan semua data game dalam bentuk tabel yang rapi.
* **`JMenuBar`, `JMenu`, `JRadioButtonMenuItem`**: Membentuk menu bar di bagian atas jendela yang menyediakan fungsionalitas filter alternatif.

**Action Listener**

* **`addUpdateButton`**: Listener ini memeriksa apakah ada baris tabel yang sedang dipilih.
    * Jika **tidak ada**, tombol berfungsi sebagai "Add" dan akan memanggil method `insertData()`.
    * Jika **ada**, tombol berfungsi sebagai "Update" dan akan memanggil method `updateData()`.
* **`deleteButton`**: Memunculkan dialog konfirmasi terlebih dahulu. Jika pengguna setuju, listener akan memanggil method `deleteData()`.
* **`cancelButton`**: Memanggil method `clearForm()` untuk mengosongkan semua field input dan mengembalikan form ke mode "Add".
* **`productTable`**: Mendeteksi klik pada baris tabel. Saat baris diklik, listener ini akan mengambil data dari baris tersebut dan menampilkannya di form input.
* **`populateList`**: Untuk menambahkan data hardcode awal ke dalam listProduct
* **`kategoriComboBox` & `JRadioButtonMenuItem`**: Keduanya memiliki listener yang akan memanggil method `filterTableByCategory()` untuk menyaring data yang ditampilkan di tabel sesuai dengan kategori yang dipilih.

<img width="940" height="531" alt="image" src="https://github.com/user-attachments/assets/0871c32d-7fd4-4933-8e58-ecd4ee33cd35" />

---

**Alur Program**

1.  **Program Buka**: Saat program dibuka, data game dimuat dan ditampilkan di tabel. Kalau filenya belum ada, program akan menampilkan beberapa data contoh.
2.  **Tambah Data**: Pengguna mengisi data di kolom informasi data, lalu menekan "Add". Data baru akan langsung muncul di tabel dan tersimpan di file.
3.  **Ubah & Hapus Data**: Untuk mengubah atau menghapus, pengguna pertama-tama mengklik salah satu baris di tabel. Setelah data muncul di form, isinya bisa diubah lalu disimpan dengan tombol "Update", atau dihapus dengan tombol "Delete".
4.  **Batas Usia**: Pengguna dapat memilih batas usia yang disarankan pada sebuah game dengan memilih opsi ("Semua Umur", "Remaja", "Dewasa").

---

# Dokumentasi

**Add**
![Add](https://github.com/user-attachments/assets/ec673f91-0c4b-4b32-af53-436351b9dffb)

