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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pesanan_penjualan", referencedColumnName = "idPesananPenjualan", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private PesananPenjualanModel pesananKomplain;

}
