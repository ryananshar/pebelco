//package propensi.tugas.pebelco.model;
//
//import java.io.Serializable;
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
//@Table(name="metode_pengiriman")
//public class MetodePengirimanModel implements Serializable {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id_metode", nullable = false)
//    private Long idMetode;
//
//    @NotNull
//    @Size(max = 50)
//    @Column(name = "nama_metode_pengiriman", nullable = false)
//    private String namaMetodePengiriman;
//
//}
