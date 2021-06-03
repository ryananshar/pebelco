package propensi.tugas.pebelco.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import propensi.tugas.pebelco.model.PesananPenjualanModel;
import propensi.tugas.pebelco.model.TransaksiPesananModel;
import propensi.tugas.pebelco.model.UserModel;
import propensi.tugas.pebelco.repository.PesananPenjualanDb;

@Service
@Transactional
public class PesananPenjualanServiceImpl implements PesananPenjualanService{
    @Autowired
    private PesananPenjualanDb pesananPenjualanDb;

    @Override
    public void addPesanan(PesananPenjualanModel pesananPenjualan) {
        pesananPenjualanDb.save(pesananPenjualan);
        
    }

    @Override
    public List<PesananPenjualanModel> getPesananList(Boolean bolean) {
        return pesananPenjualanDb.findByIsShownOrderByIdPesananPenjualanAsc(bolean);
    }

    @Override
    public PesananPenjualanModel getPesananByIdPesanan(Long idPesananPenjualan) {
        return pesananPenjualanDb.findByIdPesananPenjualan(idPesananPenjualan);
    }

    @Override
    public PesananPenjualanModel updatePesanan(PesananPenjualanModel pesananPenjualan) {
        return pesananPenjualanDb.save(pesananPenjualan);
    }

    @Override
    public void deletePesanan(PesananPenjualanModel pesananPenjualan) {
        pesananPenjualan.setIsShown(false);
        pesananPenjualanDb.save(pesananPenjualan);        
    }

    @Override
    public Long calculateTotal(List<TransaksiPesananModel> barangPesanan, Integer diskon) {
        Long tempTotal = 0L;
        for (TransaksiPesananModel barang : barangPesanan) {
            tempTotal += barang.getHarga();
        }
        Long total = (Long) tempTotal*(100 - diskon)/100;
        return total;
    }

    @Override
    public PesananPenjualanModel getPesananByKodePesanan(String kodePesananPenjualan) {
        return pesananPenjualanDb.findByKodePesananPenjualan(kodePesananPenjualan);
    }

    @Override
    public List<PesananPenjualanModel> getPesananListByUser(UserModel user, Boolean bolean) {
        return pesananPenjualanDb.findByUserAndIsShownOrderByIdPesananPenjualanAsc(user, bolean);
    }

    @Override
    public void changeStatusDisetujui(PesananPenjualanModel pesanan){
        pesanan.setStatusPesanan(1);
    }

    @Override
    public void changeStatusDitolak(PesananPenjualanModel pesanan){
        pesanan.setStatusPesanan(2);
    }

    @Override
    public List<PesananPenjualanModel> getPesananListByUserAndTanggalBetween(UserModel stafSales, Date tanggalAwal, Date tanggalAkhir) {
        return pesananPenjualanDb.findByUserAndTanggalPesananBetweenOrderByIdPesananPenjualanAsc(stafSales, tanggalAwal, tanggalAkhir);
    }

    @Override
    public List<PesananPenjualanModel> getPesananListForAdminKomplain(int status){
        return pesananPenjualanDb.findAllByIsShownIsTrueAndStatusPesananEqualsOrderByIdPesananPenjualanAsc(status);
    }

    @Override
    public List<PesananPenjualanModel> getPesananListForStafSales(UserModel user, int status){
        return pesananPenjualanDb.findAllByIsShownIsTrueAndStatusPesananEqualsAndUserEqualsOrderByIdPesananPenjualanAsc(status, user);
    }
    
}
