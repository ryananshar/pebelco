package propensi.tugas.pebelco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import propensi.tugas.pebelco.model.KomplainModel;

import java.util.List;

@Repository
public interface KomplainDb extends JpaRepository<KomplainModel, Long> {
    List<KomplainModel> findAllByIsShownIsTrueAndStatusKomplainEquals(int statusKomplain);
}
