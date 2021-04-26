package propensi.tugas.pebelco.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Table(name="transaksi_komplain")
public class TransaksiKomplainModel implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaksi_komplain", nullable = false)
    private Long idTransaksiKomplain;

    @NotNull
    @Size(max = 50)
    @Column(name = "nama_barang", nullable = false)
    private String namaBarang;

    @NotNull
    @Column(name = "jumlah", nullable = false)
    private Integer jumlah;

    @Size(max = 250)
    @Column(name = "deskripsiKomplain", nullable = false)
    private String deskripsiKomplain;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_komplain", referencedColumnName = "id_komplain", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private KomplainModel komplainTransaksi;


    public Long getIdTransaksiKomplain() {
        return this.idTransaksiKomplain;
    }

    public void setIdTransaksiKomplain(Long idTransaksiKomplain) {
        this.idTransaksiKomplain = idTransaksiKomplain;
    }

    public KomplainModel getKomplainTransaksi() {
        return this.komplainTransaksi;
    }

    public void setKomplainTransaksi(KomplainModel komplainTransaksi) {
        this.komplainTransaksi = komplainTransaksi;
    }

    public Integer getJumlah() {
        return this.jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public String getDeskripsiKomplain() {
        return this.deskripsiKomplain;
    }

    public void setDeskripsiKomplain(String deskripsiKomplain) {
        this.deskripsiKomplain = deskripsiKomplain;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }
}
