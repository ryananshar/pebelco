package propensi.tugas.pebelco.service;


import propensi.tugas.pebelco.model.TransaksiKomplainModel;

import java.util.List;

public interface TransaksiKomplainService {
    TransaksiKomplainModel getTransaksiByIdTransaksi(Long idTransaksiKomplain);
    void addTransaksiKomplain(TransaksiKomplainModel transaksiKomplain);
    List<TransaksiKomplainModel> getListTransaksiKomplain();
    TransaksiKomplainModel updateTransaksiKomplain(TransaksiKomplainModel transaksiKomplain);
    void deleteTransaksiKomplain(TransaksiKomplainModel transaksiKomplain);
}