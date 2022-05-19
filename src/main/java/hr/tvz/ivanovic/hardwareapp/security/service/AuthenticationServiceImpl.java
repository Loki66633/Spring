package hr.tvz.ivanovic.hardwareapp.security.service;

import hr.tvz.ivanovic.hardwareapp.security.command.LoginCommand;
import hr.tvz.ivanovic.hardwareapp.security.domain.User;
import hr.tvz.ivanovic.hardwareapp.security.dto.LoginDTO;
import hr.tvz.ivanovic.hardwareapp.security.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();


    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthenticationServiceImpl(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<LoginDTO> login(LoginCommand command) {
        Optional<User> user = userRepository.findByUsername(command.getUsername());

        if (user.isEmpty() || !isMatchingPassword(command.getPassword(), user.get().getPassword())) {
            return Optional.empty();
        }

        return Optional.of(
                new LoginDTO(jwtService.createJwt(user.get()))
        );
    }

    private boolean isMatchingPassword(String rawPassword, String encryptedPassword) {
        // TODO - implementirati provjeru odgovara li lozinka, koju je unio korisnik, enkriptiranoj lozinki u bazi
        boolean isPasswordMatches = bcrypt.matches(rawPassword, encryptedPassword);

        if(isPasswordMatches){
            return true;
        }

            throw new UnsupportedOperationException();

    }
}
