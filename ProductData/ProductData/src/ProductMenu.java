import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.TableColumnModel;

public class ProductMenu extends JFrame {
    public static void main(String[] args) {
        // buat object menu
        ProductMenu menu = new ProductMenu();

        // atur ukuran menu
        menu.setSize(700, 600);

        // letakkan menu di tengah layar
        menu.setLocationRelativeTo(null);

        // isi menu
        menu.setContentPane(menu.mainPanel);

        // ubah warna background
        menu.getContentPane().setBackground(Color.LIGHT_GRAY);

        // tampilkan menu
        menu.setVisible(true);

        // agar program ikut berhenti saat menu diclose
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // index baris yang diklik
    private int selectedIndex = -1;
    // list untuk menampung semua produk
    private ArrayList<Product> listProduct;

    private JPanel mainPanel;
    private JTextField idField;
    private JTextField namaField;
    private JTextField hargaField;
    private JTable productTable;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JComboBox<String> kategoriComboBox;
    private JButton deleteButton;
    private JLabel titleLabel;
    private JLabel idLabel;
    private JLabel namaLabel;
    private JLabel hargaLabel;
    private JLabel kategoriLabel;
    private JRadioButton semuaUmurRadioButton;
    private JRadioButton remajaRadioButton;
    private JRadioButton dewasaRadioButton;
    private ButtonGroup ratingGroup;

    // constructor
    public ProductMenu() {
        // inisialisasi listProduct
        listProduct = new ArrayList<>();

        // inisialisasi dan grouping Radio Button
        ratingGroup = new ButtonGroup();
        ratingGroup.add(semuaUmurRadioButton);
        ratingGroup.add(remajaRadioButton);
        ratingGroup.add(dewasaRadioButton);

        // isi listProduct
        populateList();

        // isi tabel produk
        productTable.setModel(setTable());

        aturLebarKolom();

        // ubah styling title
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        // atur isi combo box
        String[] kategoriData = { "???", "Action", "Adventure", "Fighting", "Indie", "RPG", "Simulation", "Sports", "Strategy" };
        kategoriComboBox.setModel(new DefaultComboBoxModel<>(kategoriData));

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // saat tombol add/update ditekan
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) {
                    // mode add
                    insertData();
                } else {
                    // mode update
                    updateData();
                }
            }
        });

        // saat tombol delete ditekan
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int konfirmasi = JOptionPane.showConfirmDialog(null,
                        "Yakin ingin menghapus data ini?",
                        "Konfirmasi Hapus",
                        JOptionPane.YES_NO_OPTION);

                // Jika user mengklik "Yes", maka hapus data
                if (konfirmasi == JOptionPane.YES_OPTION) {
                    deleteData();
                }
            }
        });

        // saat tombol cancel ditekan
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        // saat salah satu baris tabel ditekan
        productTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // ubah selectedIndex menjadi baris tabel yang diklik
                selectedIndex = productTable.getSelectedRow();

                // simpan value textfield dan combo box
                String curId = productTable.getModel().getValueAt(selectedIndex,1).toString();
                String curNama = productTable.getModel().getValueAt(selectedIndex,2).toString();
                String curHarga = productTable.getModel().getValueAt(selectedIndex,3).toString();
                String curKategori = productTable.getModel().getValueAt(selectedIndex,4).toString();

                // ubah isi textfield dan combo box
                idField.setText(curId);
                namaField.setText(curNama);
                hargaField.setText(curHarga);
                kategoriComboBox.setSelectedItem(curKategori);

                // ubah button "Add" menjadi "Update"
                addUpdateButton.setText("Update");

                // tampilkan button delete
                deleteButton.setVisible(true);
            }
        });
    }

    public final DefaultTableModel setTable() {
        // tentukan kolom tabel
        Object[] cols = { "No", "ID", "Nama", "Harga", "Kategori", "Rating Usia" };

        // buat objek tabel dengan kolom yang sudah dibuat
        DefaultTableModel tmp = new DefaultTableModel(null, cols);

        for (int i = 0; i < listProduct.size(); i++) {
            Object[] row = {i + 1,
                    listProduct.get(i).getId(),
                    listProduct.get(i).getNama(),
                    String.format("%.2f", listProduct.get(i).getHarga()),
                    listProduct.get(i).getKategori(),
                    listProduct.get(i).getRatingUsia()
            };
            tmp.addRow(row);
        }
        return tmp;
    }

    // Method helper untuk mendapatkan teks dari radio button yang dipilih
    public String getSelectedRating() {
        if (semuaUmurRadioButton.isSelected()) {
            return "Semua Umur";
        } else if (remajaRadioButton.isSelected()) {
            return "Remaja";
        } else if (dewasaRadioButton.isSelected()) {
            return "Dewasa";
        }
        return ""; // Kembalikan string kosong jika tidak ada yang dipilih
    }

    public void insertData() {
        try {
            // ambil value dari textfield dan combobox
            String id = idField.getText();
            String nama = namaField.getText();
            double harga = Double.parseDouble(hargaField.getText());
            String kategori = kategoriComboBox.getSelectedItem().toString();
            String rating = getSelectedRating();

            // tambahkan data ke dalam list
            listProduct.add(new Product(id, nama, harga, kategori, rating));

            // update tabel
            productTable.setModel(setTable());

            aturLebarKolom();

            // bersihkan form
            clearForm();

            // feedback
            System.out.println("Insert berhasil");
            JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Harga harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateData() {
        try {
            // ambil data dari form
            String id = idField.getText();
            String nama = namaField.getText();
            double harga = Double.parseDouble(hargaField.getText());
            String kategori = kategoriComboBox.getSelectedItem().toString();
            String rating = getSelectedRating();

            // ubah data produk di list
            listProduct.get(selectedIndex).setId(id);
            listProduct.get(selectedIndex).setNama(nama);
            listProduct.get(selectedIndex).setHarga(harga);
            listProduct.get(selectedIndex).setKategori(kategori);
            listProduct.get(selectedIndex).setRatingUsia(rating);

            // update tabel
            productTable.setModel(setTable());

            aturLebarKolom();

            // bersihkan form
            clearForm();

            // feedback
            System.out.println("Update berhasil");
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Harga harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteData() {
        // hapus data dari list
        listProduct.remove(selectedIndex);

        // 3. update tabel (Memuat ulang dari database)
        productTable.setModel(setTable());

        aturLebarKolom();

        // 4. bersihkan form
        clearForm();

        // feedback
        System.out.println("Delete berhasil");
        JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
    }

    public void clearForm() {
        // kosongkan semua texfield dan combo box
        idField.setText("");
        namaField.setText("");
        hargaField.setText("");
        kategoriComboBox.setSelectedIndex(0);
        ratingGroup.clearSelection();

        // ubah button "Update" menjadi "Add"
        addUpdateButton.setText("Add");

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // ubah selectedIndex menjadi -1 (tidak ada baris yang dipilih)
        selectedIndex = -1;
    }

    private void aturLebarKolom() {
        TableColumnModel columnModel = productTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(40);
        columnModel.getColumn(0).setMaxWidth(40);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(200);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(120);
        columnModel.getColumn(5).setPreferredWidth(100);
    }

    private void populateList() {
        // Hapus data lama (jika ada) dan isi dengan data game
        listProduct.clear();

        // Menambahkan data game digital
        listProduct.add(new Product("RPG-001", "The Witcher 3: Wild Hunt", 450000, "RPG", "Dewasa"));
        listProduct.add(new Product("SIM-001", "Stardew Valley", 120000, "Simulation", "Semua Umur"));
        listProduct.add(new Product("ACT-001", "Grand Theft Auto V", 350000, "Action", "Dewasa"));
        listProduct.add(new Product("ADV-001", "Red Dead Redemption 2", 640000, "Adventure", "Dewasa"));
        listProduct.add(new Product("STR-001", "Civilization VI", 300000, "Strategy", "Remaja"));
        listProduct.add(new Product("IND-001", "Hollow Knight", 150000, "Indie", "Semua Umur"));
        listProduct.add(new Product("RPG-002", "Cyberpunk 2077", 700000, "RPG", "Dewasa"));
        listProduct.add(new Product("SPT-001", "FIFA 23", 850000, "Sports", "Semua Umur"));
        listProduct.add(new Product("ACT-002", "DOOM Eternal", 600000, "Action", "Dewasa"));
        listProduct.add(new Product("ADV-002", "The Last of Us Part I", 730000, "Adventure", "Dewasa"));
        listProduct.add(new Product("SIM-002", "The Sims 4", 480000, "Simulation", "Remaja"));
        listProduct.add(new Product("RPG-003", "Elden Ring", 730000, "RPG", "Dewasa"));
        listProduct.add(new Product("FGT-001", "Mortal Kombat 11", 550000, "Fighting", "Dewasa"));
        listProduct.add(new Product("ACT-003", "God of War Ragnarök", 880000, "Action", "Dewasa"));
        listProduct.add(new Product("IND-002", "Among Us", 50000, "Indie", "Semua Umur"));

    }

}