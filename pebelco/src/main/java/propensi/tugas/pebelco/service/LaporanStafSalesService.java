package propensi.tugas.pebelco.service;

import java.util.List;

import propensi.tugas.pebelco.model.LaporanStafSalesModel;

public interface LaporanStafSalesService {
    void addLaporanStafSales(LaporanStafSalesModel laporanStafSales);

    List<LaporanStafSalesModel> getLaporanStafSalesList();

    LaporanStafSalesModel updateLaporanStafSales(LaporanStafSalesModel laporanStafSales);

    void deleteLaporanStafSales(LaporanStafSalesModel laporanStafSales);
}
