package propensi.tugas.pebelco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.tugas.pebelco.model.PameranModel;

import java.util.List;

@Repository
public interface PameranDb extends JpaRepository<PameranModel,Long> {
    List<PameranModel> findByOrderByIdPameranAsc();
}