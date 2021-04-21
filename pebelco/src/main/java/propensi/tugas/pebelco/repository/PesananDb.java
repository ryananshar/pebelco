package propensi.tugas.pebelco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import propensi.tugas.pebelco.model.PesananPenjualanModel;

@Repository
public interface PesananDb extends JpaRepository<PesananPenjualanModel, Long> {
}
