package propensi.tugas.pebelco.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import propensi.tugas.pebelco.model.PesananPenjualanModel;
import propensi.tugas.pebelco.model.ProdukModel;
import propensi.tugas.pebelco.model.TransaksiPesananModel;
import propensi.tugas.pebelco.repository.ProdukDb;
import propensi.tugas.pebelco.repository.TransaksiPesananDb;

@Service
@Transactional
public class TransaksiPesananServiceImpl implements TransaksiPesananService{
    @Autowired
    private TransaksiPesananDb transaksiPesananDb;

    @Autowired
    private PesananPenjualanService pesananPenjualanService;

    @Autowired
    private ProdukDb produkDb;

    @Override
    public void saveAll(List<TransaksiPesananModel> listTransaksiPesanan) {
        transaksiPesananDb.saveAll(listTransaksiPesanan);        
    }

    @Override
    public void addAll(List<TransaksiPesananModel> listTransaksiPesanan, Long idPesanan) {
        for (TransaksiPesananModel transaksiPesanan : listTransaksiPesanan) {
            transaksiPesanan.setPesananTransaksi(pesananPenjualanService.getPesananByIdPesanan(idPesanan));
            transaksiPesanan.setHarga(calculatePrice(transaksiPesanan.getJumlah(), produkDb.findByNamaProduk(transaksiPesanan.getNamaBarang())));
            transaksiPesananDb.save(transaksiPesanan);
        }
    }
//
//    @Override
//    public void deleteAll(List<TransaksiPesananService> listTransaksiPesanan,Long idPesanan){
//        listTransaksiPesanan.
//        transaksiPesanan.setPesananTransaksi(pesananPenjualanService.getPesananByIdPesanan(idPesanan));
//        transaksiPesananDb.save(transaksiPesanan);
//    }

    @Override
    public List<TransaksiPesananModel> getTransaksiPesananList() {
        return transaksiPesananDb.findAll();
    }

    @Override
    public TransaksiPesananModel getByIdTransaksiPesanan(Long idTransaksiPesanan) {
        return transaksiPesananDb.findByIdTransaksiPesanan(idTransaksiPesanan);
    }

    @Override
    public TransaksiPesananModel updateTransaksiPesanan(TransaksiPesananModel transaksiPesanan) {
        return transaksiPesananDb.save(transaksiPesanan);
    }

    @Override
    public void deleteTransaksiPesanan(Long idTransaksiPesanan) {
        transaksiPesananDb.deleteById(idTransaksiPesanan);        
    }

    @Override
    public Long calculatePrice(Integer jumlah, ProdukModel produk) {
        Long hargaProduk = produk.getHarga();
        Long jumlahBarang = Long.valueOf(jumlah);
        return jumlahBarang * hargaProduk;
    }

    @Override
    public List<TransaksiPesananModel> getListByIdPesanan(PesananPenjualanModel pesananTransaksi){
        return transaksiPesananDb.findByPesananTransaksi(pesananTransaksi);
    }

    @Override
    public List<TransaksiPesananModel> getListByIdPesananPenjualan(Long idPesanan){
        return transaksiPesananDb.findAllByIdTransaksiPesanan(idPesanan);
    }
}
