package io.github.lucasbarbosarocha.quarkussocial.core.domain.error;

import lombok.Getter;

@Getter
public class GenericResponse {
    private final String message;

    public GenericResponse(String message) {
        this.message = message;
    }
}
