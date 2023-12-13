package mx.com.aey.util.error;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ErrorMapper {

    public static Response.ResponseBuilder toResponse(ErrorCode errorCode) {
        return Response.status(errorCode.getStatusCode()).entity(errorCode.getDescription());
    }
}
