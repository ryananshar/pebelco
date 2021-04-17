package propensi.tugas.pebelco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import propensi.tugas.pebelco.model.PesananPenjualanModel;
import propensi.tugas.pebelco.model.ProdukModel;
import propensi.tugas.pebelco.model.TransaksiPesananModel;

@Repository
public interface TransaksiPesananDb extends JpaRepository<TransaksiPesananModel, Long> {
    TransaksiPesananModel findByIdTransaksiPesanan(Long idTransaksiPesanan);

    TransaksiPesananModel findByPesananTransaksi(PesananPenjualanModel pesananTransaksi);

    // TransaksiPesananModel findByProdukPesanan(ProdukModel produkPesanan);
}
