package propensi.tugas.pebelco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.tugas.pebelco.model.PengirimanModel;
import propensi.tugas.pebelco.repository.PengirimanDb;
import propensi.tugas.pebelco.utils.Pengiriman.Pengiriman;

import java.util.ArrayList;
import java.util.List;

@Service
public class PengirimanServiceImpl implements PengirimanService {

    @Autowired
    private PengirimanDb pengirimanDb;

    @Override
    public List<Pengiriman> findAll() {
        List<PengirimanModel> pengirimanList = pengirimanDb.findAll();
        return getPengirimanFromModel(pengirimanList);
    }

    private List<Pengiriman> getPengirimanFromModel(List<PengirimanModel> pengirimanModelList) {
        List<Pengiriman> pengirimanList = new ArrayList<>();

        for (PengirimanModel pengirimanModel: pengirimanModelList) {
            pengirimanList.add(new Pengiriman(pengirimanModel));
        }

        return pengirimanList;
    }
}
