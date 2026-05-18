package view;

import controller.AuthController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class RegisterFrame extends JFrame {
    private JTextField txtNamaLengkap;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtKonfirmasiPassword;
    private JButton btnRegister;
    private JButton btnLogin;
    private AuthController authController;

    public RegisterFrame() {
        this.authController = new AuthController();
        initComponents();
    }

    private void initComponents() {
        setTitle("Hotel Nusantara - Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(26, 54, 93),
                    0, getHeight(), new Color(13, 27, 42)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new GridLayout(3, 1, 0, 5));
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(35, 40, 15, 40));

        JLabel lblIcon = new JLabel("🏨", SwingConstants.CENTER);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));

        JLabel lblTitle = new JLabel("HOTEL NUSANTARA", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(new Color(212, 175, 55));

        JLabel lblSubtitle = new JLabel("Buat Akun Baru", SwingConstants.CENTER);
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSubtitle.setForeground(new Color(180, 180, 180));

        headerPanel.add(lblIcon);
        headerPanel.add(lblTitle);
        headerPanel.add(lblSubtitle);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        formPanel.setBorder(new EmptyBorder(10, 50, 30, 50));

        JPanel cardPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(255, 255, 255, 20));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2d.setColor(new Color(212, 175, 55, 80));
                g2d.setStroke(new BasicStroke(1));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            }
        };
        cardPanel.setOpaque(false);
        cardPanel.setBorder(new EmptyBorder(25, 25, 25, 25));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 0, 5, 0);

        // Title
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblRegister = new JLabel("DAFTAR");
        lblRegister.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblRegister.setForeground(Color.WHITE);
        cardPanel.add(lblRegister, gbc);

        // Nama Lengkap
        gbc.gridy = 1;
        cardPanel.add(createLabel("Nama Lengkap"), gbc);
        gbc.gridy = 2;
        txtNamaLengkap = createStyledTextField();
        cardPanel.add(txtNamaLengkap, gbc);

        // Username
        gbc.gridy = 3;
        cardPanel.add(createLabel("Username"), gbc);
        gbc.gridy = 4;
        txtUsername = createStyledTextField();
        cardPanel.add(txtUsername, gbc);

        // Password
        gbc.gridy = 5;
        cardPanel.add(createLabel("Password"), gbc);
        gbc.gridy = 6;
        txtPassword = createStyledPasswordField();
        cardPanel.add(txtPassword, gbc);

        // Konfirmasi Password
        gbc.gridy = 7;
        cardPanel.add(createLabel("Konfirmasi Password"), gbc);
        gbc.gridy = 8;
        txtKonfirmasiPassword = createStyledPasswordField();
        cardPanel.add(txtKonfirmasiPassword, gbc);

        // Register button
        gbc.gridy = 9;
        gbc.insets = new Insets(16, 0, 6, 0);
        btnRegister = createPrimaryButton("Daftar");
        cardPanel.add(btnRegister, gbc);

        // Login button
        gbc.gridy = 10;
        gbc.insets = new Insets(4, 0, 0, 0);
        btnLogin = createSecondaryButton("Sudah punya akun? Masuk");
        cardPanel.add(btnLogin, gbc);

        GridBagConstraints mainGbc = new GridBagConstraints();
        mainGbc.fill = GridBagConstraints.HORIZONTAL;
        mainGbc.weightx = 1.0;
        formPanel.add(cardPanel, mainGbc);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);

        // Event Handling
        btnRegister.addActionListener(e -> doRegister());
        btnLogin.addActionListener(e -> authController.showLogin(this));

        txtKonfirmasiPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) doRegister();
            }
        });
    }

    private void doRegister() {
        authController.doRegister(
            txtNamaLengkap.getText(),
            txtUsername.getText(),
            new String(txtPassword.getPassword()),
            new String(txtKonfirmasiPassword.getPassword()),
            this
        );
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(new Color(180, 180, 180));
        return lbl;
    }

    private JTextField createStyledTextField() {
        JTextField tf = new JTextField(20);
        tf.setBackground(new Color(50, 80, 120));
        tf.setForeground(Color.WHITE);
        tf.setCaretColor(Color.WHITE);
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(212, 175, 55, 120), 1, true),
            new EmptyBorder(8, 12, 8, 12)
        ));
        return tf;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField pf = new JPasswordField(20);
        pf.setBackground(new Color(50, 80, 120));
        pf.setForeground(Color.WHITE);
        pf.setCaretColor(Color.WHITE);
        pf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(212, 175, 55, 120), 1, true),
            new EmptyBorder(8, 12, 8, 12)
        ));
        return pf;
    }

    private JButton createPrimaryButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2d.setColor(new Color(180, 145, 30));
                } else if (getModel().isRollover()) {
                    g2d.setColor(new Color(230, 190, 60));
                } else {
                    g2d.setColor(new Color(212, 175, 55));
                }
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2d.setColor(new Color(26, 54, 93));
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                g2d.drawString(getText(), x, y);
            }
        };
        btn.setPreferredSize(new Dimension(100, 42));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JButton createSecondaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn.setForeground(new Color(212, 175, 55));
        btn.setBackground(new Color(0, 0, 0, 0));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
