package propensi.tugas.pebelco.service;

import java.util.List;

import propensi.tugas.pebelco.model.PesananPenjualanModel;
import propensi.tugas.pebelco.model.TransaksiPesananModel;
import propensi.tugas.pebelco.repository.PesananPenjualanDb;

public interface TransaksiPesananService {
    void saveAll(List<TransaksiPesananModel> listTransaksiPesanan);

    void addAll(List<TransaksiPesananModel> listTransaksiPesanan, Long idPesanan);

    List<TransaksiPesananModel> getTransaksiPesananList();

    TransaksiPesananModel getByIdTransaksiPesanan(Long idTransaksiPesanan);

    TransaksiPesananModel updateTransaksiPesanan(TransaksiPesananModel transaksiPesanan);

    void deleteTransaksiPesanan(Long idTransaksiPesanan);
}
