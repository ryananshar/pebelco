package propensi.tugas.pebelco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.tugas.pebelco.model.KomplainModel;
import propensi.tugas.pebelco.model.MetodePengirimanModel;
import propensi.tugas.pebelco.model.PengirimanModel;
import propensi.tugas.pebelco.model.PesananPenjualanModel;
import propensi.tugas.pebelco.repository.MetodePengirimanDb;
import propensi.tugas.pebelco.repository.PengirimanDb;
import propensi.tugas.pebelco.utils.Pengiriman.Pengiriman;
import propensi.tugas.pebelco.utils.PerluDikirim.Barang;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

@Transactional
@Service
public class PengirimanServiceImpl implements PengirimanService {

    @Autowired
    private PerluDikirimService perluDikirimService;

    @Autowired
    private PengirimanDb pengirimanDb;

    @Autowired
    private MetodePengirimanDb metodePengirimanDb;

    @Override
    public List<Pengiriman> findAll() {
        List<PengirimanModel> pengirimanList = pengirimanDb.findAllByIsShownIsTrueOrderByIdPengirimanAsc();
        return getPengirimanFromModel(pengirimanList);
    }

    @Override
    public List<Pengiriman> findAllLaporan() {
        List<PengirimanModel> pengirimanList = pengirimanDb.findAll();
        return getPengirimanFromModel(pengirimanList);
    }

    @Override
    public Pengiriman findPengirimanByKode(String kodePengiriman) {
        PengirimanModel pengirimanModel = pengirimanDb.findByKodePengiriman(kodePengiriman);
        return new Pengiriman(pengirimanModel);
    }

    @Override
    public List<Barang> findAllBarangByKodePengiriman(String kodePengiriman) {
        PengirimanModel pengiriman = pengirimanDb.findByKodePengiriman(kodePengiriman);
        boolean isKomplain = checkIfKomplain(pengiriman);

        if (isKomplain) {
            KomplainModel komplain = pengiriman.getKomplain();
            return perluDikirimService.findAllBarangByKodeKomplain(komplain.getKodeKomplain());
        } else {
            PesananPenjualanModel pesanan = pengiriman.getPesananPenjualan();
            return perluDikirimService.findAllBarangByKodePesanan(pesanan.getKodePesananPenjualan());
        }
    }

    @Override
    public void updateMetodePengiriman(String kodePengiriman, Long idMetodePengiriman) {
        PengirimanModel pengiriman = pengirimanDb.findByKodePengiriman(kodePengiriman);
        MetodePengirimanModel metodePengiriman = metodePengirimanDb.getOne(idMetodePengiriman);

        pengiriman.setMetodePengiriman(metodePengiriman);
        pengirimanDb.save(pengiriman);
    }

    @Override
    public void updateStatusPengiriman(String kodePengiriman, int statusPengiriman) {
        PengirimanModel pengirimanModel = pengirimanDb.findByKodePengiriman(kodePengiriman);

        // Kalau sekarang dikirim
        if (statusPengiriman == 2) {
            pengirimanModel.setTanggalDikirim(new Date());
        }

        pengirimanModel.setStatusPengiriman(statusPengiriman);
        pengirimanDb.save(pengirimanModel);
    }

    @Override
    public void terimaPengiriman(String kodePengiriman, Date tanggalDiterima, String namaPenerima) {
        PengirimanModel pengirimanModel = pengirimanDb.findByKodePengiriman(kodePengiriman);
        pengirimanModel.setTanggalDiterima(tanggalDiterima);
        pengirimanModel.setNamaPenerima(namaPenerima);
        pengirimanDb.save(pengirimanModel);
    }

    @Override
    public boolean showUbahStatusButton(int status) {
        return status < 3;
    }

    @Override
    public boolean showUbahPengirimanButton(int status) {
        return status < 2;
    }

    @Override
    public void setIsShownFalse(String kodePengiriman) {
        PengirimanModel pengirimanModel = pengirimanDb.findByKodePengiriman(kodePengiriman);
        pengirimanModel.setIsShown(false);
        pengirimanDb.save(pengirimanModel);
    }

    private List<Pengiriman> getPengirimanFromModel(List<PengirimanModel> pengirimanModelList) {
        List<Pengiriman> pengirimanList = new ArrayList<>();

        for (PengirimanModel pengirimanModel: pengirimanModelList) {
            pengirimanList.add(new Pengiriman(pengirimanModel));
        }

        return pengirimanList;
    }

    private boolean checkIfKomplain(PengirimanModel pengiriman) {
        try {
            pengiriman.getKomplain().getIdKomplain();
            return true;
        } catch (NullPointerException npe) {
            return false;
        }
    }

    
	@Override
	public List<Pengiriman> getPengirimanByDate(Date startDate, Date finalDate) {
        List<PengirimanModel> pengirimanList = pengirimanDb.findByTanggalDiterimaBetweenOrderByIdPengirimanAsc(startDate, finalDate);
		return getPengirimanFromModel(pengirimanList);
	}
}
