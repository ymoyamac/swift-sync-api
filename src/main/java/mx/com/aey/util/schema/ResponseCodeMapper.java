package mx.com.aey.util.schema;

import jakarta.ws.rs.core.Response;

public class ResponseCodeMapper {
    public static Response.ResponseBuilder toResponse(ResponseCode responseCode) {
        return Response.status(Response.Status.OK).entity(responseCode.getDescription());
    }
}
