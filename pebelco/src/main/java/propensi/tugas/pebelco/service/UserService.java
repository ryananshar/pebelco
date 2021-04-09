package propensi.tugas.pebelco.service;

import propensi.tugas.pebelco.model.UserModel;

public interface UserService {
    UserModel addUser(UserModel user);

    UserModel getUserbyNamaPanjang(String namaPanjang);

    UserModel getUserbyEmail(String email);

    public String encrypt(String password);

    boolean checkIfValidOldPassword(UserModel user, String oldPassword);

	void changePassword(UserModel user, String newPassword);
}
