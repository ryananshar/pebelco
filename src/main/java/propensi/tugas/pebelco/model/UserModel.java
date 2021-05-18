package propensi.tugas.pebelco.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Table(name="pengguna")
public class UserModel implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", nullable = false)
    private Long idUser;

    @NotNull
    @Size(max = 50)
    @Column(name = "nama_panjang", nullable = false)
    private String namaPanjang;

    @NotNull
    @Size(max = 50)
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotNull
    @Lob
    @Size(max = 250)
    @Column(name = "password", nullable = false)
    private String password;

    // @ManyToMany(mappedBy = "listUser")
    // private List<NotifikasiModel> listNotifikasi;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<NotifikasiModel> listNotifikasi;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_role", referencedColumnName = "id_role", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private RoleModel role;

    @OneToMany(mappedBy = "stafSales", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<KunjunganModel> listKunjungan;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<PesananPenjualanModel> listPesananPenjualan;

    @OneToMany(mappedBy = "stafSales", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<LaporanStafSalesModel> listLaporanStafSales;


    public Long getIdUser() {
        return this.idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getNamaPanjang() {
        return this.namaPanjang;
    }

    public void setNamaPanjang(String namaPanjang) {
        this.namaPanjang = namaPanjang;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<NotifikasiModel> getListNotifikasi() {
        return this.listNotifikasi;
    }

    public void setListNotifikasi(List<NotifikasiModel> listNotifikasi) {
        this.listNotifikasi = listNotifikasi;
    }

    public RoleModel getRole() {
        return this.role;
    }

    public void setRole(RoleModel role) {
        this.role = role;
    }

    public List<KunjunganModel> getListKunjungan() {
        return this.listKunjungan;
    }

    public void setListKunjungan(List<KunjunganModel> listKunjungan) {
        this.listKunjungan = listKunjungan;
    }

    public List<PesananPenjualanModel> getListPesananPenjualan() {
        return this.listPesananPenjualan;
    }

    public void setListPesananPenjualan(List<PesananPenjualanModel> listPesananPenjualan) {
        this.listPesananPenjualan = listPesananPenjualan;
    }

    public List<LaporanStafSalesModel> getListLaporanStafSales() {
        return this.listLaporanStafSales;
    }

    public void setListLaporanStafSales(List<LaporanStafSalesModel> listLaporanStafSales) {
        this.listLaporanStafSales = listLaporanStafSales;
    }

    public boolean isEnabled() {
        return true;
    }
    
}
