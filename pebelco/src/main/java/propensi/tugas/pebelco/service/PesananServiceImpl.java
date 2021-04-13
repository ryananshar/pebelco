package propensi.tugas.pebelco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.tugas.pebelco.model.PesananPenjualanModel;
import propensi.tugas.pebelco.repository.PesananDb;
import propensi.tugas.pebelco.repository.ProdukDb;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PesananServiceImpl implements PesananService{

    @Autowired
    PesananDb pesananDb;

    @Override
    public List<PesananPenjualanModel> findAll() {
        return pesananDb.findAll();
    }
}
