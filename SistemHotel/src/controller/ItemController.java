package controller;

import model.*;
import view.FormDialog;
import view.MainFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ItemController {
    private ItemDAO itemDAO;
    private MainFrame mainFrame;

    public ItemController(MainFrame mainFrame) {
        this.itemDAO = new ItemDAO();
        this.mainFrame = mainFrame;
    }

    // ===================== KAMAR =====================

    public void loadKamar() {
        List<Kamar> list = itemDAO.getAllKamar();
        DefaultTableModel model = mainFrame.getKamarTableModel();
        model.setRowCount(0);
        NumberFormat nf = NumberFormat.getInstance(new Locale("id", "ID"));
        for (Kamar k : list) {
            model.addRow(new Object[]{
                k.getId(),
                k.getNamaItem(),
                "Rp " + nf.format(k.getHargaPerMalam()),
                k.getTipeKamar(),
                k.getKapasitasTamu() + " orang"
            });
        }
    }

    public void tambahKamar() {
        FormDialog dialog = new FormDialog(mainFrame, "Tambah Kamar", "kamar", null);
        dialog.setVisible(true);
        if (dialog.isSubmitted()) {
            Kamar kamar = new Kamar(
                dialog.getNamaItem(),
                dialog.getHargaPerMalam(),
                dialog.getTipeKamar(),
                dialog.getKapasitasTamu()
            );
            boolean success = itemDAO.tambahKamar(kamar);
            if (success) {
                JOptionPane.showMessageDialog(mainFrame, "Data kamar berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                loadKamar();
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Gagal menambahkan data kamar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void editKamar(int selectedRow) {
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(mainFrame, "Pilih data kamar yang ingin diedit!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        DefaultTableModel model = mainFrame.getKamarTableModel();
        int id = (int) model.getValueAt(selectedRow, 0);
        String nama = (String) model.getValueAt(selectedRow, 1);
        String hargaStr = ((String) model.getValueAt(selectedRow, 2)).replace("Rp ", "").replace(".", "").replace(",", "");
        double harga = Double.parseDouble(hargaStr);
        String tipe = (String) model.getValueAt(selectedRow, 3);
        String kapStr = ((String) model.getValueAt(selectedRow, 4)).replace(" orang", "");
        int kapasitas = Integer.parseInt(kapStr);

        Kamar existing = new Kamar(id, nama, harga, tipe, kapasitas);
        FormDialog dialog = new FormDialog(mainFrame, "Edit Kamar", "kamar", existing);
        dialog.setVisible(true);

        if (dialog.isSubmitted()) {
            Kamar updated = new Kamar(
                id,
                dialog.getNamaItem(),
                dialog.getHargaPerMalam(),
                dialog.getTipeKamar(),
                dialog.getKapasitasTamu()
            );
            boolean success = itemDAO.updateKamar(updated);
            if (success) {
                JOptionPane.showMessageDialog(mainFrame, "Data kamar berhasil diupdate!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                loadKamar();
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Gagal mengupdate data kamar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void hapusKamar(int selectedRow) {
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(mainFrame, "Pilih data kamar yang ingin dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        DefaultTableModel model = mainFrame.getKamarTableModel();
        int id = (int) model.getValueAt(selectedRow, 0);
        String nama = (String) model.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(mainFrame,
            "Yakin ingin menghapus kamar '" + nama + "'?",
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = itemDAO.hapusKamar(id);
            if (success) {
                JOptionPane.showMessageDialog(mainFrame, "Data kamar berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                loadKamar();
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Gagal menghapus data kamar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void cariKamar(String keyword) {
        List<Kamar> list = itemDAO.cariKamar(keyword);
        DefaultTableModel model = mainFrame.getKamarTableModel();
        model.setRowCount(0);
        NumberFormat nf = NumberFormat.getInstance(new Locale("id", "ID"));
        for (Kamar k : list) {
            model.addRow(new Object[]{
                k.getId(),
                k.getNamaItem(),
                "Rp " + nf.format(k.getHargaPerMalam()),
                k.getTipeKamar(),
                k.getKapasitasTamu() + " orang"
            });
        }
    }

    // ===================== RUANG MEETING =====================

    public void loadRuangMeeting() {
        List<RuangMeeting> list = itemDAO.getAllRuangMeeting();
        DefaultTableModel model = mainFrame.getRuangMeetingTableModel();
        model.setRowCount(0);
        NumberFormat nf = NumberFormat.getInstance(new Locale("id", "ID"));
        for (RuangMeeting rm : list) {
            model.addRow(new Object[]{
                rm.getId(),
                rm.getNamaItem(),
                "Rp " + nf.format(rm.getHargaPerMalam()),
                rm.getKapasitasOrang() + " orang",
                rm.getFasilitas()
            });
        }
    }

    public void tambahRuangMeeting() {
        FormDialog dialog = new FormDialog(mainFrame, "Tambah Ruang Meeting", "ruang_meeting", null);
        dialog.setVisible(true);
        if (dialog.isSubmitted()) {
            RuangMeeting rm = new RuangMeeting(
                dialog.getNamaItem(),
                dialog.getHargaPerMalam(),
                dialog.getKapasitasOrang(),
                dialog.getFasilitas()
            );
            boolean success = itemDAO.tambahRuangMeeting(rm);
            if (success) {
                JOptionPane.showMessageDialog(mainFrame, "Data ruang meeting berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                loadRuangMeeting();
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Gagal menambahkan data ruang meeting.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void editRuangMeeting(int selectedRow) {
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(mainFrame, "Pilih data ruang meeting yang ingin diedit!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        DefaultTableModel model = mainFrame.getRuangMeetingTableModel();
        int id = (int) model.getValueAt(selectedRow, 0);
        String nama = (String) model.getValueAt(selectedRow, 1);
        String hargaStr = ((String) model.getValueAt(selectedRow, 2)).replace("Rp ", "").replace(".", "").replace(",", "");
        double harga = Double.parseDouble(hargaStr);
        String kapStr = ((String) model.getValueAt(selectedRow, 3)).replace(" orang", "");
        int kapasitas = Integer.parseInt(kapStr);
        String fasilitas = (String) model.getValueAt(selectedRow, 4);

        RuangMeeting existing = new RuangMeeting(id, nama, harga, kapasitas, fasilitas);
        FormDialog dialog = new FormDialog(mainFrame, "Edit Ruang Meeting", "ruang_meeting", existing);
        dialog.setVisible(true);

        if (dialog.isSubmitted()) {
            RuangMeeting updated = new RuangMeeting(
                id,
                dialog.getNamaItem(),
                dialog.getHargaPerMalam(),
                dialog.getKapasitasOrang(),
                dialog.getFasilitas()
            );
            boolean success = itemDAO.updateRuangMeeting(updated);
            if (success) {
                JOptionPane.showMessageDialog(mainFrame, "Data ruang meeting berhasil diupdate!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                loadRuangMeeting();
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Gagal mengupdate data ruang meeting.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void hapusRuangMeeting(int selectedRow) {
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(mainFrame, "Pilih data ruang meeting yang ingin dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        DefaultTableModel model = mainFrame.getRuangMeetingTableModel();
        int id = (int) model.getValueAt(selectedRow, 0);
        String nama = (String) model.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(mainFrame,
            "Yakin ingin menghapus ruang meeting '" + nama + "'?",
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = itemDAO.hapusRuangMeeting(id);
            if (success) {
                JOptionPane.showMessageDialog(mainFrame, "Data ruang meeting berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                loadRuangMeeting();
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Gagal menghapus data ruang meeting.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void cariRuangMeeting(String keyword) {
        List<RuangMeeting> list = itemDAO.cariRuangMeeting(keyword);
        DefaultTableModel model = mainFrame.getRuangMeetingTableModel();
        model.setRowCount(0);
        NumberFormat nf = NumberFormat.getInstance(new Locale("id", "ID"));
        for (RuangMeeting rm : list) {
            model.addRow(new Object[]{
                rm.getId(),
                rm.getNamaItem(),
                "Rp " + nf.format(rm.getHargaPerMalam()),
                rm.getKapasitasOrang() + " orang",
                rm.getFasilitas()
            });
        }
    }
}
