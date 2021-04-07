//package propensi.tugas.pebelco.model;
//
//import java.io.Serializable;
//import java.time.LocalTime;
//import java.util.Date;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//
//import org.hibernate.annotations.OnDelete;
//import org.hibernate.annotations.OnDeleteAction;
//import org.springframework.format.annotation.DateTimeFormat;
//
//@Entity
//@Table(name="pengiriman")
//public class PengirimanModel implements Serializable{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id_pengiriman", nullable = false)
//    private Long idPengiriman;
//
//    @NotNull
//    @Size(max = 15)
//    @Column(name = "kode_pengiriman", nullable = false)
//    private String kodePengiriman;
//
//    @OneToMany(fetch = FetchType.EAGER)
//    @JoinColumn(name = "id_komplain", referencedColumnName = "idKomplain", nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonIgnore
//    private KomplainModel idKomplain;
//
//    @OneToMany(fetch = FetchType.EAGER)
//    @JoinColumn(name = "id_pesanan_penjualan", referencedColumnName = "idPesananaPenjualan", nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonIgnore
//    private PesananPenjualanModel idPesananPenjualan;
//
//    @OneToMany(fetch = FetchType.EAGER)
//    @JoinColumn(name = "id_metode", referencedColumnName = "idMetode", nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonIgnore
//    private MetodePengirimanModel idMetode;
//
//    // status pengiriman
//
//    // nama toko
//
//    // alamat toko
//
//    // tanggal2
//
//
//    @NotNull
//    @Size(max = 50)
//    @Column(name = "nama_penerima", nullable = false)
//    private String namaPenerima;
//
//    @NotNull
//    @Column(name = "is_shown", nullable = false)
//    private Boolean isShown;
//
//    // barang pengiriman
//
//
//
