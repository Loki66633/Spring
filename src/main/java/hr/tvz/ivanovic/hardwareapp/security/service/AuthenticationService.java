package hr.tvz.ivanovic.hardwareapp.security.service;

import hr.tvz.ivanovic.hardwareapp.security.command.LoginCommand;
import hr.tvz.ivanovic.hardwareapp.security.dto.LoginDTO;

import java.util.Optional;

public interface AuthenticationService {

    Optional<LoginDTO> login(LoginCommand command);

}
