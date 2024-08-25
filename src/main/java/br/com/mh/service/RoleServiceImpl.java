package br.com.mh.service;

import br.com.mh.entity.Role;
import br.com.mh.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {

        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRoleByNome(Role role) {

        Optional<Role> roleOptional = roleRepository.findByNome(role.getNome());

        return roleOptional.orElse(null);
    }
}
