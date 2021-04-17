package propensi.tugas.pebelco.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import propensi.tugas.pebelco.model.PesananPenjualanModel;
import propensi.tugas.pebelco.model.UserModel;

@Repository
public interface PesananPenjualanDb extends JpaRepository<PesananPenjualanModel, Long>{
    PesananPenjualanModel findByIdPesananPenjualan(Long idPesananPenjualan);

    PesananPenjualanModel findByKodePesananPenjualan(String kodePesananPenjualan);

    List<PesananPenjualanModel> findByIsShown(Boolean isShown);

    List<PesananPenjualanModel> findByUser(UserModel user);

    List<PesananPenjualanModel> findByUserAndIsShown(UserModel user, Boolean isShown);
}
