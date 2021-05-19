package propensi.tugas.pebelco.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import propensi.tugas.pebelco.model.RoleModel;
import propensi.tugas.pebelco.repository.RoleDb;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleDb roleDb;

    @Override
    public List<RoleModel> findAll() {
        return roleDb.findAll();
    }
    
}
