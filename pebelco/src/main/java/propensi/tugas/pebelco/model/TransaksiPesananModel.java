package propensi.tugas.pebelco.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Table(name="transaksi_pesanan")
public class TransaksiPesananModel implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaksi_pesanan", nullable = false)
    private Long idTransaksiPesanan;

    // @EmbeddedId
    // PesananProdukKey id;

    @NotNull
    @Column(name = "jumlah", nullable = false)
    private Integer jumlah;

    // harga satuan x jumlah
    @NotNull
    @Column(name = "harga", nullable = false)
    private Long harga;

    // id pesanan
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idPesananPenjualan")
    @JoinColumn(name = "id_pesanan", referencedColumnName = "id_pesanan_penjualan")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PesananPenjualanModel pesananTransaksi;

    // id produk
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idProduk")
    @JoinColumn(name = "id_produk", referencedColumnName = "id_produk")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProdukModel produkPesanan;


    public Long getIdTransaksiPesanan() {
        return this.idTransaksiPesanan;
    }

    public void setIdTransaksiPesanan(Long idTransaksiPesanan) {
        this.idTransaksiPesanan = idTransaksiPesanan;
    }

    public PesananPenjualanModel getPesananTransaksi() {
        return this.pesananTransaksi;
    }

    public void setPesananTransaksi(PesananPenjualanModel pesananTransaksi) {
        this.pesananTransaksi = pesananTransaksi;
    }

    public ProdukModel getProdukPesanan() {
        return this.produkPesanan;
    }

    public void setProdukPesanan(ProdukModel produkPesanan) {
        this.produkPesanan = produkPesanan;
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

    // public PesananProdukKey getId() {
    //     return this.id;
    // }

    // public void setId(PesananProdukKey id) {
    //     this.id = id;
    // }


}
