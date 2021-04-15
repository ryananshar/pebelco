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
@Table(name="pesanan_penjualan")
public class PesananPenjualanModel implements Serializable{
    @Id
    @Column(name = "id_pesanan_penjualan")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPesananPenjualan;

    @NotNull
    @Size(max = 15)
    @Column(name = "kode_pesanan_penjualan", nullable = false)
    private String kodePesananPenjualan;

    @NotNull
    @Column(name = "status_pesanan", nullable = false)
    private Integer statusPesanan;

    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "tanggal_pesanan", nullable = false)
    private Date tanggalPesanan;

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

    @Column(name = "diskon", nullable = true)
    private Integer diskon;

    // @NotNull
    @Column(name = "total_harga", nullable = true)
    private Long totalHarga;

    @Size(max = 250)
    @Column(name = "request_change", nullable = true)
    private String requestChange;

    @NotNull
    @Column(name = "is_shown", nullable = false)
    private Boolean isShown;

    // id user -> staf sales / sales counter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private UserModel user;

    @OneToMany(mappedBy = "pesananKomplain", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<KomplainModel> listKomplain;

    @OneToOne(mappedBy = "pesananPenjualan", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private PengirimanModel pengirimanPesanan;

    @OneToOne(mappedBy = "pesananPenjualan", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private LaporanStafSalesModel laporanStafSales;

    @OneToMany(
        targetEntity = TransaksiPesananModel.class,
        cascade = CascadeType.ALL, orphanRemoval = true,
        mappedBy = "pesananTransaksi", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<TransaksiPesananModel> barangPesanan;


    public Long getIdPesananPenjualan() {
        return this.idPesananPenjualan;
    }

    public void setIdPesananPenjualan(Long idPesananPenjualan) {
        this.idPesananPenjualan = idPesananPenjualan;
    }

    public String getKodePesananPenjualan() {
        return this.kodePesananPenjualan;
    }

    public void setKodePesananPenjualan(String kodePesananPenjualan) {
        this.kodePesananPenjualan = kodePesananPenjualan;
    }

    public Integer getStatusPesanan() {
        return this.statusPesanan;
    }

    public void setStatusPesanan(Integer statusPesanan) {
        this.statusPesanan = statusPesanan;
    }

    public Date getTanggalPesanan() {
        return this.tanggalPesanan;
    }

    public void setTanggalPesanan(Date tanggalPesanan) {
        this.tanggalPesanan = tanggalPesanan;
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

    public Integer getDiskon() {
        return this.diskon;
    }

    public void setDiskon(Integer diskon) {
        this.diskon = diskon;
    }

    public Long getTotalHarga() {
        return this.totalHarga;
    }

    public void setTotalHarga(Long totalHarga) {
        this.totalHarga = totalHarga;
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

    public UserModel getUser() {
        return this.user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public List<KomplainModel> getListKomplain() {
        return this.listKomplain;
    }

    public void setListKomplain(List<KomplainModel> listKomplain) {
        this.listKomplain = listKomplain;
    }

    public PengirimanModel getPengirimanPesanan() {
        return this.pengirimanPesanan;
    }

    public void setPengirimanPesanan(PengirimanModel pengirimanPesanan) {
        this.pengirimanPesanan = pengirimanPesanan;
    }

    public LaporanStafSalesModel getLaporanStafSales() {
        return this.laporanStafSales;
    }

    public void setLaporanStafSales(LaporanStafSalesModel laporanStafSales) {
        this.laporanStafSales = laporanStafSales;
    }

    public List<TransaksiPesananModel> getBarangPesanan() {
        return this.barangPesanan;
    }

    public void setBarangPesanan(List<TransaksiPesananModel> barangPesanan) {
        this.barangPesanan = barangPesanan;
    }

}
