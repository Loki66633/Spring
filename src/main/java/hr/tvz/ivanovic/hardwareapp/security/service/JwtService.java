package hr.tvz.ivanovic.hardwareapp.security.service;

import hr.tvz.ivanovic.hardwareapp.security.domain.User;

public interface JwtService {

    boolean authenticate(String token);

    String createJwt(User user);

}
