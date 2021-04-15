package propensi.tugas.pebelco.service;

import java.util.List;

import propensi.tugas.pebelco.model.TransaksiPesananModel;

public interface TransaksiPesananService {
    void addTransaksiPesanan(TransaksiPesananModel transaksiPesanan);

    List<TransaksiPesananModel> getTransaksiPesananList();

    TransaksiPesananModel getByIdTransaksiPesanan(Long idTransaksiPesanan);

    TransaksiPesananModel updateTransaksiPesanan(TransaksiPesananModel transaksiPesanan);

    void deleteTransaksiPesanan(Long idTransaksiPesanan);
}
