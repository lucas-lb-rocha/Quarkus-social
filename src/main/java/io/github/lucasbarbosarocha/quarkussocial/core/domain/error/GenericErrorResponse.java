package io.github.lucasbarbosarocha.quarkussocial.core.domain.error;

import lombok.Getter;

@Getter
public class GenericErrorResponse {
    private final String message;

    public GenericErrorResponse(String message) {
        this.message = message;
    }
}
