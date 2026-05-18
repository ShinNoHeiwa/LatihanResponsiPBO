package model;

public abstract class ItemHotel {
    private int id;
    private String namaItem;
    private double hargaPerMalam;

    public ItemHotel() {}

    public ItemHotel(int id, String namaItem, double hargaPerMalam) {
        this.id = id;
        this.namaItem = namaItem;
        this.hargaPerMalam = hargaPerMalam;
    }

    public ItemHotel(String namaItem, double hargaPerMalam) {
        this.namaItem = namaItem;
        this.hargaPerMalam = hargaPerMalam;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNamaItem() { return namaItem; }
    public void setNamaItem(String namaItem) { this.namaItem = namaItem; }

    public double getHargaPerMalam() { return hargaPerMalam; }
    public void setHargaPerMalam(double hargaPerMalam) { this.hargaPerMalam = hargaPerMalam; }

    public abstract String getTipe();

    @Override
    public String toString() {
        return "ItemHotel{id=" + id + ", namaItem='" + namaItem + "', hargaPerMalam=" + hargaPerMalam + "}";
    }
}
