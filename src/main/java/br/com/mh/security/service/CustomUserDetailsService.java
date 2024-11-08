package br.com.mh.security.service;

import br.com.mh.model.AuthUser;
import br.com.mh.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthUserService authUserService;

    @Autowired
    public CustomUserDetailsService(AuthUserService authUserService) {

        this.authUserService = authUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return authUserService.getAuthUserByNomeUsuario(AuthUser.builder().nomeUsuario(username).build());
    }
}
