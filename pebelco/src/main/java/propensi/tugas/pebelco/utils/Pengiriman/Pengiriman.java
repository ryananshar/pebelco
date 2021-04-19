package propensi.tugas.pebelco.utils.Pengiriman;

import propensi.tugas.pebelco.model.PengirimanModel;

import java.util.Date;

public class Pengiriman {
    private Long id;
    private String kode;
    private String namaToko;
    private Date tanggalDibuat;
    private String status;
    private String metodePengiriman;
    private String alamatToko;
    private Date tanggalDikirim;
    private Date tanggalDiterima;
    private String namaPenerima;


    public Pengiriman(PengirimanModel pengiriman) {
        this.id = pengiriman.getIdPengiriman();
        this.kode = pengiriman.getKodePengiriman();
        this.namaToko = pengiriman.getNamaToko();
        this.tanggalDibuat = pengiriman.getTanggalDibuat();
        this.status = getStatus(pengiriman);
        this.metodePengiriman = pengiriman.getMetodePengiriman().getNamaMetodePengiriman();
        this.alamatToko = pengiriman.getAlamatToko();
        this.tanggalDikirim = pengiriman.getTanggalDikirim();
        this.tanggalDiterima = pengiriman.getTanggalDiterima();
        this.namaPenerima = pengiriman.getNamaPenerima();
    }

    private String getStatus(PengirimanModel pengiriman) {
        Integer status = pengiriman.getStatusPengiriman();
        if (status == 1) {
            return "Belum Dikirim";
        } else if (status == 2) {
            return "Dikirim";
        } else if (status == 3) {
            return "Diterima";
        } else {
            return "Not a status";
        }
    }

    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getNamaToko() {
        return namaToko;
    }

    public String getKode() {
        return kode;
    }

    public Date getTanggalDibuat() {
        return tanggalDibuat;
    }

    public String getAlamatToko() {
        return alamatToko;
    }

    public String getMetodePengiriman() {
        return metodePengiriman;
    }

    public Date getTanggalDikirim() {
        return tanggalDikirim;
    }

    public Date getTanggalDiterima() {
        return tanggalDiterima;
    }

    public String getNamaPenerima() {
        return namaPenerima;
    }
}
