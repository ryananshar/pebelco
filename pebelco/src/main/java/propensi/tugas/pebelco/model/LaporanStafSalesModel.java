package propensi.tugas.pebelco.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="laporan_staf_sales")
public class LaporanStafSalesModel implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_laporan_staf_sales", nullable = false)
    private Long idLaporanStafSales;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_staf_sales", referencedColumnName = "id_user", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private UserModel stafSales;

    @NotNull
    @Column(name = "is_kunjungan", nullable = false)
    private Boolean isKunjungan;

    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "tanggal_dibuat", nullable = false)
    private Date tanggalDibuat;

    // id kunjungan
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_kunjungan", referencedColumnName = "id_kunjungan", nullable = true)
    @JsonIgnore
    private KunjunganModel kunjungan;    
    
    // id pesanan penjualan
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pesanan", referencedColumnName = "id_pesanan_penjualan", nullable = true)
    @JsonIgnore
    private PesananPenjualanModel pesananPenjualan;


    public Long getIdLaporanStafSales() {
        return this.idLaporanStafSales;
    }

    public void setIdLaporanStafSales(Long idLaporanStafSales) {
        this.idLaporanStafSales = idLaporanStafSales;
    }

    public UserModel getStafSales() {
        return this.stafSales;
    }

    public void setStafSales(UserModel stafSales) {
        this.stafSales = stafSales;
    }

    public Boolean isIsKunjungan() {
        return this.isKunjungan;
    }

    public Boolean getIsKunjungan() {
        return this.isKunjungan;
    }

    public void setIsKunjungan(Boolean isKunjungan) {
        this.isKunjungan = isKunjungan;
    }

    public Date getTanggalDibuat() {
        return this.tanggalDibuat;
    }

    public void setTanggalDibuat(Date tanggalDibuat) {
        this.tanggalDibuat = tanggalDibuat;
    }

    public KunjunganModel getKunjungan() {
        return this.kunjungan;
    }

    public void setKunjungan(KunjunganModel kunjungan) {
        this.kunjungan = kunjungan;
    }

    public PesananPenjualanModel getPesananPenjualan() {
        return this.pesananPenjualan;
    }

    public void setPesananPenjualan(PesananPenjualanModel pesananPenjualan) {
        this.pesananPenjualan = pesananPenjualan;
    }

}
