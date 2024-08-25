package br.com.mh.service;

import br.com.mh.entity.AuthUser;

public interface AuthUserService {

    AuthUser getAuthUserByNomeUsuario(AuthUser authUser);
    AuthUser addAuthUser(AuthUser authUser);
}
