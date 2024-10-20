package br.com.mh.service;

import br.com.mh.model.AuthUser;

public interface AuthService {

    String authenticateUser(AuthUser authUser);
    AuthUser registerUser(AuthUser authUser);
}
