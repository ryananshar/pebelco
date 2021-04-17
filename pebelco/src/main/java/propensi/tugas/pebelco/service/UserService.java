package propensi.tugas.pebelco.service;

import propensi.tugas.pebelco.model.UserModel;

public interface UserService {
    //  Method untuk menambah objek
    UserModel addUser(UserModel user);

    //  Method untuk mendapatkan list data yang telah tersimpan
    UserModel getUserbyNamaPanjang(String namaPanjang);

    //  Method untuk mendapatkan data berdasarkan id
    UserModel getUserbyEmail(String email);

    // Method untuk enkripsi password 
    public String encrypt(String password);

    // Method untuk memvalidasi pass baru dengan lama
    boolean checkIfValidOldPassword(UserModel user, String oldPassword);

    // method untuk mengubah password
	void changePassword(UserModel user, String newPassword);
}
