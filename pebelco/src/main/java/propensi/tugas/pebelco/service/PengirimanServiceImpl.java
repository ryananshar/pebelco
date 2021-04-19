package propensi.tugas.pebelco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.tugas.pebelco.model.KomplainModel;
import propensi.tugas.pebelco.model.PengirimanModel;
import propensi.tugas.pebelco.model.PesananPenjualanModel;
import propensi.tugas.pebelco.repository.KomplainDb;
import propensi.tugas.pebelco.repository.PengirimanDb;
import propensi.tugas.pebelco.utils.Pengiriman.Pengiriman;
import propensi.tugas.pebelco.utils.PerluDikirim.Barang;

import java.util.ArrayList;
import java.util.List;

@Service
public class PengirimanServiceImpl implements PengirimanService {

    @Autowired
    private PerluDikirimService perluDikirimService;

    @Autowired
    private PengirimanDb pengirimanDb;

    @Autowired
    private KomplainDb komplainDb;

    @Override
    public List<Pengiriman> findAll() {
        List<PengirimanModel> pengirimanList = pengirimanDb.findAll();
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
