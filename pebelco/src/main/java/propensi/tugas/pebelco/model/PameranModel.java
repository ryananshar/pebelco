package propensi.tugas.pebelco.model;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="pameran")
public class PameranModel implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pameran", nullable = false)
    private Long idPameran;

    @NotNull
    @Size(max = 50)
    @Column(name = "nama_pameran", nullable = false)
    private String namaPameran;

    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "tanggal_mulai", nullable = false)
    private Date tanggalMulai;

    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "tanggal_selesai", nullable = false)
    private Date tanggalSelesai;

    @NotNull
    @Size(max = 250)
    @Column(name = "alamat_pameran", nullable = false)
    private String alamatPameran;

    @Size(max = 250)
    @Column(name = "catatan_pameran", nullable = true)
    private String catatanPameran;

    @NotNull
    @Column(name = "tipe_pameran", nullable = false)
    private Integer tipePameran;

    @NotNull
    @Column(name = "waktu_mulai", nullable = false)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime waktuMulai;

    @NotNull
    @Column(name = "waktu_selesai", nullable = false)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime waktuSelesai;


    public Long getIdPameran() {
        return this.idPameran;
    }

    public void setIdPameran(Long idPameran) {
        this.idPameran = idPameran;
    }

    public String getNamaPameran() {
        return this.namaPameran;
    }

    public void setNamaPameran(String namaPameran) {
        this.namaPameran = namaPameran;
    }

    public Date getTanggalMulai() {
        return this.tanggalMulai;
    }

    public void setTanggalMulai(Date tanggalMulai) {
        this.tanggalMulai = tanggalMulai;
    }

    public Date getTanggalSelesai() {
        return this.tanggalSelesai;
    }

    public void setTanggalSelesai(Date tanggalSelesai) {
        this.tanggalSelesai = tanggalSelesai;
    }

    public String getAlamatPameran() {
        return this.alamatPameran;
    }

    public void setAlamatPameran(String alamatPameran) {
        this.alamatPameran = alamatPameran;
    }

    public String getCatatanPameran() {
        return this.catatanPameran;
    }

    public void setCatatanPameran(String catatanPameran) {
        this.catatanPameran = catatanPameran;
    }

    public Integer getTipePameran() {
        return this.tipePameran;
    }

    public void setTipePameran(Integer tipePameran) {
        this.tipePameran = tipePameran;
    }

    public LocalTime getWaktuMulai() {
        return this.waktuMulai;
    }

    public void setWaktuMulai(LocalTime waktuMulai) {
        this.waktuMulai = waktuMulai;
    }

    public LocalTime getWaktuSelesai() {
        return this.waktuSelesai;
    }

    public void setWaktuSelesai(LocalTime waktuSelesai) {
        this.waktuSelesai = waktuSelesai;
    }

}
