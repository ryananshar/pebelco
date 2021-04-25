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
    List<KomplainModel> findByIsShown(boolean isShown);
    List<KomplainModel> findByUserAndIsShown(UserModel user, Boolean isShown);
    KomplainModel findByKodeKomplain(String kodeKomplain);
    List<KomplainModel> findAllByIsShownIsTrueAndStatusKomplainEquals(int statusKomplain);
}
