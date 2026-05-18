package model;

public interface Bookable {
    boolean booking(int itemId, String namaTamu, String tanggalCheckIn, String tanggalCheckOut);
    boolean batalBooking(int reservasiId);
}
