package propensi.tugas.pebelco.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="komplain")
public class KomplainModel implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_komplain", nullable = false)
    private Long idKomplain;

    @NotNull
    @Size(max = 15)
    @Column(name = "kode_komplain", nullable = false)
    private String kodeKomplain;

    @NotNull
    @Column(name = "status_komplain", nullable = false)
    private Integer statusKomplain;

    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "tanggal_komplain", nullable = false)
    private Date tanggalKomplain;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "tanggal_persetujuan", nullable = true)
    private Date tanggalPersetujuan;

    @NotNull
    @Size(max = 50)
    @Column(name = "nama_toko", nullable = false)
    private String namaToko;

    @NotNull
    @Size(max = 250)
    @Column(name = "alamat_Toko", nullable = false)
    private String alamatToko;

    @Size(max = 250)
    @Column(name = "desc_kerusakan", nullable = true)
    private String descKerusakan;

    @Size(max = 250)
    @Column(name = "request_change", nullable = true)
    private String requestChange;

    @NotNull
    @Column(name = "is_shown", nullable = false)
    private Boolean isShown;

    // id pesanan penjualan
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pesanan", referencedColumnName = "id_pesanan_penjualan", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private PesananPenjualanModel pesananKomplain;

    @OneToOne(mappedBy = "komplain", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private PengirimanModel pengirimanKomplain;

    // list barang komplain
    @OneToMany(mappedBy = "komplainTransaksi", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<TransaksiKomplainModel> barangKomplain;
    
    // @OneToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "id_komplain", referencedColumnName = "idKomplain", nullable = false)
    // @OnDelete(action = OnDeleteAction.CASCADE)
    // @JsonIgnore
    // private PengirimanModel pengirimanKomplain;

    public Long getIdKomplain() {
        return this.idKomplain;
    }

    public void setIdKomplain(Long idKomplain) {
        this.idKomplain = idKomplain;
    }

    public String getKodeKomplain() {
        return this.kodeKomplain;
    }

    public void setKodeKomplain(String kodeKomplain) {
        this.kodeKomplain = kodeKomplain;
    }

    public Integer getStatusKomplain() {
        return this.statusKomplain;
    }

    public void setStatusKomplain(Integer statusKomplain) {
        this.statusKomplain = statusKomplain;
    }

    public Date getTanggalKomplain() {
        return this.tanggalKomplain;
    }

    public void setTanggalKomplain(Date tanggalKomplain) {
        this.tanggalKomplain = tanggalKomplain;
    }

    public Date getTanggalPersetujuan() {
        return this.tanggalPersetujuan;
    }

    public void setTanggalPersetujuan(Date tanggalPersetujuan) {
        this.tanggalPersetujuan = tanggalPersetujuan;
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

    public String getDescKerusakan() {
        return this.descKerusakan;
    }

    public void setDescKerusakan(String descKerusakan) {
        this.descKerusakan = descKerusakan;
    }

    public String getRequestChange() {
        return this.requestChange;
    }

    public void setRequestChange(String requestChange) {
        this.requestChange = requestChange;
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

    public PesananPenjualanModel getPesananKomplain() {
        return this.pesananKomplain;
    }

    public void setPesananKomplain(PesananPenjualanModel pesananKomplain) {
        this.pesananKomplain = pesananKomplain;
    }

    public PengirimanModel getPengirimanKomplain() {
        return this.pengirimanKomplain;
    }

    public void setPengirimanKomplain(PengirimanModel pengirimanKomplain) {
        this.pengirimanKomplain = pengirimanKomplain;
    }

    public List<TransaksiKomplainModel> getBarangKomplain() {
        return this.barangKomplain;
    }

    public void setBarangKomplain(List<TransaksiKomplainModel> barangKomplain) {
        this.barangKomplain = barangKomplain;
    }

}
