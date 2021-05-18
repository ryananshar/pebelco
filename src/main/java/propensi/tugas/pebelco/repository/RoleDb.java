package propensi.tugas.pebelco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import propensi.tugas.pebelco.model.RoleModel;

@Repository
public interface RoleDb extends JpaRepository<RoleModel, Long> {
    
}
