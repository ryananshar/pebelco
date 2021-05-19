package propensi.tugas.pebelco.utils.Pengiriman;

import propensi.tugas.pebelco.model.PengirimanModel;

import java.util.Date;

public class Pengiriman {
    private Long id;
    private String kodePengiriman;
    private String kode;
    private String namaToko;
    private Date tanggalDibuat;
    private int statusId;
    private String status;
    private String metodePengiriman;
    private String alamatToko;
    private Date tanggalDikirim;
    private Date tanggalDiterima;
    private String namaPenerima;
    private boolean isShown;

    public Pengiriman(PengirimanModel pengiriman) {
        this.id = pengiriman.getIdPengiriman();
        this.kodePengiriman = pengiriman.getKodePengiriman();
        this.kode = getKode(pengiriman);
        this.namaToko = pengiriman.getNamaToko();
        this.tanggalDibuat = pengiriman.getTanggalDibuat();
        this.statusId = pengiriman.getStatusPengiriman();
        this.status = getStatusString(statusId);
        this.metodePengiriman = pengiriman.getMetodePengiriman().getNamaMetodePengiriman();
        this.alamatToko = pengiriman.getAlamatToko();
        this.tanggalDikirim = pengiriman.getTanggalDikirim();
        this.tanggalDiterima = pengiriman.getTanggalDiterima();
        this.namaPenerima = pengiriman.getNamaPenerima();
        this.isShown = pengiriman.getIsShown();
    }

    public boolean isShown() {
        return isShown;
    }

    public void setShown(boolean shown) {
        isShown = shown;
    }

    private String getKode(PengirimanModel pengiriman) {
        try {
            return pengiriman.getKomplain().getKodeKomplain();
        } catch (NullPointerException e) {
            return pengiriman.getPesananPenjualan().getKodePesananPenjualan();
        }
    }

    private String getStatusString(int status) {
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

    public int getNextStatusId() {
        return this.statusId + 1;
    }

    public String getNextStatus() {
        return getStatusString(statusId + 1);
    }

    public Long getId() {
        return id;
    }

    public String getKodePengiriman() {
        return kodePengiriman;
    }

    public String getStatus() {
        return status;
    }

    public int getStatusId() {
        return statusId;
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

    public boolean getDisableHapus() {
        return this.statusId != 3;
    }
}
