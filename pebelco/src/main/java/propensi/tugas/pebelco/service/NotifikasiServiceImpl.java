package propensi.tugas.pebelco.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import propensi.tugas.pebelco.model.NotifikasiModel;
import propensi.tugas.pebelco.model.RoleModel;
import propensi.tugas.pebelco.model.UserModel;
import propensi.tugas.pebelco.repository.NotifikasiDb;
import propensi.tugas.pebelco.repository.RoleDb;

@Service
@Transactional
public class NotifikasiServiceImpl implements NotifikasiService{
    @Autowired
    private NotifikasiDb notifikasiDb;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleDb roleDb;

    @Override
    public void addNotifikasi(NotifikasiModel notifikasi) {
        if (Objects.isNull(notifikasi.getIdPenerima())) {
            RoleModel role = roleDb.findById(notifikasi.getIdRole()).get();
            List<UserModel> listUser = userService.getUserListbyRole(role);
            notifikasi.setWaktuDibuat(new Date());
            notifikasi.setListUser(listUser);
            notifikasiDb.save(notifikasi);
            for (UserModel user : listUser) {
                user.getListNotifikasi().add(notifikasi);
            }
        } else {
            List<UserModel> listUser = new ArrayList<UserModel>();
            UserModel userPenerima = userService.getUserbyIdUser(notifikasi.getIdPenerima());
            listUser.add(userPenerima);
            notifikasi.setWaktuDibuat(new Date());
            notifikasi.setListUser(listUser);
            notifikasiDb.save(notifikasi);
            userPenerima.getListNotifikasi().add(notifikasi);
        }
    }

    @Override
    public List<NotifikasiModel> getNotifListByUserAndRole(Long idPenerima, Long idRole, Boolean isNotif) {
        return notifikasiDb.findByIdPenerimaOrIdRoleAndIsNotifOrderByWaktuDibuatDesc(idPenerima, idRole, isNotif);
    }

}