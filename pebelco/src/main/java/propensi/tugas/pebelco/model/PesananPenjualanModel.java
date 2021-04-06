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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pesanan_penjualan", nullable = false)
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

    @NotNull
    @Column(name = "total_harga", nullable = false)
    private Long totalHarga;

    @Size(max = 250)
    @Column(name = "request_change", nullable = true)
    private String requestChange;

    @NotNull
    @Column(name = "is_shown", nullable = false)
    private Boolean isShown;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user", referencedColumnName = "idUser", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private UserModel user;

    @OneToMany(mappedBy = "pesananKomplain", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<KomplainModel> listKomplain;

    // pesanan bikin lier
    // private Map<ProdukModel, Integer> pesanan;
    
}
