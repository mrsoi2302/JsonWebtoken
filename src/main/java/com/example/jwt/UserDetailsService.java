package com.example.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) userRepository.findByUsername(username).get();
    }

    public String generateToken(String alg, User user) {
        try {
            return tokenProvider.tokenGenerator(SignatureAlgorithm.valueOf(alg),user.getUsername());
        } catch (Exception e) {
            if(e instanceof IllegalArgumentException){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Algorithm", e);
            }
            throw new RuntimeException("Invalid User");
        }
    }

    public void createUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("User already exists");
        }
        userRepository.save(user);
        new UserDto(user.getId(), user.getUsername());
    }

}
