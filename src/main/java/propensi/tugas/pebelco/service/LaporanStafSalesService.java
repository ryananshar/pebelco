package propensi.tugas.pebelco.service;

import java.util.Date;
import java.util.List;

import propensi.tugas.pebelco.model.LaporanStafSalesModel;
import propensi.tugas.pebelco.model.UserModel;

public interface LaporanStafSalesService {
    void addLaporanStafSales(LaporanStafSalesModel laporanStafSales);

    List<LaporanStafSalesModel> getLaporanStafSalesList();

    LaporanStafSalesModel updateLaporanStafSales(LaporanStafSalesModel laporanStafSales);

    void deleteLaporanStafSales(LaporanStafSalesModel laporanStafSales);

    List<LaporanStafSalesModel> getLaporanStafSalesByStafSalesList(UserModel stafSales);

    LaporanStafSalesModel getLaporanStafSalesById(Long idLaporanStafSales);

    List<LaporanStafSalesModel> getLaporanStafSalesByDate(Date startDate, Date finalDate);
}
