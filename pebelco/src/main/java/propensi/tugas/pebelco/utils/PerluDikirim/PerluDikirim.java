package propensi.tugas.pebelco.utils.PerluDikirim;

import java.util.Date;

public abstract class PerluDikirim {
    protected Long id;
    protected String kode;
    protected String namaToko;
    protected String alamatToko;
    protected Date tanggalPersetujuan;
    protected String status;
    protected String type;

    public PerluDikirim(Long id, String kode, String namaToko, String alamatToko, Date tanggalPersetujuan, int status, String type) {
        this.id = id;
        this.kode = kode;
        this.namaToko = namaToko;
        this.alamatToko = alamatToko;
        this.tanggalPersetujuan = tanggalPersetujuan;
        this.status = getStatusFromStatusNumber(status);
        this.type = type;
    }

    private String getStatusFromStatusNumber(int number) {
        if (number == 1 || number == 3 || number == 4){
            return "Disetujui";
        } else if (number == 2) {
            return "Ditolak";
        } else if (number == 0) {
            return "Menunggu Persetujuan";
        } else {
            return "Not a status";
        }
    }

    public Long getId() {
        return id;
    }

    public String getKode() {
        return kode;
    }

    public String getNamaToko() {
        return namaToko;
    }

    public String getAlamatToko() {
        return alamatToko;
    }

    public Date getTanggalPersetujuan() {
        return tanggalPersetujuan;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }
}
