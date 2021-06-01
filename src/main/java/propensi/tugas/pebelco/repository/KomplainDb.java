package propensi.tugas.pebelco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import propensi.tugas.pebelco.model.KomplainModel;
import propensi.tugas.pebelco.model.UserModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface KomplainDb extends JpaRepository<KomplainModel, Long> {
    Optional<KomplainModel> findByIdKomplain(Long idKomplain);
    List<KomplainModel> findAll();
    List<KomplainModel> findByIsShownOrderByIdKomplainAsc(boolean isShown);
    List<KomplainModel> findByUserAndIsShownOrderByIdKomplainAsc(UserModel user, Boolean isShown);
    KomplainModel findByKodeKomplain(String kodeKomplain);
    List<KomplainModel> findAllByIsShownIsTrueAndStatusKomplainEqualsOrderByIdKomplainAsc(int statusKomplain);
}
