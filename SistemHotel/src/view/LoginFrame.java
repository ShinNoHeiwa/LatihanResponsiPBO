package view;

import controller.AuthController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnRegister;
    private AuthController authController;

    public LoginFrame() {
        this.authController = new AuthController();
        initComponents();
    }

    private void initComponents() {
        setTitle("Hotel Nusantara - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 520);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel with gradient background
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

        // Header panel
        JPanel headerPanel = new JPanel(new GridLayout(3, 1, 0, 5));
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(40, 40, 20, 40));

        JLabel lblIcon = new JLabel("🏨", SwingConstants.CENTER);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));

        JLabel lblTitle = new JLabel("HOTEL NUSANTARA", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(212, 175, 55));

        JLabel lblSubtitle = new JLabel("Sistem Manajemen Hotel", SwingConstants.CENTER);
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSubtitle.setForeground(new Color(180, 180, 180));

        headerPanel.add(lblIcon);
        headerPanel.add(lblTitle);
        headerPanel.add(lblSubtitle);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        formPanel.setBorder(new EmptyBorder(10, 50, 30, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 0);

        // Card panel for form
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

        GridBagConstraints cardGbc = new GridBagConstraints();
        cardGbc.fill = GridBagConstraints.HORIZONTAL;
        cardGbc.weightx = 1.0;
        cardGbc.insets = new Insets(6, 0, 6, 0);

        // Login label
        cardGbc.gridx = 0; cardGbc.gridy = 0;
        JLabel lblLogin = new JLabel("MASUK");
        lblLogin.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblLogin.setForeground(Color.WHITE);
        cardPanel.add(lblLogin, cardGbc);

        // Username label
        cardGbc.gridy = 1;
        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblUsername.setForeground(new Color(180, 180, 180));
        cardPanel.add(lblUsername, cardGbc);

        // Username field
        cardGbc.gridy = 2;
        txtUsername = createStyledTextField();
        cardPanel.add(txtUsername, cardGbc);

        // Password label
        cardGbc.gridy = 3;
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblPassword.setForeground(new Color(180, 180, 180));
        cardPanel.add(lblPassword, cardGbc);

        // Password field
        cardGbc.gridy = 4;
        txtPassword = createStyledPasswordField();
        cardPanel.add(txtPassword, cardGbc);

        // Login button
        cardGbc.gridy = 5;
        cardGbc.insets = new Insets(16, 0, 6, 0);
        btnLogin = createPrimaryButton("Login");
        cardPanel.add(btnLogin, cardGbc);

        // Register button
        cardGbc.gridy = 6;
        cardGbc.insets = new Insets(6, 0, 0, 0);
        btnRegister = createSecondaryButton("Belum punya akun? Daftar");
        cardPanel.add(btnRegister, cardGbc);

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.weightx = 1.0;
        formPanel.add(cardPanel, gbc);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);

        // Event Handling
        btnLogin.addActionListener(e -> doLogin());
        btnRegister.addActionListener(e -> authController.showRegister(this));

        txtPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) doLogin();
            }
        });

        txtUsername.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) txtPassword.requestFocus();
            }
        });
    }

    private void doLogin() {
        authController.doLogin(
            txtUsername.getText(),
            new String(txtPassword.getPassword()),
            this
        );
        txtPassword.setText("");
    }

    private JTextField createStyledTextField() {
        JTextField tf = new JTextField(20);
        tf.setBackground(new Color(255, 255, 255, 30));
        tf.setForeground(Color.WHITE);
        tf.setCaretColor(Color.WHITE);
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(212, 175, 55, 120), 1, true),
            new EmptyBorder(8, 12, 8, 12)
        ));
        tf.setOpaque(true);
        return tf;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField pf = new JPasswordField(20);
        pf.setBackground(new Color(255, 255, 255, 30));
        pf.setForeground(Color.WHITE);
        pf.setCaretColor(Color.WHITE);
        pf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(212, 175, 55, 120), 1, true),
            new EmptyBorder(8, 12, 8, 12)
        ));
        pf.setOpaque(true);
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
