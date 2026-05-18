public class MainFrame extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton tambahBtn, editBtn, hapusBtn, cariBtn;
    private ItemController itemController;

    public MainFrame() {
        itemController = new ItemController();
        initComponents();
        loadTableData();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Sistem Manajemen Hotel");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Table model
        tableModel = new DefaultTableModel(new String[]{"ID", "Nama", "Harga/Malam", "Tipe/Kapasitas", "Jenis"}, 0);
        table = new JTable(tableModel);
        
        // Tombol
        tambahBtn = new JButton("Tambah");
        editBtn = new JButton("Edit");
        hapusBtn = new JButton("Hapus");
        cariBtn = new JButton("Cari");

        // Layout (gunakan BorderLayout + JPanel untuk tombol)
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(tambahBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(hapusBtn);
        buttonPanel.add(cariBtn);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Event handling (cukup panggil method controller)
        tambahBtn.addActionListener(e -> bukaFormTambah());
        editBtn.addActionListener(e -> bukaFormEdit());
        hapusBtn.addActionListener(e -> hapusData());
        cariBtn.addActionListener(e -> cariData());
    }

    private void loadTableData() {
        tableModel.setRowCount(0);
        for (ItemHotel item : itemController.getAllItems()) {
            if (item instanceof Kamar) {
                Kamar k = (Kamar) item;
                tableModel.addRow(new Object[]{k.getId(), k.getNamaItem(), k.getHargaPerMalam(), k.getTipeKamar() + ", " + k.getKapasitasTamu() + " org", "Kamar"});
            } else if (item instanceof RuangMeeting) {
                RuangMeeting r = (RuangMeeting) item;
                tableModel.addRow(new Object[]{r.getId(), r.getNamaItem(), r.getHargaPerMalam(), r.getKapasitasOrang() + " org, " + r.getFasilitas(), "Meeting"});
            }
        }
    }

    private void bukaFormTambah() {
        FormDialog dialog = new FormDialog(this, "Tambah Data", null);
        dialog.setVisible(true);
        loadTableData(); // refresh setelah close
    }

    private void bukaFormEdit() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan diedit.");
            return;
        }
        String id = tableModel.getValueAt(row, 0).toString();
        ItemHotel item = itemController.getItemById(id);
        FormDialog dialog = new FormDialog(this, "Edit Data", item);
        dialog.setVisible(true);
        loadTableData();
    }

    private void hapusData() {
        int row = table.getSelectedRow();
        if (row == -1) return;
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin hapus?");
        if (confirm == JOptionPane.YES_OPTION) {
            String id = tableModel.getValueAt(row, 0).toString();
            itemController.deleteItem(id);
            loadTableData();
        }
    }

    private void cariData() {
        String keyword = JOptionPane.showInputDialog(this, "Masukkan kata kunci:");
        if (keyword != null) {
            tableModel.setRowCount(0);
            for (ItemHotel item : itemController.searchItems(keyword)) {
                // tambahkan ke tabel seperti di loadTableData
            }
        }
    }
}            }
        }
    }
}
