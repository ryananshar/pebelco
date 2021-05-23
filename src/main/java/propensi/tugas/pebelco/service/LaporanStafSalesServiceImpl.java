package propensi.tugas.pebelco.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import propensi.tugas.pebelco.model.LaporanStafSalesModel;
import propensi.tugas.pebelco.model.UserModel;
import propensi.tugas.pebelco.repository.LaporanStafSalesDb;

@Service
@Transactional
public class LaporanStafSalesServiceImpl implements LaporanStafSalesService{
    @Autowired
    private LaporanStafSalesDb laporanStafSalesDb;

    @Override
    public void addLaporanStafSales(LaporanStafSalesModel laporanStafSales) {
        laporanStafSalesDb.save(laporanStafSales);        
    }

    @Override
    public List<LaporanStafSalesModel> getLaporanStafSalesList() {
        return laporanStafSalesDb.findAll();
    }

    @Override
    public LaporanStafSalesModel updateLaporanStafSales(LaporanStafSalesModel laporanStafSales) {
        return laporanStafSalesDb.save(laporanStafSales);
    }

    @Override
    public void deleteLaporanStafSales(LaporanStafSalesModel laporanStafSales) {
        laporanStafSalesDb.delete(laporanStafSales);
    }

    @Override
    public List<LaporanStafSalesModel> getLaporanStafSalesByStafSalesList(UserModel stafSales) {
        return laporanStafSalesDb.findByStafSales(stafSales);
    }

    @Override
    public LaporanStafSalesModel getLaporanStafSalesById(Long id) {
        return laporanStafSalesDb.findById(id).get();
    }

	@Override
	public List<LaporanStafSalesModel> getLaporanStafSalesByDate(Date startDate, Date finalDate) {
		return laporanStafSalesDb.findByTanggalDibuatBetween(startDate, finalDate);
	}
    
    
}
