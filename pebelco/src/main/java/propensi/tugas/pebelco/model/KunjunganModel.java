package propensi.tugas.pebelco.model;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="kunjungan")
public class KunjunganModel implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_kunjungan", nullable = false)
    private Long idKunjungan;

    @NotNull
    @Size(max = 15)
    @Column(name = "kode_kunjungan", nullable = false)
    private String kodeKunjungan;

    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "tanggal_kunjungan", nullable = false)
    private Date tanggalKunjungan;

    @NotNull
    @Column(name = "waktu_mulai", nullable = false)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime waktuMulai;

    @NotNull
    @Column(name = "waktu_selesai", nullable = false)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime waktuSelesai;

    @NotNull
    @Size(max = 50)
    @Column(name = "nama_toko", nullable = false)
    private String namaToko;

    @NotNull
    @Size(max = 250)
    @Column(name = "alamat_Toko", nullable = false)
    private String alamatToko;

    @Size(max = 250)
    @Column(name = "catatan_Kunjungan", nullable = true)
    private String catatanKunjungan;

    @NotNull
    @Column(name = "is_shown", nullable = false)
    private Boolean isShown;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_staf_sales", referencedColumnName = "idUser", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private UserModel stafSales;


    public Long getIdKunjungan() {
        return this.idKunjungan;
    }

    public void setIdKunjungan(Long idKunjungan) {
        this.idKunjungan = idKunjungan;
    }

    public String getKodeKunjungan() {
        return this.kodeKunjungan;
    }

    public void setKodeKunjungan(String kodeKunjungan) {
        this.kodeKunjungan = kodeKunjungan;
    }

    public Date getTanggalKunjungan() {
        return this.tanggalKunjungan;
    }

    public void setTanggalKunjungan(Date tanggalKunjungan) {
        this.tanggalKunjungan = tanggalKunjungan;
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

    public String getNamaToko() {
        return this.namaToko;
    }

    public void setNamaToko(String namaToko) {
        this.namaToko = namaToko;
    }

    public String getAlamatToko() {
        return this.alamatToko;
    }

    public void setAlamatToko(String alamatToko) {
        this.alamatToko = alamatToko;
    }

    public String getCatatanKunjungan() {
        return this.catatanKunjungan;
    }

    public void setCatatanKunjungan(String catatanKunjungan) {
        this.catatanKunjungan = catatanKunjungan;
    }

    public Boolean isIsShown() {
        return this.isShown;
    }

    public Boolean getIsShown() {
        return this.isShown;
    }

    public void setIsShown(Boolean isShown) {
        this.isShown = isShown;
    }

    public UserModel getStafSales() {
        return this.stafSales;
    }

    public void setStafSales(UserModel stafSales) {
        this.stafSales = stafSales;
    }

}
