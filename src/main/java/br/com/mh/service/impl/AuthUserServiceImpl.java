package br.com.mh.service.impl;

import br.com.mh.exception.AuthException;
import br.com.mh.model.AuthUser;
import br.com.mh.repository.AuthUserRepository;
import br.com.mh.service.AuthUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class AuthUserServiceImpl implements AuthUserService {

    private static final String MESSAGE_ERROR = "Aconteceu um erro no servidor.";

    private final AuthUserRepository authUserRepository;

    @Autowired
    public AuthUserServiceImpl(AuthUserRepository authUserRepository) {

        this.authUserRepository = authUserRepository;
    }

    @Override
    public AuthUser getAuthUserByNomeUsuario(AuthUser authUser) {

        return authUserRepository.findByNomeUsuario(authUser.getNomeUsuario())
                .orElseThrow(() -> {
                    log.error(String.format("Erro ao buscar usuário %s no banco de dados.", authUser.getNomeUsuario()));
                    return new AuthException(HttpStatus.INTERNAL_SERVER_ERROR, MESSAGE_ERROR);
                });
    }

    @Override
    public AuthUser addAuthUser(AuthUser authUser) {

        try {

            return authUserRepository.save(authUser);
        } catch (DataIntegrityViolationException e) {

            throw new AuthException(HttpStatus.BAD_REQUEST, String.format("Já existe um usuário com o nome %s", authUser.getNomeUsuario()));
        } catch (Exception e) {

            log.error("Erro ao salvar o usuário: ".concat(e.getLocalizedMessage()));

            throw new AuthException(HttpStatus.INTERNAL_SERVER_ERROR, MESSAGE_ERROR);
        }
    }

}
