package model;

public class RuangMeeting extends ItemHotel {
    private int kapasitasOrang;
    private String fasilitas;

    public RuangMeeting() {}

    public RuangMeeting(int id, String namaItem, double hargaPerMalam, int kapasitasOrang, String fasilitas) {
        super(id, namaItem, hargaPerMalam);
        this.kapasitasOrang = kapasitasOrang;
        this.fasilitas = fasilitas;
    }

    public RuangMeeting(String namaItem, double hargaPerMalam, int kapasitasOrang, String fasilitas) {
        super(namaItem, hargaPerMalam);
        this.kapasitasOrang = kapasitasOrang;
        this.fasilitas = fasilitas;
    }

    public int getKapasitasOrang() { return kapasitasOrang; }
    public void setKapasitasOrang(int kapasitasOrang) { this.kapasitasOrang = kapasitasOrang; }

    public String getFasilitas() { return fasilitas; }
    public void setFasilitas(String fasilitas) { this.fasilitas = fasilitas; }

    @Override
    public String getTipe() { return "Ruang Meeting"; }

    @Override
    public String toString() {
        return "RuangMeeting{id=" + getId() + ", namaItem='" + getNamaItem() +
               "', hargaPerMalam=" + getHargaPerMalam() +
               ", kapasitasOrang=" + kapasitasOrang + ", fasilitas='" + fasilitas + "'}";
    }
}
