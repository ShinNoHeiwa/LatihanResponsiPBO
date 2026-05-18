package view;

import model.ItemHotel;
import model.Kamar;
import model.RuangMeeting;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class FormDialog extends JDialog {
    private boolean submitted = false;
    private String tipe;

    // Shared fields
    private JTextField txtNamaItem;
    private JTextField txtHargaPerMalam;

    // Kamar fields
    private JComboBox<String> cmbTipeKamar;
    private JTextField txtKapasitasTamu;

    // Ruang Meeting fields
    private JTextField txtKapasitasOrang;
    private JTextField txtFasilitas;

    public FormDialog(JFrame parent, String title, String tipe, ItemHotel existing) {
        super(parent, title, true);
        this.tipe = tipe;
        initComponents(existing);
    }

    private void initComponents(ItemHotel existing) {
        setSize(420, tipe.equals("kamar") ? 400 : 420);
        setLocationRelativeTo(getParent());
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(26, 54, 93), 0, getHeight(), new Color(13, 27, 42));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // Title bar
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.setBorder(new EmptyBorder(20, 25, 10, 25));

        JLabel lblTitle = new JLabel(getTitle());
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitle.setForeground(new Color(212, 175, 55));
        titlePanel.add(lblTitle, BorderLayout.WEST);

        // Form content
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        formPanel.setBorder(new EmptyBorder(5, 25, 15, 25));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 0, 2, 0);
        gbc.gridx = 0;

        // Nama Item
        gbc.gridy = 0;
        formPanel.add(createLabel("Nama Item"), gbc);
        gbc.gridy = 1;
        txtNamaItem = createTextField();
        formPanel.add(txtNamaItem, gbc);

        // Harga Per Malam
        gbc.gridy = 2;
        formPanel.add(createLabel("Harga Per Malam (Rp)"), gbc);
        gbc.gridy = 3;
        txtHargaPerMalam = createTextField();
        formPanel.add(txtHargaPerMalam, gbc);

        if (tipe.equals("kamar")) {
            // Tipe Kamar
            gbc.gridy = 4;
            formPanel.add(createLabel("Tipe Kamar"), gbc);
            gbc.gridy = 5;
            String[] tipeOptions = {"Standard", "Deluxe", "Suite", "Junior Suite", "Presidential Suite"};
            cmbTipeKamar = new JComboBox<>(tipeOptions);
            styleComboBox(cmbTipeKamar);
            formPanel.add(cmbTipeKamar, gbc);

            // Kapasitas Tamu
            gbc.gridy = 6;
            formPanel.add(createLabel("Kapasitas Tamu (orang)"), gbc);
            gbc.gridy = 7;
            txtKapasitasTamu = createTextField();
            formPanel.add(txtKapasitasTamu, gbc);

            // Fill existing data
            if (existing instanceof Kamar) {
                Kamar k = (Kamar) existing;
                txtNamaItem.setText(k.getNamaItem());
                txtHargaPerMalam.setText(String.valueOf((long) k.getHargaPerMalam()));
                cmbTipeKamar.setSelectedItem(k.getTipeKamar());
                txtKapasitasTamu.setText(String.valueOf(k.getKapasitasTamu()));
            }
        } else {
            // Kapasitas Orang
            gbc.gridy = 4;
            formPanel.add(createLabel("Kapasitas Orang"), gbc);
            gbc.gridy = 5;
            txtKapasitasOrang = createTextField();
            formPanel.add(txtKapasitasOrang, gbc);

            // Fasilitas
            gbc.gridy = 6;
            formPanel.add(createLabel("Fasilitas"), gbc);
            gbc.gridy = 7;
            txtFasilitas = createTextField();
            formPanel.add(txtFasilitas, gbc);

            // Fill existing data
            if (existing instanceof RuangMeeting) {
                RuangMeeting rm = (RuangMeeting) existing;
                txtNamaItem.setText(rm.getNamaItem());
                txtHargaPerMalam.setText(String.valueOf((long) rm.getHargaPerMalam()));
                txtKapasitasOrang.setText(String.valueOf(rm.getKapasitasOrang()));
                txtFasilitas.setText(rm.getFasilitas());
            }
        }

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(10, 25, 20, 25));

        JButton btnBatal = createSecondaryBtn("Batal");
        JButton btnSimpan = createPrimaryBtn("Simpan");

        buttonPanel.add(btnBatal);
        buttonPanel.add(btnSimpan);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);

        // Event handling
        btnSimpan.addActionListener(e -> doSimpan());
        btnBatal.addActionListener(e -> dispose());

        getRootPane().setDefaultButton(btnSimpan);
    }

    private void doSimpan() {
        if (!validateForm()) return;
        submitted = true;
        dispose();
    }

    private boolean validateForm() {
        if (txtNamaItem.getText().trim().isEmpty()) {
            showError("Nama Item tidak boleh kosong!");
            txtNamaItem.requestFocus();
            return false;
        }
        if (txtHargaPerMalam.getText().trim().isEmpty()) {
            showError("Harga Per Malam tidak boleh kosong!");
            txtHargaPerMalam.requestFocus();
            return false;
        }
        try {
            double harga = Double.parseDouble(txtHargaPerMalam.getText().trim());
            if (harga <= 0) {
                showError("Harga Per Malam harus lebih dari 0!");
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Harga Per Malam harus berupa angka!");
            txtHargaPerMalam.requestFocus();
            return false;
        }

        if (tipe.equals("kamar")) {
            if (txtKapasitasTamu.getText().trim().isEmpty()) {
                showError("Kapasitas Tamu tidak boleh kosong!");
                txtKapasitasTamu.requestFocus();
                return false;
            }
            try {
                int kap = Integer.parseInt(txtKapasitasTamu.getText().trim());
                if (kap <= 0) {
                    showError("Kapasitas Tamu harus lebih dari 0!");
                    return false;
                }
            } catch (NumberFormatException e) {
                showError("Kapasitas Tamu harus berupa angka bulat!");
                txtKapasitasTamu.requestFocus();
                return false;
            }
        } else {
            if (txtKapasitasOrang.getText().trim().isEmpty()) {
                showError("Kapasitas Orang tidak boleh kosong!");
                txtKapasitasOrang.requestFocus();
                return false;
            }
            try {
                int kap = Integer.parseInt(txtKapasitasOrang.getText().trim());
                if (kap <= 0) {
                    showError("Kapasitas Orang harus lebih dari 0!");
                    return false;
                }
            } catch (NumberFormatException e) {
                showError("Kapasitas Orang harus berupa angka bulat!");
                txtKapasitasOrang.requestFocus();
                return false;
            }
            if (txtFasilitas.getText().trim().isEmpty()) {
                showError("Fasilitas tidak boleh kosong!");
                txtFasilitas.requestFocus();
                return false;
            }
        }
        return true;
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Validasi", JOptionPane.WARNING_MESSAGE);
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(new Color(180, 180, 180));
        return lbl;
    }

    private JTextField createTextField() {
        JTextField tf = new JTextField();
        tf.setBackground(new Color(50, 80, 120));
        tf.setForeground(Color.WHITE);
        tf.setCaretColor(Color.WHITE);
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(212, 175, 55, 120), 1),
            new EmptyBorder(7, 10, 7, 10)
        ));
        return tf;
    }

    private void styleComboBox(JComboBox<String> cmb) {
        cmb.setBackground(new Color(50, 80, 120));
        cmb.setForeground(Color.WHITE);
        cmb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cmb.setBorder(BorderFactory.createLineBorder(new Color(212, 175, 55, 120), 1));
        ((JLabel) cmb.getRenderer()).setBorder(new EmptyBorder(5, 8, 5, 8));
    }

    private JButton createPrimaryBtn(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getModel().isPressed() ? new Color(180, 145, 30) :
                             getModel().isRollover() ? new Color(230, 190, 60) :
                             new Color(212, 175, 55));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                g2d.setColor(new Color(26, 54, 93));
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                g2d.drawString(getText(),
                    (getWidth() - fm.stringWidth(getText())) / 2,
                    ((getHeight() - fm.getHeight()) / 2) + fm.getAscent());
            }
        };
        btn.setPreferredSize(new Dimension(90, 36));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JButton createSecondaryBtn(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getModel().isPressed() ? new Color(60, 100, 150) :
                             getModel().isRollover() ? new Color(50, 90, 140) :
                             new Color(40, 70, 110));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                g2d.setColor(Color.WHITE);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                g2d.drawString(getText(),
                    (getWidth() - fm.stringWidth(getText())) / 2,
                    ((getHeight() - fm.getHeight()) / 2) + fm.getAscent());
            }
        };
        btn.setPreferredSize(new Dimension(90, 36));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // ===================== GETTERS =====================

    public boolean isSubmitted() { return submitted; }

    public String getNamaItem() { return txtNamaItem.getText().trim(); }

    public double getHargaPerMalam() { return Double.parseDouble(txtHargaPerMalam.getText().trim()); }

    public String getTipeKamar() {
        return cmbTipeKamar != null ? (String) cmbTipeKamar.getSelectedItem() : "";
    }

    public int getKapasitasTamu() {
        return txtKapasitasTamu != null ? Integer.parseInt(txtKapasitasTamu.getText().trim()) : 0;
    }

    public int getKapasitasOrang() {
        return txtKapasitasOrang != null ? Integer.parseInt(txtKapasitasOrang.getText().trim()) : 0;
    }

    public String getFasilitas() {
        return txtFasilitas != null ? txtFasilitas.getText().trim() : "";
    }
}
