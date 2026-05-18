package model;

import util.DatabaseConnection;
import java.sql.*;

public class UserDAO {
    
    public boolean cekLogin(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean isUsernameExists(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return true; // anggap ada error biar safety
        }
    }
    
    public boolean saveUser(User user) {
        String sql = "INSERT INTO users (nama_lengkap, username, password) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getNamaLengkap());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}        // tambahkan komponen...
        
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
