package com.example.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;

    public String toString() {
        return """
                {
                    "id": "%d",
                    "username": "%s"
                }
                """.formatted(id, username);
    }
}
