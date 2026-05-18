package model;

public class Kamar extends ItemHotel {
    private String tipeKamar;
    private int kapasitasTamu;

    public Kamar() {}

    public Kamar(int id, String namaItem, double hargaPerMalam, String tipeKamar, int kapasitasTamu) {
        super(id, namaItem, hargaPerMalam);
        this.tipeKamar = tipeKamar;
        this.kapasitasTamu = kapasitasTamu;
    }

    public Kamar(String namaItem, double hargaPerMalam, String tipeKamar, int kapasitasTamu) {
        super(namaItem, hargaPerMalam);
        this.tipeKamar = tipeKamar;
        this.kapasitasTamu = kapasitasTamu;
    }

    public String getTipeKamar() { return tipeKamar; }
    public void setTipeKamar(String tipeKamar) { this.tipeKamar = tipeKamar; }

    public int getKapasitasTamu() { return kapasitasTamu; }
    public void setKapasitasTamu(int kapasitasTamu) { this.kapasitasTamu = kapasitasTamu; }

    @Override
    public String getTipe() { return "Kamar"; }

    @Override
    public String toString() {
        return "Kamar{id=" + getId() + ", namaItem='" + getNamaItem() +
               "', hargaPerMalam=" + getHargaPerMalam() +
               ", tipeKamar='" + tipeKamar + "', kapasitasTamu=" + kapasitasTamu + "}";
    }
}
