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
@Table(name="notifikasi")
public class NotifikasiModel implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notifikasi", nullable = false)
    private Long idNotifikasi;

    @NotNull
    @Column(name = "is_notif", nullable = false)
    private Boolean isNotif;

    @NotNull
    @Size(max = 250)
    @Column(name = "desc_notifikasi", nullable = false)
    private String desc;

    @NotNull
    @Size(max = 250)
    @Column(name = "url_page", nullable = false)
    private String url;

    @NotNull
    @Column(name = "id_pengirim", nullable = false)
    private Long idPengirim;

    @Column(name = "id_penerima", nullable = true)
    private Long idPenerima;

    @Column(name = "id_role_penerima", nullable = true)
    private Long idRole;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "waktu_dibuat", nullable = false)
    private Date waktuDibuat; 
    
    // create table "notifikasi user"
    // @ManyToMany(cascade = CascadeType.ALL)
    // @JoinTable(
    //     name = "notifikasi_user", 
    //     joinColumns = @JoinColumn(name = "id_notifikasi"), 
    //     inverseJoinColumns = @JoinColumn(name = "id_user"))
    // private List<UserModel> listUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private UserModel user;


    public NotifikasiModel() {
    }


    public NotifikasiModel(Boolean isNotif, String desc, String url, Long idPengirim, Long idPenerima, Long idRole) {
        this.isNotif = isNotif;
        this.desc = desc;
        this.url = url;
        this.idPengirim = idPengirim;
        this.idPenerima = idPenerima;
        this.idRole = idRole;
    }    

    public Long getIdNotifikasi() {
        return this.idNotifikasi;
    }

    public void setIdNotifikasi(Long idNotifikasi) {
        this.idNotifikasi = idNotifikasi;
    }

    public Boolean isIsNotif() {
        return this.isNotif;
    }

    public Boolean getIsNotif() {
        return this.isNotif;
    }

    public void setIsNotif(Boolean isNotif) {
        this.isNotif = isNotif;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getIdPengirim() {
        return this.idPengirim;
    }

    public void setIdPengirim(Long idPengirim) {
        this.idPengirim = idPengirim;
    }

    public Long getIdPenerima() {
        return this.idPenerima;
    }

    public void setIdPenerima(Long idPenerima) {
        this.idPenerima = idPenerima;
    }

    public Long getIdRole() {
        return this.idRole;
    }

    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }

    public Date getWaktuDibuat() {
        return this.waktuDibuat;
    }

    public void setWaktuDibuat(Date waktuDibuat) {
        this.waktuDibuat = waktuDibuat;
    }

    public UserModel getUser() {
        return this.user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }  
}
