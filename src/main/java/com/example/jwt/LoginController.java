package com.example.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final UserDetailsService userDetailsService;

    @PostMapping("/{alg}")
    public String login(@PathVariable String alg,@RequestBody User user) {
        return userDetailsService.generateToken(alg,user);
    }

}
