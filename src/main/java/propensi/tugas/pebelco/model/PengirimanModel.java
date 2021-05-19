package propensi.tugas.pebelco.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="pengiriman")
public class PengirimanModel implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pengiriman", nullable = false)
    private Long idPengiriman;

    @NotNull
    @Size(max = 15)
    @Column(name = "kode_pengiriman", nullable = false)
    private String kodePengiriman;

    @NotNull
    @Column(name = "status_pengiriman", nullable = false)
    private Integer statusPengiriman;

    @NotNull
    @Size(max = 50)
    @Column(name = "nama_toko", nullable = false)
    private String namaToko;

    @NotNull
    @Size(max = 250)
    @Column(name = "alamat_Toko", nullable = false)
    private String alamatToko;

    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "tanggal_dibuat", nullable = false)
    private Date tanggalDibuat;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "tanggal_dikirim", nullable = true)
    private Date tanggalDikirim;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "tanggal_diterima", nullable = true)
    private Date tanggalDiterima;

    @Size(max = 50)
    @Column(name = "nama_penerima", nullable = true)
    private String namaPenerima;

    @NotNull
    @Column(name = "is_shown", nullable = false)
    private Boolean isShown;

    // id komplain
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_komplain", referencedColumnName = "id_komplain", nullable = true)
    @JsonIgnore
    private KomplainModel komplain;

    // id pesanan penjualan
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pesanan", referencedColumnName = "id_pesanan_penjualan", nullable = true)
    @JsonIgnore
    private PesananPenjualanModel pesananPenjualan;

    // id metode pengiriman
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_metode", referencedColumnName = "id_metode", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private MetodePengirimanModel metodePengiriman;

    // 
    // @Column(name = "i", nullable = true)
    // private List<TransaksiPesananModel> PengirimanPesanan;

    // status pengiriman

    // nama toko

    // alamat toko

    // tanggal2

    // barang pengiriman

    // @OneToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "id_komplain", referencedColumnName = "idKomplain", nullable = false)
    // @OnDelete(action = OnDeleteAction.CASCADE)
    // @JsonIgnore
    // private KomplainModel idKomplain;

    // @OneToMany(fetch = FetchType.EAGER)
    // @JoinColumn(name = "id_pesanan_penjualan", referencedColumnName = "idPesananaPenjualan", nullable = false)
    // @OnDelete(action = OnDeleteAction.CASCADE)
    // @JsonIgnore
    // private PesananPenjualanModel idPesananPenjualan;

    // @OneToMany(fetch = FetchType.EAGER)
    // @JoinColumn(name = "id_metode", referencedColumnName = "idMetode", nullable = false)
    // @OnDelete(action = OnDeleteAction.CASCADE)
    // @JsonIgnore
    // private MetodePengirimanModel idMetode;


    public Long getIdPengiriman() {
        return this.idPengiriman;
    }

    public void setIdPengiriman(Long idPengiriman) {
        this.idPengiriman = idPengiriman;
    }

    public String getKodePengiriman() {
        return this.kodePengiriman;
    }

    public void setKodePengiriman(String kodePengiriman) {
        this.kodePengiriman = kodePengiriman;
    }

    public Integer getStatusPengiriman() {
        return this.statusPengiriman;
    }

    public void setStatusPengiriman(Integer statusPengiriman) {
        this.statusPengiriman = statusPengiriman;
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

    public Date getTanggalDibuat() {
        return this.tanggalDibuat;
    }

    public void setTanggalDibuat(Date tanggalDibuat) {
        this.tanggalDibuat = tanggalDibuat;
    }

    public Date getTanggalDikirim() {
        return this.tanggalDikirim;
    }

    public void setTanggalDikirim(Date tanggalDikirim) {
        this.tanggalDikirim = tanggalDikirim;
    }

    public Date getTanggalDiterima() {
        return this.tanggalDiterima;
    }

    public void setTanggalDiterima(Date tanggalDiterima) {
        this.tanggalDiterima = tanggalDiterima;
    }

    public String getNamaPenerima() {
        return this.namaPenerima;
    }

    public void setNamaPenerima(String namaPenerima) {
        this.namaPenerima = namaPenerima;
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

    public KomplainModel getKomplain() {
        return this.komplain;
    }

    public void setKomplain(KomplainModel komplain) {
        this.komplain = komplain;
    }

    public PesananPenjualanModel getPesananPenjualan() {
        return this.pesananPenjualan;
    }

    public void setPesananPenjualan(PesananPenjualanModel pesananPenjualan) {
        this.pesananPenjualan = pesananPenjualan;
    }

    public MetodePengirimanModel getMetodePengiriman() {
        return this.metodePengiriman;
    }

    public void setMetodePengiriman(MetodePengirimanModel metodePengiriman) {
        this.metodePengiriman = metodePengiriman;
    }

}



