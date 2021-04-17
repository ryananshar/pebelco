package propensi.tugas.pebelco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import propensi.tugas.pebelco.model.PesananPenjualanModel;

import java.util.List;

@Repository
public interface PesananPenjualanDb extends JpaRepository<PesananPenjualanModel, Long> {
    List<PesananPenjualanModel> findAllByIsShownIsTrueAndStatusPesananEquals(int statusPesanan);
}
