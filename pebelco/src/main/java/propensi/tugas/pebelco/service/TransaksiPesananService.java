package propensi.tugas.pebelco.service;

import java.util.List;

import propensi.tugas.pebelco.model.PesananPenjualanModel;
import propensi.tugas.pebelco.model.ProdukModel;
import propensi.tugas.pebelco.model.TransaksiPesananModel;

public interface TransaksiPesananService {
    void saveAll(List<TransaksiPesananModel> listTransaksiPesanan);

    void addAll(List<TransaksiPesananModel> listTransaksiPesanan, Long idPesanan);

    List<TransaksiPesananModel> getTransaksiPesananList();

    TransaksiPesananModel getByIdTransaksiPesanan(Long idTransaksiPesanan);

    TransaksiPesananModel updateTransaksiPesanan(TransaksiPesananModel transaksiPesanan);

    void deleteTransaksiPesanan(Long idTransaksiPesanan);

    Long calculatePrice(Integer jumlah, ProdukModel produk);

    List<TransaksiPesananModel> getListByIdPesanan(PesananPenjualanModel pesananTransaksi);
}
