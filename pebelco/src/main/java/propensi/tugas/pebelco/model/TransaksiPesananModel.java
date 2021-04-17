package propensi.tugas.pebelco.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Table(name="transaksi_pesanan")
public class TransaksiPesananModel implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaksi_pesanan", nullable = false)
    private Long idTransaksiPesanan;

    @NotNull
    @Size(max = 50)
    @Column(name = "nama_barang", nullable = false)
    private String namaBarang;

    @NotNull
    @Column(name = "jumlah", nullable = false)
    private Integer jumlah;

    // harga satuan x jumlah
    @NotNull
    @Column(name = "harga", nullable = false)
    private Long harga;

    // id pesanan
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "id_pesanan", referencedColumnName = "id_pesanan_penjualan")
    // @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_pesanan", referencedColumnName = "id_pesanan_penjualan", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private PesananPenjualanModel pesananTransaksi;

    // id produk
    // @ManyToOne(fetch = FetchType.LAZY)
    // @MapsId("idProduk")
    // @JoinColumn(name = "id_produk", referencedColumnName = "id_produk")
    // @OnDelete(action = OnDeleteAction.CASCADE)
    // private ProdukModel produkPesanan;


    public TransaksiPesananModel() {
        // super();
        // this.setJumlah(0);
        // this.setHarga(Long.valueOf(0));
    }


    // public TransaksiPesananModel(Long idTransaksiPesanan, String namaBarang, Integer jumlah, Long harga, PesananPenjualanModel pesananTransaksi) {
    //     this.namaBarang = namaBarang;
    //     this.jumlah = jumlah;
    //     this.harga = harga;
    //     this.pesananTransaksi = pesananTransaksi;
    // }
    

    public Long getIdTransaksiPesanan() {
        return this.idTransaksiPesanan;
    }

    public void setIdTransaksiPesanan(Long idTransaksiPesanan) {
        this.idTransaksiPesanan = idTransaksiPesanan;
    }

    public String getNamaBarang() {
        return this.namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public Integer getJumlah() {
        return this.jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public Long getHarga() {
        return this.harga;
    }

    public void setHarga(Long harga) {
        this.harga = harga;
    }

    public PesananPenjualanModel getPesananTransaksi() {
        return this.pesananTransaksi;
    }

    public void setPesananTransaksi(PesananPenjualanModel pesananTransaksi) {
        this.pesananTransaksi = pesananTransaksi;
    }

    // tester
}
