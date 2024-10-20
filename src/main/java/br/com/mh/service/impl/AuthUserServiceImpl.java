package br.com.mh.service.impl;

import br.com.mh.model.AuthUser;
import br.com.mh.repository.AuthUserRepository;
import br.com.mh.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthUserServiceImpl implements AuthUserService {

    private final AuthUserRepository authUserRepository;

    @Autowired
    public AuthUserServiceImpl(AuthUserRepository authUserRepository) {

        this.authUserRepository = authUserRepository;
    }

    @Override
    public AuthUser getAuthUserByNomeUsuario(AuthUser authUser) {

        Optional<AuthUser> authUserOptional = authUserRepository.findByNomeUsuario(authUser.getNomeUsuario());

        return authUserOptional.orElseThrow();
    }

    @Override
    public AuthUser addAuthUser(AuthUser authUser) {

        try {

            return authUserRepository.save(authUser);
        } catch (DataIntegrityViolationException e) {

            throw new RuntimeException(String.format("Já existe um usuário com o nome %s", authUser.getNomeUsuario()));
        } catch (Exception e) {

            throw new RuntimeException("Erro ao salvar o usuário: ".concat(e.getLocalizedMessage()));
        }
    }

}
