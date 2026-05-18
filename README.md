public class FormDialog extends JDialog {
    private JTextField namaField, hargaField, tipeField, kapasitasField, fasilitasField;
    private JComboBox<String> jenisCombo;
    private JButton simpanBtn;
    private ItemController controller;
    private String editId; // null jika tambah

    public FormDialog(JFrame parent, String title, ItemHotel item) {
        super(parent, title, true);
        controller = new ItemController();
        if (item != null) {
            editId = item.getId();
            // isi field dengan data item
        }
        initComponents();
        pack();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setLayout(new GridLayout(0, 2, 10, 10));
        // tambahkan komponen...
        
        simpanBtn.addActionListener(e -> {
            if (validasi()) {
                if (editId == null) {
                    controller.addItem(jenisCombo.getSelectedItem().toString(), ...);
                } else {
                    controller.updateItem(editId, ...);
                }
                dispose();
            }
        });
    }

    private boolean validasi() {
        if (namaField.getText().trim().isEmpty() || hargaField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Field tidak boleh kosong!");
            return false;
        }
        return true;
    }
}
