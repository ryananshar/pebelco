package propensi.tugas.pebelco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.tugas.pebelco.model.KomplainModel;
import propensi.tugas.pebelco.model.MetodePengirimanModel;
import propensi.tugas.pebelco.model.PengirimanModel;
import propensi.tugas.pebelco.model.PesananPenjualanModel;
import propensi.tugas.pebelco.repository.KomplainDb;
import propensi.tugas.pebelco.repository.MetodePengirimanDb;
import propensi.tugas.pebelco.repository.PengirimanDb;
import propensi.tugas.pebelco.utils.Pengiriman.Pengiriman;
import propensi.tugas.pebelco.utils.PerluDikirim.Barang;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        List<PengirimanModel> pengirimanList = pengirimanDb.findAllByIsShownIsTrue();
        return getPengirimanFromModel(pengirimanList);
    }

    @Override
    public Pengiriman findPengirimanById(Long id) {
        PengirimanModel pengirimanModel = pengirimanDb.getOne(id);
        return new Pengiriman(pengirimanModel);
    }

    @Override
    public List<Barang> findAllBarangByIdPengiriman(Long id) {
        PengirimanModel pengiriman = pengirimanDb.getOne(id);
        boolean isKomplain = checkIfKomplain(pengiriman);

        if (isKomplain) {
            KomplainModel komplain = pengiriman.getKomplain();
            return perluDikirimService.findAllBarangByIdKomplain(komplain.getIdKomplain());
        } else {
            PesananPenjualanModel pesanan = pengiriman.getPesananPenjualan();
            return perluDikirimService.findAllBarangByIdPesanan(pesanan.getIdPesananPenjualan());
        }
    }

    @Override
    public void updateMetodePengiriman(Long id, Long idMetodePengiriman) {
        PengirimanModel pengiriman = pengirimanDb.getOne(id);
        MetodePengirimanModel metodePengiriman = metodePengirimanDb.getOne(idMetodePengiriman);

        pengiriman.setMetodePengiriman(metodePengiriman);
        pengirimanDb.save(pengiriman);
    }

    @Override
    public void updateStatusPengiriman(Long id, int statusPengiriman) {
        PengirimanModel pengirimanModel = pengirimanDb.getOne(id);

        // Kalau sekarang dikirim
        if (statusPengiriman == 2) {
            pengirimanModel.setTanggalDikirim(new Date());
        }

        pengirimanModel.setStatusPengiriman(statusPengiriman);
        pengirimanDb.save(pengirimanModel);
    }

    @Override
    public void terimaPengiriman(Long id, Date tanggalDiterima, String namaPenerima) {
        PengirimanModel pengirimanModel = pengirimanDb.getOne(id);

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
    public void setIsShownFalse(Long id) {
        PengirimanModel pengirimanModel = pengirimanDb.getOne(id);

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
}
