package propensi.tugas.pebelco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.tugas.pebelco.model.*;
import propensi.tugas.pebelco.repository.*;
import propensi.tugas.pebelco.utils.PerluDikirim.Barang;
import propensi.tugas.pebelco.utils.PerluDikirim.PerluDikirim;
import propensi.tugas.pebelco.utils.PerluDikirim.PerluDikirimUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class PerluDikirimServiceImpl implements PerluDikirimService {
    @Autowired
    private KomplainDb komplainDb;

    @Autowired
    private PesananPenjualanDb pesananPenjualanDb;

    @Autowired
    private MetodePengirimanDb metodePengirimanDb;

    @Autowired
    private TransaksiKomplainDb transaksiKomplainDb;

    @Autowired
    private TransaksiPesananDb transaksiPesananDb;

    @Autowired
    private ProdukDb produkDb;

    @Autowired
    private PerluDikirimUtils utils;

    @Override
    public List<PerluDikirim> findAll() {
        return createPerluDikirimList(
                komplainDb.findAllByIsShownIsTrueAndStatusKomplainEquals(1),
                pesananPenjualanDb.findAllByIsShownIsTrueAndStatusPesananEquals(1)
        );
    }

    @Override
    public PerluDikirim findKomplainById(Long id) {
        KomplainModel komplain = komplainDb.findById(id).get();
        return utils.getPerluDikirimFromKomplain(komplain);
    }

    @Override
    public PerluDikirim findPesananById(Long id) {
        PesananPenjualanModel pesanan = pesananPenjualanDb.findById(id).get();
        return utils.getPerluDikirimFromPesanan(pesanan);
    }

    @Override
    public List<MetodePengirimanModel> findAllMetodePengiriman() {
        return metodePengirimanDb.findAll();
    }

    @Override
    public List<Barang> findAllBarangByIdKomplain(Long id) {
        KomplainModel komplain = komplainDb.findById(id).get();
        List<TransaksiKomplainModel> transaksiList = transaksiKomplainDb.findAllByKomplainTransaksi(komplain);
        return getBarangListFromTransaksiKomplain(transaksiList);
    }

    @Override
    public List<Barang> findAllBarangByIdPesanan(Long id) {
        PesananPenjualanModel pesanan = pesananPenjualanDb.findById(id).get();
        List<TransaksiPesananModel> transaksiList = transaksiPesananDb.findAllByPesananTransaksi(pesanan);
        return getBarangListFromTransaksiPesanan(transaksiList);
    }

    private List<PerluDikirim> createPerluDikirimList(
            List<KomplainModel> komplainList,
            List<PesananPenjualanModel> pesananList) {
        List<PerluDikirim> perluDikirimList = new ArrayList<>();

        for (KomplainModel komplain: komplainList) {
            perluDikirimList.add(
                    utils.getPerluDikirimFromKomplain(komplain)
            );
        }

        for (PesananPenjualanModel pesanan: pesananList) {
            perluDikirimList.add(
                    utils.getPerluDikirimFromPesanan(pesanan)
            );
        }

        return perluDikirimList;
    }

    private List<Barang> getBarangListFromTransaksiKomplain(List<TransaksiKomplainModel> transaksiList) {
        List<Barang> barangList = new ArrayList<>();

        for (TransaksiKomplainModel transaksi: transaksiList) {
            ProdukModel produk = transaksi.getProdukKomplain();
            Barang barang = new Barang(
                    produk.getNamaProduk(),
                    transaksi.getJumlah()
            );
            barangList.add(barang);
        }

        return barangList;
    }

    private List<Barang> getBarangListFromTransaksiPesanan(List<TransaksiPesananModel> transaksiList) {
        List<Barang> barangList = new ArrayList<>();

        for (TransaksiPesananModel transaksi: transaksiList) {
            ProdukModel produk = transaksi.getProdukPesanan();
            Barang barang = new Barang(
                    produk.getNamaProduk(),
                    transaksi.getJumlah()
            );
            barangList.add(barang);
        }

        return barangList;
    }
}
