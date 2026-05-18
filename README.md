public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginBtn, registerBtn;
    private AuthController authController;
    private int percobaan = 0;

    public LoginFrame() {
        authController = new AuthController();
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Login - Hotel Nusantara");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        // tambahkan label dan field dengan gbc...

        loginBtn.addActionListener(e -> handleLogin());
        registerBtn.addActionListener(e -> {
            new RegisterFrame().setVisible(true);
            dispose();
        });
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (authController.login(username, password)) {
            new MainFrame().setVisible(true);
            dispose();
        } else {
            percobaan++;
            if (percobaan >= 3) {
                JOptionPane.showMessageDialog(this, "Gagal 3 kali. Program ditutup.");
                System.exit(0);
            } else {
                JOptionPane.showMessageDialog(this, "Username/password salah. Percobaan ke-" + percobaan);
            }
        }
    }
}
