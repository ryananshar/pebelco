package propensi.tugas.pebelco.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import propensi.tugas.pebelco.model.NotifikasiModel;
import propensi.tugas.pebelco.model.UserModel;

public class SomeUserDetails implements UserDetails{
    private UserModel user;

    public SomeUserDetails(UserModel user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().getNamaRole()));
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserModel getUser() {
        return this.user;
    }

    public Long getIdUser() {
        return this.user.getIdUser();
    }

    public String getNamaPanjang() {
        return this.user.getNamaPanjang();
    }

    public List<NotifikasiModel> getListNotifikasi() {
        return this.user.getListNotifikasi();
    }

    public String getRole() {
        return this.user.getRole().getNamaRole();
    }
    
}
