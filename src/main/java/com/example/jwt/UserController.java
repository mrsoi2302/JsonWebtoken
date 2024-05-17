package com.example.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserDetailsService userDetailsService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody User user) {
        userDetailsService.createUser(user);
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome!";
    }

}
