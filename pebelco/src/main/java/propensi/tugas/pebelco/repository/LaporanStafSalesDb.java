package propensi.tugas.pebelco.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import propensi.tugas.pebelco.model.LaporanStafSalesModel;
import propensi.tugas.pebelco.model.UserModel;

@Repository
public interface LaporanStafSalesDb extends JpaRepository<LaporanStafSalesModel, Long>{
    List<LaporanStafSalesModel> findAll();

    List<LaporanStafSalesModel> findByStafSales(UserModel stafSales);
}
