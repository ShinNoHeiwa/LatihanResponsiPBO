package model;

import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO implements Bookable {

    // ===================== KAMAR =====================

    public boolean tambahKamar(Kamar kamar) {
        String sql = "INSERT INTO kamar (nama_item, harga_per_malam, tipe_kamar, kapasitas_tamu) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, kamar.getNamaItem());
            stmt.setDouble(2, kamar.getHargaPerMalam());
            stmt.setString(3, kamar.getTipeKamar());
            stmt.setInt(4, kamar.getKapasitasTamu());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error tambah kamar: " + e.getMessage());
            return false;
        }
    }

    public List<Kamar> getAllKamar() {
        List<Kamar> list = new ArrayList<>();
        String sql = "SELECT * FROM kamar ORDER BY id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Kamar(
                    rs.getInt("id"),
                    rs.getString("nama_item"),
                    rs.getDouble("harga_per_malam"),
                    rs.getString("tipe_kamar"),
                    rs.getInt("kapasitas_tamu")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error get all kamar: " + e.getMessage());
        }
        return list;
    }

    public List<Kamar> cariKamar(String keyword) {
        List<Kamar> list = new ArrayList<>();
        String sql = "SELECT * FROM kamar WHERE nama_item LIKE ? OR tipe_kamar LIKE ? ORDER BY id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String kw = "%" + keyword + "%";
            stmt.setString(1, kw);
            stmt.setString(2, kw);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Kamar(
                    rs.getInt("id"),
                    rs.getString("nama_item"),
                    rs.getDouble("harga_per_malam"),
                    rs.getString("tipe_kamar"),
                    rs.getInt("kapasitas_tamu")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error cari kamar: " + e.getMessage());
        }
        return list;
    }

    public boolean updateKamar(Kamar kamar) {
        String sql = "UPDATE kamar SET nama_item=?, harga_per_malam=?, tipe_kamar=?, kapasitas_tamu=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, kamar.getNamaItem());
            stmt.setDouble(2, kamar.getHargaPerMalam());
            stmt.setString(3, kamar.getTipeKamar());
            stmt.setInt(4, kamar.getKapasitasTamu());
            stmt.setInt(5, kamar.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error update kamar: " + e.getMessage());
            return false;
        }
    }

    public boolean hapusKamar(int id) {
        String sql = "DELETE FROM kamar WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error hapus kamar: " + e.getMessage());
            return false;
        }
    }

    // ===================== RUANG MEETING =====================

    public boolean tambahRuangMeeting(RuangMeeting rm) {
        String sql = "INSERT INTO ruang_meeting (nama_item, harga_per_malam, kapasitas_orang, fasilitas) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, rm.getNamaItem());
            stmt.setDouble(2, rm.getHargaPerMalam());
            stmt.setInt(3, rm.getKapasitasOrang());
            stmt.setString(4, rm.getFasilitas());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error tambah ruang meeting: " + e.getMessage());
            return false;
        }
    }

    public List<RuangMeeting> getAllRuangMeeting() {
        List<RuangMeeting> list = new ArrayList<>();
        String sql = "SELECT * FROM ruang_meeting ORDER BY id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new RuangMeeting(
                    rs.getInt("id"),
                    rs.getString("nama_item"),
                    rs.getDouble("harga_per_malam"),
                    rs.getInt("kapasitas_orang"),
                    rs.getString("fasilitas")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error get all ruang meeting: " + e.getMessage());
        }
        return list;
    }

    public List<RuangMeeting> cariRuangMeeting(String keyword) {
        List<RuangMeeting> list = new ArrayList<>();
        String sql = "SELECT * FROM ruang_meeting WHERE nama_item LIKE ? OR fasilitas LIKE ? ORDER BY id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String kw = "%" + keyword + "%";
            stmt.setString(1, kw);
            stmt.setString(2, kw);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new RuangMeeting(
                    rs.getInt("id"),
                    rs.getString("nama_item"),
                    rs.getDouble("harga_per_malam"),
                    rs.getInt("kapasitas_orang"),
                    rs.getString("fasilitas")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error cari ruang meeting: " + e.getMessage());
        }
        return list;
    }

    public boolean updateRuangMeeting(RuangMeeting rm) {
        String sql = "UPDATE ruang_meeting SET nama_item=?, harga_per_malam=?, kapasitas_orang=?, fasilitas=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, rm.getNamaItem());
            stmt.setDouble(2, rm.getHargaPerMalam());
            stmt.setInt(3, rm.getKapasitasOrang());
            stmt.setString(4, rm.getFasilitas());
            stmt.setInt(5, rm.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error update ruang meeting: " + e.getMessage());
            return false;
        }
    }

    public boolean hapusRuangMeeting(int id) {
        String sql = "DELETE FROM ruang_meeting WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error hapus ruang meeting: " + e.getMessage());
            return false;
        }
    }

    // ===================== BOOKABLE INTERFACE =====================

    @Override
    public boolean booking(int itemId, String namaTamu, String tanggalCheckIn, String tanggalCheckOut) {
        String sql = "INSERT INTO reservasi (item_id, nama_tamu, tanggal_check_in, tanggal_check_out, status) VALUES (?, ?, ?, ?, 'aktif')";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, itemId);
            stmt.setString(2, namaTamu);
            stmt.setString(3, tanggalCheckIn);
            stmt.setString(4, tanggalCheckOut);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error booking: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean batalBooking(int reservasiId) {
        String sql = "UPDATE reservasi SET status='batal' WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reservasiId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error batal booking: " + e.getMessage());
            return false;
        }
    }
}
