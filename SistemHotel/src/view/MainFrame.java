package view;

import controller.ItemController;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {
    private User currentUser;
    private ItemController itemController;

    // Kamar table
    private JTable tblKamar;
    private DefaultTableModel kamarTableModel;
    private JTextField txtCariKamar;

    // Ruang Meeting table
    private JTable tblRuangMeeting;
    private DefaultTableModel ruangMeetingTableModel;
    private JTextField txtCariRuangMeeting;

    private JTabbedPane tabbedPane;

    public MainFrame(User user) {
        this.currentUser = user;
        this.itemController = new ItemController(this);
        initComponents();
        loadData();
    }

    private void initComponents() {
        setTitle("Hotel Nusantara - Sistem Manajemen Hotel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 680);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(800, 550));

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(13, 27, 42), 0, getHeight(), new Color(26, 54, 93));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // ===== TOP BAR =====
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setBorder(new EmptyBorder(15, 25, 15, 25));

        JPanel leftTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftTop.setOpaque(false);

        JLabel lblHotelIcon = new JLabel("🏨 ");
        lblHotelIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 22));

        JLabel lblHotelName = new JLabel("HOTEL NUSANTARA");
        lblHotelName.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblHotelName.setForeground(new Color(212, 175, 55));

        leftTop.add(lblHotelIcon);
        leftTop.add(lblHotelName);

        JPanel rightTop = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightTop.setOpaque(false);

        JLabel lblUser = new JLabel("👤 " + currentUser.getNamaLengkap());
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblUser.setForeground(new Color(180, 200, 220));

        JButton btnLogout = new JButton("Logout") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getModel().isRollover() ? new Color(200, 50, 50) : new Color(160, 30, 30));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                g2d.setColor(Color.WHITE);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                g2d.drawString(getText(),
                    (getWidth() - fm.stringWidth(getText())) / 2,
                    ((getHeight() - fm.getHeight()) / 2) + fm.getAscent());
            }
        };
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnLogout.setPreferredSize(new Dimension(80, 30));
        btnLogout.setContentAreaFilled(false);
        btnLogout.setBorderPainted(false);
        btnLogout.setFocusPainted(false);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(MainFrame.this,
                "Yakin ingin logout?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new LoginFrame().setVisible(true);
            }
        });

        rightTop.add(lblUser);
        rightTop.add(btnLogout);

        topBar.add(leftTop, BorderLayout.WEST);
        topBar.add(rightTop, BorderLayout.EAST);

        // Divider line
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(212, 175, 55, 80));
        sep.setBackground(new Color(0, 0, 0, 0));

        JPanel topSection = new JPanel(new BorderLayout());
        topSection.setOpaque(false);
        topSection.add(topBar, BorderLayout.CENTER);
        topSection.add(sep, BorderLayout.SOUTH);

        // ===== TABBED PANE =====
        tabbedPane = new JTabbedPane();
        tabbedPane.setOpaque(false);
        tabbedPane.setBackground(new Color(0, 0, 0, 0));
        tabbedPane.setForeground(Color.WHITE);
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 13));
        styleTabPane(tabbedPane);

        // Tab Kamar
        JPanel kamarTab = buildKamarPanel();
        tabbedPane.addTab("  🛏  Kamar  ", kamarTab);

        // Tab Ruang Meeting
        JPanel ruangMeetingTab = buildRuangMeetingPanel();
        tabbedPane.addTab("  🏢  Ruang Meeting  ", ruangMeetingTab);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.setBorder(new EmptyBorder(15, 20, 20, 20));
        contentPanel.add(tabbedPane, BorderLayout.CENTER);

        mainPanel.add(topSection, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    private JPanel buildKamarPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(15, 10, 10, 10));

        // Top: search + buttons
        JPanel topPanel = new JPanel(new BorderLayout(10, 0));
        topPanel.setOpaque(false);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        searchPanel.setOpaque(false);

        txtCariKamar = createSearchField("Cari nama atau tipe kamar...");
        searchPanel.add(txtCariKamar);

        JButton btnCari = createActionButton("🔍 Cari", new Color(52, 152, 219));
        btnCari.addActionListener(e -> {
            String kw = txtCariKamar.getText().trim();
            if (kw.isEmpty() || kw.equals("Cari nama atau tipe kamar...")) {
                itemController.loadKamar();
            } else {
                itemController.cariKamar(kw);
            }
        });
        JButton btnReset = createActionButton("↺ Reset", new Color(100, 110, 130));
        btnReset.addActionListener(e -> {
            txtCariKamar.setText("");
            itemController.loadKamar();
        });
        searchPanel.add(btnCari);
        searchPanel.add(btnReset);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        buttonPanel.setOpaque(false);
        JButton btnTambah = createActionButton("➕ Tambah", new Color(39, 174, 96));
        JButton btnEdit   = createActionButton("✏ Edit", new Color(230, 126, 34));
        JButton btnHapus  = createActionButton("🗑 Hapus", new Color(192, 57, 43));

        btnTambah.addActionListener(e -> itemController.tambahKamar());
        btnEdit.addActionListener(e -> itemController.editKamar(tblKamar.getSelectedRow()));
        btnHapus.addActionListener(e -> itemController.hapusKamar(tblKamar.getSelectedRow()));

        buttonPanel.add(btnTambah);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnHapus);

        topPanel.add(searchPanel, BorderLayout.WEST);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        // Table
        String[] columns = {"ID", "Nama Kamar", "Harga / Malam", "Tipe Kamar", "Kapasitas"};
        kamarTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tblKamar = createStyledTable(kamarTableModel);

        // Set column widths
        tblKamar.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblKamar.getColumnModel().getColumn(1).setPreferredWidth(200);
        tblKamar.getColumnModel().getColumn(2).setPreferredWidth(150);
        tblKamar.getColumnModel().getColumn(3).setPreferredWidth(130);
        tblKamar.getColumnModel().getColumn(4).setPreferredWidth(100);

        JScrollPane scrollPane = createStyledScrollPane(tblKamar);

        // Info label
        JLabel lblInfo = new JLabel("Double-click baris untuk melihat detail • Pilih baris lalu klik Edit atau Hapus");
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblInfo.setForeground(new Color(130, 150, 170));

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(lblInfo, BorderLayout.SOUTH);

        // Double click detail
        tblKamar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = tblKamar.getSelectedRow();
                    if (row >= 0) {
                        showDetailKamar(row);
                    }
                }
            }
        });

        return panel;
    }

    private JPanel buildRuangMeetingPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(15, 10, 10, 10));

        JPanel topPanel = new JPanel(new BorderLayout(10, 0));
        topPanel.setOpaque(false);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        searchPanel.setOpaque(false);

        txtCariRuangMeeting = createSearchField("Cari nama atau fasilitas...");
        searchPanel.add(txtCariRuangMeeting);

        JButton btnCari = createActionButton("🔍 Cari", new Color(52, 152, 219));
        btnCari.addActionListener(e -> {
            String kw = txtCariRuangMeeting.getText().trim();
            if (kw.isEmpty() || kw.equals("Cari nama atau fasilitas...")) {
                itemController.loadRuangMeeting();
            } else {
                itemController.cariRuangMeeting(kw);
            }
        });
        JButton btnReset = createActionButton("↺ Reset", new Color(100, 110, 130));
        btnReset.addActionListener(e -> {
            txtCariRuangMeeting.setText("");
            itemController.loadRuangMeeting();
        });
        searchPanel.add(btnCari);
        searchPanel.add(btnReset);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        buttonPanel.setOpaque(false);
        JButton btnTambah = createActionButton("➕ Tambah", new Color(39, 174, 96));
        JButton btnEdit   = createActionButton("✏ Edit", new Color(230, 126, 34));
        JButton btnHapus  = createActionButton("🗑 Hapus", new Color(192, 57, 43));

        btnTambah.addActionListener(e -> itemController.tambahRuangMeeting());
        btnEdit.addActionListener(e -> itemController.editRuangMeeting(tblRuangMeeting.getSelectedRow()));
        btnHapus.addActionListener(e -> itemController.hapusRuangMeeting(tblRuangMeeting.getSelectedRow()));

        buttonPanel.add(btnTambah);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnHapus);

        topPanel.add(searchPanel, BorderLayout.WEST);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        String[] columns = {"ID", "Nama Ruang", "Harga / Malam", "Kapasitas", "Fasilitas"};
        ruangMeetingTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tblRuangMeeting = createStyledTable(ruangMeetingTableModel);

        tblRuangMeeting.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblRuangMeeting.getColumnModel().getColumn(1).setPreferredWidth(200);
        tblRuangMeeting.getColumnModel().getColumn(2).setPreferredWidth(150);
        tblRuangMeeting.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblRuangMeeting.getColumnModel().getColumn(4).setPreferredWidth(200);

        JScrollPane scrollPane = createStyledScrollPane(tblRuangMeeting);

        JLabel lblInfo = new JLabel("Double-click baris untuk melihat detail • Pilih baris lalu klik Edit atau Hapus");
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblInfo.setForeground(new Color(130, 150, 170));

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(lblInfo, BorderLayout.SOUTH);

        tblRuangMeeting.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = tblRuangMeeting.getSelectedRow();
                    if (row >= 0) {
                        showDetailRuangMeeting(row);
                    }
                }
            }
        });

        return panel;
    }

    private void showDetailKamar(int row) {
        String id = String.valueOf(kamarTableModel.getValueAt(row, 0));
        String nama = (String) kamarTableModel.getValueAt(row, 1);
        String harga = (String) kamarTableModel.getValueAt(row, 2);
        String tipe = (String) kamarTableModel.getValueAt(row, 3);
        String kap = (String) kamarTableModel.getValueAt(row, 4);
        JOptionPane.showMessageDialog(this,
            "ID       : " + id + "\n" +
            "Nama     : " + nama + "\n" +
            "Harga    : " + harga + "\n" +
            "Tipe     : " + tipe + "\n" +
            "Kapasitas: " + kap,
            "Detail Kamar", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showDetailRuangMeeting(int row) {
        String id = String.valueOf(ruangMeetingTableModel.getValueAt(row, 0));
        String nama = (String) ruangMeetingTableModel.getValueAt(row, 1);
        String harga = (String) ruangMeetingTableModel.getValueAt(row, 2);
        String kap = (String) ruangMeetingTableModel.getValueAt(row, 3);
        String fasilitas = (String) ruangMeetingTableModel.getValueAt(row, 4);
        JOptionPane.showMessageDialog(this,
            "ID         : " + id + "\n" +
            "Nama       : " + nama + "\n" +
            "Harga      : " + harga + "\n" +
            "Kapasitas  : " + kap + "\n" +
            "Fasilitas  : " + fasilitas,
            "Detail Ruang Meeting", JOptionPane.INFORMATION_MESSAGE);
    }

    private void loadData() {
        itemController.loadKamar();
        itemController.loadRuangMeeting();
    }

    private JTextField createSearchField(String placeholder) {
        JTextField tf = new JTextField(22) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty()) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setColor(new Color(120, 140, 160));
                    g2d.setFont(getFont().deriveFont(Font.ITALIC));
                    g2d.drawString(placeholder, 10, getHeight() / 2 + g2d.getFontMetrics().getAscent() / 2 - 2);
                }
            }
        };
        tf.setBackground(new Color(50, 80, 120));
        tf.setForeground(Color.WHITE);
        tf.setCaretColor(Color.WHITE);
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(212, 175, 55, 80), 1),
            new EmptyBorder(6, 10, 6, 10)
        ));
        tf.setPreferredSize(new Dimension(220, 34));
        return tf;
    }

    private JButton createActionButton(String text, Color baseColor) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color c = getModel().isPressed() ? baseColor.darker() :
                          getModel().isRollover() ? baseColor.brighter() : baseColor;
                g2d.setColor(c);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                g2d.setColor(Color.WHITE);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                g2d.drawString(getText(),
                    (getWidth() - fm.stringWidth(getText())) / 2,
                    ((getHeight() - fm.getHeight()) / 2) + fm.getAscent());
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setPreferredSize(new Dimension(110, 34));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JTable createStyledTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setBackground(new Color(20, 45, 75));
        table.setForeground(Color.WHITE);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(36);
        table.setSelectionBackground(new Color(212, 175, 55, 100));
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(new Color(212, 175, 55, 40));
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Header styling
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(26, 54, 93));
        header.setForeground(new Color(212, 175, 55));
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setReorderingAllowed(false);
        header.setPreferredSize(new Dimension(header.getWidth(), 42));

        // Center renderer for ID and numeric columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

        // Alternating row colors
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value, boolean selected,
                    boolean focused, int row, int col) {
                super.getTableCellRendererComponent(t, value, selected, focused, row, col);
                if (selected) {
                    setBackground(new Color(212, 175, 55, 80));
                    setForeground(Color.WHITE);
                } else if (row % 2 == 0) {
                    setBackground(new Color(20, 45, 75));
                    setForeground(Color.WHITE);
                } else {
                    setBackground(new Color(25, 55, 90));
                    setForeground(new Color(200, 210, 220));
                }
                setBorder(new EmptyBorder(0, 10, 0, 10));
                if (col == 0) setHorizontalAlignment(JLabel.CENTER);
                else setHorizontalAlignment(JLabel.LEFT);
                return this;
            }
        });

        return table;
    }

    private JScrollPane createStyledScrollPane(JTable table) {
        JScrollPane sp = new JScrollPane(table);
        sp.setOpaque(false);
        sp.getViewport().setOpaque(false);
        sp.getViewport().setBackground(new Color(20, 45, 75));
        sp.setBorder(BorderFactory.createLineBorder(new Color(212, 175, 55, 60), 1));
        sp.getVerticalScrollBar().setBackground(new Color(20, 45, 75));
        return sp;
    }

    private void styleTabPane(JTabbedPane pane) {
        pane.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
            @Override
            protected void installDefaults() {
                super.installDefaults();
                highlight = new Color(212, 175, 55);
                lightHighlight = new Color(212, 175, 55);
                shadow = new Color(26, 54, 93);
                darkShadow = new Color(13, 27, 42);
                focus = new Color(212, 175, 55);
            }
            @Override
            protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex,
                    int x, int y, int w, int h, boolean isSelected) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (isSelected) {
                    g2d.setColor(new Color(26, 54, 93));
                } else {
                    g2d.setColor(new Color(13, 27, 42));
                }
                g2d.fillRect(x, y, w, h);
            }
            @Override
            protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex,
                    int x, int y, int w, int h, boolean isSelected) {
                Graphics2D g2d = (Graphics2D) g;
                if (isSelected) {
                    g2d.setColor(new Color(212, 175, 55));
                    g2d.fillRect(x, y + h - 3, w, 3);
                }
            }
            @Override
            protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(new Color(212, 175, 55, 60));
                Insets insets = getContentBorderInsets(tabPlacement);
                int x = tabInsets.left;
                int y = calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
                int w = tabPane.getWidth() - insets.right - insets.left;
                int h = tabPane.getHeight() - y - insets.bottom;
                g2d.drawRect(x, y, w - 1, h - 1);
            }
        });
    }

    // ===== PUBLIC GETTERS FOR CONTROLLER =====

    public DefaultTableModel getKamarTableModel() { return kamarTableModel; }
    public DefaultTableModel getRuangMeetingTableModel() { return ruangMeetingTableModel; }
}
