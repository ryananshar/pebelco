package propensi.tugas.pebelco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import propensi.tugas.pebelco.model.PesananPenjualanModel;
import propensi.tugas.pebelco.model.UserModel;

import java.util.List;

@Repository
public interface PesananPenjualanDb extends JpaRepository<PesananPenjualanModel, Long> {
    List<PesananPenjualanModel> findAllByIsShownIsTrueAndStatusPesananEquals(int statusPesanan);

    PesananPenjualanModel findByIdPesananPenjualan(Long idPesananPenjualan);

    PesananPenjualanModel findByKodePesananPenjualan(String kodePesananPenjualan);

    List<PesananPenjualanModel> findByIsShown(Boolean isShown);

    List<PesananPenjualanModel> findByUser(UserModel user);

    List<PesananPenjualanModel> findByUserAndIsShown(UserModel user, Boolean isShown);

}
