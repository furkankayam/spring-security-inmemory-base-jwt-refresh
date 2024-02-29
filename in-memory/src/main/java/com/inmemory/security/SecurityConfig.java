package com.inmemory.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//EnableWebSecurity demezsek security filter chain uygulayamam (interseptor)
@EnableWebSecurity
//EnbaleMethodSecurity direk controller sinifinin metodlarinin securitysi
@EnableMethodSecurity
public class SecurityConfig {

    //Sifreyi encript etme
    //inmemory cok kullanilmaz neredeyse hic
    //PasswordEncoder --> sha1
    //BCryptPasswordEncoder --> base64
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService users() {

        //Farkli fieldlarimiz varsa ornek email kendi user nesnenizi yaratmalisiniz
        //USER ile ROLE_USER aynidir
        UserDetails user = User.builder()
                .username("furkan")
                .password(passwordEncoder().encode("1234"))
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("ayse")
                .password(passwordEncoder().encode("1234"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        security
                //frameoptions -> custom degerler gonderilir headerda uygulamada kullanmak icin iptal et
                .headers(x -> x.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .csrf(AbstractHttpConfigurer::disable)
                //default login page verir
                //.formLogin(Customizer.withDefaults())
                //login form yok, kullanici adi sifreyi headerla gondeririz
                .formLogin(AbstractHttpConfigurer::disable)
                //permitAll --> ne gelirse kabul et
                //authenticated --> authentice olsun yeterli
                //hasRole --> sadece bu role izin ver
                //hasAnyRole --> bu rollerden herhangi biri varsa izin ver
                .authorizeHttpRequests(x -> x.requestMatchers("/public/**", "/auth/**").permitAll())
                .authorizeHttpRequests(x -> x.requestMatchers("/private/user/**").hasRole("USER"))
                .authorizeHttpRequests(x -> x.requestMatchers("/private/admin/**").hasRole("ADMIN"))
                //yukaridakiler disindaki hepsini authenticate et
                .authorizeHttpRequests(x -> x.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());

        return security.build();
    }

}
