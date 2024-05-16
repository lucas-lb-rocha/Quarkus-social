package io.github.lucasbarbosarocha.quarkussocial.infrastructure.utils;

import io.github.lucasbarbosarocha.quarkussocial.core.domain.error.GenericErrorResponse;
import io.quarkus.arc.Unremovable;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import java.util.Arrays;

@Unremovable
@ApplicationScoped
public class ResponseUtils {

    public Response getGenericErrorResponse(Response.Status status, String message, String ... args){
        final var finalMessage = getMessage(message, args);
        return Response
                .status(status)
                .entity(new GenericErrorResponse(finalMessage))
                .build();
    }

    public Response getGenericResponseSuccess(Response.Status status, String message, String ... args){
        final var finalMessage = getMessage(message, args);
        return Response
                .status(status)
                .entity(new GenericErrorResponse(finalMessage))
                .build();
    }

    private String getMessage(String message, String ... args){
        return Arrays.stream(args)
                .reduce(message, (msg, arg) -> msg.replaceFirst("\\{}", arg));
    }
}
