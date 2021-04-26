package propensi.tugas.pebelco.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/user/register").permitAll()
                // Kunjungan
                .antMatchers("/kunjungan").hasAnyAuthority("Staf Sales","Admin")
                .antMatchers("/kunjungan/*").hasAnyAuthority("Staf Sales","Admin")
                .antMatchers("/kunjungan/tambah").hasAnyAuthority("Staf Sales","Admin")
                .antMatchers("/kunjungan/ubah/**").hasAnyAuthority("Staf Sales","Admin")
                .antMatchers("/kunjungan/konfirmasi-hapus/**").hasAnyAuthority("Staf Sales","Admin")
                .antMatchers("/kunjungan/hapus/**").hasAnyAuthority("Staf Sales","Admin")
                // Pesananan
                .antMatchers("/pesanan").hasAnyAuthority("Staf Sales", "Sales Counter", "Manager Pemasaran", "Factory Manager", "Vice Factory Manager","Admin")
                .antMatchers("/pesanan/*").hasAnyAuthority("Staf Sales", "Sales Counter", "Manager Pemasaran", "Factory Manager", "Vice Factory Manager","Admin")
                .antMatchers("/pesanan/req/").hasAnyAuthority("Staf Sales", "Admin")
                .antMatchers("/pesanan/tambah").hasAnyAuthority("Staf Sales","Sales Counter", "Admin")
                .antMatchers("/pesanan/ubah/**").hasAnyAuthority("Sales Counter","Admin")
                .antMatchers("/pesanan/ubah-status/**").hasAnyAuthority("Sales Counter","Admin")
                .antMatchers("/pesanan/konfirmasi-ubah-status/**").hasAnyAuthority("Sales Counter","Admin")
                .antMatchers("/pesanan/hapus/**").hasAnyAuthority("Sales Counter", "Admin")
                .antMatchers("/pesanan/konfirmasi-hapus/**").hasAnyAuthority("Sales Counter", "Admin")
                // Komplain
                .antMatchers("/komplain").hasAnyAuthority("Staf Sales", "Admin Komplain", "Manager Pemasaran", "Factory Manager", "Vice Factory Manager","Admin")
                .antMatchers("/komplain/*").hasAnyAuthority("Staf Sales", "Admin Komplain", "Manager Pemasaran", "Factory Manager", "Vice Factory Manager","Admin")
                .antMatchers("/komplain/req/").hasAnyAuthority("Staf Sales", "Admin")
                .antMatchers("/komplain/tambah").hasAnyAuthority("Staf Sales","Admin Komplain", "Admin")
                .antMatchers("/komplain/ubah/**").hasAnyAuthority("Admin Komplain","Admin")
                .antMatchers("/komplain/ubah-status/**").hasAnyAuthority("Admin Komplain","Admin")
                .antMatchers("/komplain/konfirmasi-ubah-status/**").hasAnyAuthority("Admin Komplain","Admin")
                .antMatchers("/komplain/hapus/**").hasAnyAuthority("Admin Komplain", "Admin")
                .antMatchers("/komplain/konfirmasi-hapus/**").hasAnyAuthority("Admin Komplain", "Admin")
                // Produk
                .antMatchers("/produk").hasAnyAuthority("Sales Counter", "Staf Sales","Admin Komplain", "Staf Pengiriman","Manager Pemasaran", "Factory Manager", "Vice Factory Manager","Admin")
                .antMatchers("/produk/*").hasAnyAuthority("Sales Counter", "Staf Sales","Admin Komplain", "Staf Pengiriman","Manager Pemasaran", "Factory Manager", "Vice Factory Manager","Admin")
                .antMatchers("/produk/tambah/").hasAnyAuthority( "Factory Manager", "Admin")
                .antMatchers("/produk/ubah/**").hasAnyAuthority( "Vice Factory Manager","Sales Counter", "Admin")
                .antMatchers("/produk/ubah/stok/***").hasAnyAuthority( "Sales Counter", "Admin")
                .antMatchers("/produk/konfirmasi-hapus/**").hasAnyAuthority("Factory Manager","Admin")
                .antMatchers("/produk/hapus/**").hasAnyAuthority("Factory Manager","Admin")

                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").usernameParameter("email").permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login").permitAll()
                .and().cors().and().csrf().disable();
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    // @Autowired
    // public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    //     auth.inMemoryAuthentication()
    //             .passwordEncoder(encoder())
    //             .withUser("odading").password(encoder().encode("mangoleh"))
    //             .roles("USER");
    // }

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }
}