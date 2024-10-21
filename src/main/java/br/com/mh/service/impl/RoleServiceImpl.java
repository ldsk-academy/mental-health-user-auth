package br.com.mh.service.impl;

import br.com.mh.exception.AuthException;
import br.com.mh.model.Role;
import br.com.mh.repository.RoleRepository;
import br.com.mh.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private static final String ROLE_NOT_FOUND_MESSAGE = "Role %s não foi encontrada.";

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {

        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRoleByNome(String nome) {

        Optional<Role> roleOptional = roleRepository.findByNome(nome);

        return roleOptional
                .orElseThrow(() -> new AuthException(HttpStatus.INTERNAL_SERVER_ERROR,
                        String.format(ROLE_NOT_FOUND_MESSAGE, nome)));
    }
}
