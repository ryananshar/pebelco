package propensi.tugas.pebelco.service;

import java.time.ZonedDateTime;
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
            Date date = new Date();
            for (UserModel user : listUser) {
                NotifikasiModel newNotif = new NotifikasiModel(notifikasi.getIsNotif(), notifikasi.getDesc(), 
                                                               notifikasi.getUrl(), notifikasi.getIdPengirim(), 
                                                               notifikasi.getIdPenerima(), notifikasi.getIdRole());
                newNotif.setUser(user);
                newNotif.setWaktuDibuat(date);
                notifikasiDb.save(newNotif);
                user.getListNotifikasi().add(newNotif);
            }
        } else {
            UserModel userPenerima = userService.getUserbyIdUser(notifikasi.getIdPenerima());
            notifikasi.setWaktuDibuat(new Date());
            notifikasi.setUser(userPenerima);
            notifikasiDb.save(notifikasi);
            userPenerima.getListNotifikasi().add(notifikasi);
        }
    }

    @Override
    public List<NotifikasiModel> getNotifListByUserAndRole(Long idPenerima, Long idRole, Boolean isNotif) {
        UserModel user = userService.getUserbyIdUser(idPenerima);
        Date now = Date.from(ZonedDateTime.now().toInstant());
        Date minusNow = Date.from(ZonedDateTime.now().minusDays(3).toInstant());
        // return notifikasiDb.findByIdPenerimaOrIdRoleAndWaktuDibuatBetweenOrderByWaktuDibuatDesc(idPenerima, idRole, minusNow, now);
        return notifikasiDb.findByUserAndWaktuDibuatBetweenOrderByWaktuDibuatDesc(user, minusNow, now);
    }

}