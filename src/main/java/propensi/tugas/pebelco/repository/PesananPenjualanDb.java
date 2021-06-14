package propensi.tugas.pebelco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import propensi.tugas.pebelco.model.PesananPenjualanModel;
import propensi.tugas.pebelco.model.ProdukModel;
import propensi.tugas.pebelco.model.UserModel;

import java.util.Date;
import java.util.List;

@Repository
public interface PesananPenjualanDb extends JpaRepository<PesananPenjualanModel, Long> {
    List<PesananPenjualanModel> findAllByIsShownIsTrueAndStatusPesananEqualsOrderByIdPesananPenjualanAsc(int statusPesanan);

    List<PesananPenjualanModel> findAllByIsShownIsTrueAndStatusPesananEqualsAndUserEqualsOrderByIdPesananPenjualanAsc(int statusPesanan, UserModel user);

    PesananPenjualanModel findByIdPesananPenjualan(Long idPesananPenjualan);

    List<PesananPenjualanModel> findAll();

    List<PesananPenjualanModel> findAllByStatusPesanan(Integer status);

    PesananPenjualanModel findByKodePesananPenjualan(String kodePesananPenjualan);

    List<PesananPenjualanModel> findByIsShownOrderByIdPesananPenjualanAsc(Boolean isShown);

    List<PesananPenjualanModel> findByUserAndIsShownOrderByIdPesananPenjualanAsc(UserModel user, Boolean isShown);

    List<PesananPenjualanModel> findByUserAndTanggalPesananBetweenOrderByIdPesananPenjualanAsc(UserModel user, Date tanggalAwal, Date tanggalAkhir);
}