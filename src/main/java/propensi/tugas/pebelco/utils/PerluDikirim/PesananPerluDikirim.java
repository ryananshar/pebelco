package propensi.tugas.pebelco.utils.PerluDikirim;

import propensi.tugas.pebelco.model.PesananPenjualanModel;

public class PesananPerluDikirim extends PerluDikirim {
    public PesananPerluDikirim(PesananPenjualanModel pesanan) {
        super(
                pesanan.getIdPesananPenjualan(),
                pesanan.getKodePesananPenjualan(),
                pesanan.getNamaToko(),
                pesanan.getAlamatToko(),
                pesanan.getTanggalPersetujuan(),
                pesanan.getStatusPesanan(),
                "pesanan"
        );
    }
}
