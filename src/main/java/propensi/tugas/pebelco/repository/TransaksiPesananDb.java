package propensi.tugas.pebelco.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import propensi.tugas.pebelco.model.PesananPenjualanModel;
import propensi.tugas.pebelco.model.TransaksiPesananModel;

import java.util.List;

@Repository
public interface TransaksiPesananDb extends JpaRepository<TransaksiPesananModel, Long> {
    List<TransaksiPesananModel> findAllByPesananTransaksi(PesananPenjualanModel pesananPenjualanModel);

    TransaksiPesananModel findByIdTransaksiPesanan(Long idTransaksiPesanan);

    List<TransaksiPesananModel> findByPesananTransaksi(PesananPenjualanModel pesananTransaksi);

    List<TransaksiPesananModel> findAllByIdTransaksiPesanan(Long idPesanan);

    List<TransaksiPesananModel> findByNamaBarang(String namaBarang);
}