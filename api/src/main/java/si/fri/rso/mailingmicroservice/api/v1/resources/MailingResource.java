package si.fri.rso.mailingmicroservice.api.v1.resources;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.rso.mailingmicroservice.lib.Mail;
import si.fri.rso.mailingmicroservice.services.beans.MailingBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
@Path("/mailing")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MailingResource {

        private Logger log = Logger.getLogger(MailingResource.class.getName());

        @Inject
        private MailingBean mailingBean;

        @Operation(description = "Get all mail items.", summary = "Get all items")
        @GET
        public Response getMails() {
                List<Mail> mail = this.mailingBean.getMails();
                return Response.status(Response.Status.OK).entity(mail).build();
        }

        @Operation(description = "Get all items.", summary = "Get all items")
        @POST
        public Response sendMail() {
                Mail mail = this.mailingBean.sendEmail();
                return Response.status(Response.Status.OK).entity(mail).build();
        }
}
