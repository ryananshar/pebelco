package propensi.tugas.pebelco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.tugas.pebelco.model.*;
import propensi.tugas.pebelco.repository.*;
import propensi.tugas.pebelco.service.*;
import propensi.tugas.pebelco.utils.PerluDikirim.Barang;
import propensi.tugas.pebelco.utils.PerluDikirim.PerluDikirim;
import propensi.tugas.pebelco.utils.PerluDikirim.PerluDikirimUtils;

import java.util.ArrayList;
import java.util.Date;
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
    private PengirimanDb pengirimanDb;

    @Autowired
    private PerluDikirimUtils utils;

    @Autowired
    private ProdukService produkService;

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

    @Override
    public void addPengirimanKomplain(Long idKomplain, Long idMetodePengiriman) {
        KomplainModel komplain = komplainDb.getOne(idKomplain);
        MetodePengirimanModel metodePengiriman = metodePengirimanDb.getOne(idMetodePengiriman);

        komplain.setIsShown(false);
        komplainDb.save(komplain);

        savePengirimanFromKomplain(komplain, metodePengiriman);
    }

    @Override
    public void addPengirimanPesanan(Long idPesanan, Long idMetodePengiriman) {
        PesananPenjualanModel pesanan = pesananPenjualanDb.getOne(idPesanan);
        MetodePengirimanModel metodePengiriman = metodePengirimanDb.getOne(idMetodePengiriman);

        pesanan.setIsShown(false);
        pesananPenjualanDb.save(pesanan);

        savePengirimanFromPesanan(pesanan, metodePengiriman);
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
            Barang barang = new Barang(
                    transaksi.getNamaBarang(),
                    transaksi.getJumlah()
            );
            barangList.add(barang);
        }


        return barangList;
    }

    private List<Barang> getBarangListFromTransaksiPesanan(List<TransaksiPesananModel> transaksiList) {
        List<Barang> barangList = new ArrayList<>();

        for (TransaksiPesananModel transaksi: transaksiList) {
            Barang barang = new Barang(
                    transaksi.getNamaBarang(),
                    transaksi.getJumlah()
            );
            barangList.add(barang);
        }

        return barangList;
    }

    private void savePengirimanFromKomplain(
            KomplainModel komplain,
            MetodePengirimanModel metodePengiriman) {
        PengirimanModel pengiriman = new PengirimanModel();

        pengiriman.setAlamatToko(komplain.getAlamatToko());
        pengiriman.setIsShown(true);
        pengiriman.setKodePengiriman("temp");
        pengiriman.setNamaToko(komplain.getNamaToko());
        pengiriman.setStatusPengiriman(1);
        pengiriman.setTanggalDibuat(new Date());
        pengiriman.setKomplain(komplain);
        pengiriman.setMetodePengiriman(metodePengiriman);

        pengirimanDb.save(pengiriman);

        String kodePengiriman = getKodePengiriman(pengiriman.getIdPengiriman());
        pengiriman.setKodePengiriman(kodePengiriman);
        pengirimanDb.save(pengiriman);
    }

    private void savePengirimanFromPesanan(
            PesananPenjualanModel pesanan,
            MetodePengirimanModel metodePengiriman) {
        PengirimanModel pengiriman = new PengirimanModel();

        pengiriman.setAlamatToko(pesanan.getAlamatToko());
        pengiriman.setIsShown(true);
        pengiriman.setKodePengiriman("temp");
        pengiriman.setNamaToko(pesanan.getNamaToko());
        pengiriman.setStatusPengiriman(1);
        pengiriman.setTanggalDibuat(new Date());
        pengiriman.setPesananPenjualan(pesanan);
        pengiriman.setMetodePengiriman(metodePengiriman);

        pengirimanDb.save(pengiriman);

        String kodePengiriman = getKodePengiriman(pengiriman.getIdPengiriman());
        pengiriman.setKodePengiriman(kodePengiriman);
        pengirimanDb.save(pengiriman);
    }

    private String getKodePengiriman(Long id) {
        return "PNG" + id.toString();
    }
}
