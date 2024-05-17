package com.example.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final UserDetailsService userDetailsService;

    @PostMapping("/{alg}")
    public String login(@PathVariable String alg,@RequestBody User user) {
        var token = userDetailsService.generateToken(alg,user);
        log.info("Key: {}", Key.Value);
        return token;
    }

}
