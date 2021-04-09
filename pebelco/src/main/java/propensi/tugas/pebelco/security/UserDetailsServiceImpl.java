package propensi.tugas.pebelco.security;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import propensi.tugas.pebelco.model.UserModel;
import propensi.tugas.pebelco.repository.UserDb;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private UserDb userDb;

    // @Override
    // public UserDetails loadUserByUsername(String namaPanjang) throws UsernameNotFoundException {
    //     UserModel user = userDb.findByNamaPanjang(namaPanjang);
    //     Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
    //     grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().getNamaRole()));
    //     return new User(user.getNamaPanjang(), user.getPassword(), grantedAuthorities);
    // }

    // @Override
    // public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
    //     UserModel user = userDb.findByEmail(email);
    //     Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
    //     grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().getNamaRole()));
    //     return new User(user.getEmail(), user.getPassword(), grantedAuthorities);
    // }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel user = userDb.findByEmail(email);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().getNamaRole()));
        return new User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }
    
}
