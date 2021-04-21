package propensi.tugas.pebelco.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import propensi.tugas.pebelco.model.KomplainModel;
import propensi.tugas.pebelco.model.TransaksiKomplainModel;

import java.util.List;

@Repository
public interface TransaksiKomplainDb extends JpaRepository<TransaksiKomplainModel, Long> {
    List<TransaksiKomplainModel> findAllByKomplainTransaksi(KomplainModel komplainModel);
}
