package propensi.tugas.pebelco.utils.PerluDikirim;

public class Barang {
    private String namaBarang;
    private int jumlah;

    public Barang(String namaBarang, int jumlah) {
        this.namaBarang = namaBarang;
        this.jumlah = jumlah;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public int getJumlah() {
        return jumlah;
    }
}
