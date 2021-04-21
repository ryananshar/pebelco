package propensi.tugas.pebelco.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import propensi.tugas.pebelco.model.RoleModel;
import propensi.tugas.pebelco.model.UserModel;

@Repository
public interface UserDb extends JpaRepository<UserModel, Long>{
    UserModel findByNamaPanjang(String namaPanjang);

    UserModel findByEmail(String email);

    List<UserModel> findByRole(RoleModel role);
}
