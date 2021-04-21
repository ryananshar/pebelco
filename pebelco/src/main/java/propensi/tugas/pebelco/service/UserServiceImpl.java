package propensi.tugas.pebelco.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import propensi.tugas.pebelco.model.RoleModel;
import propensi.tugas.pebelco.model.UserModel;
import propensi.tugas.pebelco.repository.UserDb;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDb userDb;

    @Override
    public UserModel addUser(UserModel user) {
        String pass = encrypt(user.getPassword());
        user.setPassword(pass);
        return userDb.save(user);
    }

    @Override
    public UserModel getUserbyNamaPanjang(String namaPanjang) {
        return userDb.findByNamaPanjang(namaPanjang);
    }

    @Override
    public UserModel getUserbyEmail(String email) {
        return userDb.findByEmail(email);
    }

    @Override
    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    @Override
    public boolean checkIfValidOldPassword(UserModel user, String oldPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String pass = user.getPassword();
        if (passwordEncoder.matches(oldPassword, pass)) {
            return true;
        }
        return false;
    }

    @Override
    public void changePassword(UserModel user, String newPassword) {
        String pass = encrypt(newPassword);
        user.setPassword(pass);
        userDb.save(user);
        
    }

    @Override
    public List<UserModel> getUserListbyRole(RoleModel role) {
        return userDb.findByRole(role);
    }

    @Override
    public UserModel getUserbyIdUser(Long idUser) {
        return userDb.findById(idUser).get();
    }
   
}
