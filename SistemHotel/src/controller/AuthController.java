package controller;

import model.User;
import model.UserDAO;
import view.LoginFrame;
import view.MainFrame;
import view.RegisterFrame;

import javax.swing.*;

public class AuthController {
    private UserDAO userDAO;
    private int loginAttempts = 0;
    private static final int MAX_ATTEMPTS = 3;

    public AuthController() {
        this.userDAO = new UserDAO();
    }

    public void doLogin(String username, String password, LoginFrame loginFrame) {
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(loginFrame,
                "Username dan password tidak boleh kosong!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        User user = userDAO.login(username.trim(), password.trim());
        if (user != null) {
            loginAttempts = 0;
            JOptionPane.showMessageDialog(loginFrame,
                "Selamat datang, " + user.getNamaLengkap() + "!",
                "Login Berhasil", JOptionPane.INFORMATION_MESSAGE);
            loginFrame.dispose();
            new MainFrame(user).setVisible(true);
        } else {
            loginAttempts++;
            int sisa = MAX_ATTEMPTS - loginAttempts;
            if (loginAttempts >= MAX_ATTEMPTS) {
                JOptionPane.showMessageDialog(loginFrame,
                    "Anda telah melebihi batas percobaan login (3x).\nProgram akan ditutup.",
                    "Akses Ditolak", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            } else {
                JOptionPane.showMessageDialog(loginFrame,
                    "Username atau password salah!\nSisa percobaan: " + sisa,
                    "Login Gagal", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void doRegister(String namaLengkap, String username, String password, String konfirmasiPassword, RegisterFrame registerFrame) {
        if (namaLengkap.trim().isEmpty() || username.trim().isEmpty() ||
            password.trim().isEmpty() || konfirmasiPassword.trim().isEmpty()) {
            JOptionPane.showMessageDialog(registerFrame,
                "Semua field harus diisi!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!password.equals(konfirmasiPassword)) {
            JOptionPane.showMessageDialog(registerFrame,
                "Password dan konfirmasi password tidak cocok!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (userDAO.isUsernameExists(username.trim())) {
            JOptionPane.showMessageDialog(registerFrame,
                "Username '" + username.trim() + "' sudah digunakan. Pilih username lain.",
                "Username Sudah Ada", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User newUser = new User(namaLengkap.trim(), username.trim(), password.trim());
        boolean success = userDAO.register(newUser);

        if (success) {
            JOptionPane.showMessageDialog(registerFrame,
                "Registrasi berhasil! Silakan login.",
                "Registrasi Berhasil", JOptionPane.INFORMATION_MESSAGE);
            registerFrame.dispose();
            new LoginFrame().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(registerFrame,
                "Gagal melakukan registrasi. Coba lagi.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void showRegister(LoginFrame loginFrame) {
        loginFrame.dispose();
        new RegisterFrame().setVisible(true);
    }

    public void showLogin(RegisterFrame registerFrame) {
        registerFrame.dispose();
        new LoginFrame().setVisible(true);
    }
}
