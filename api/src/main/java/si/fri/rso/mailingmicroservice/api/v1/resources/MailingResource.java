package si.fri.rso.mailingmicroservice.api.v1.resources;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import si.fri.rso.mailingmicroservice.lib.Attachment;
import si.fri.rso.mailingmicroservice.lib.Mail;
import si.fri.rso.mailingmicroservice.lib.MailingDto;
import si.fri.rso.mailingmicroservice.services.beans.MailingBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
        @APIResponses({
                @APIResponse(responseCode = "200", description = "List of mails", content = @Content(schema = @Schema(implementation = Mail.class, type = SchemaType.ARRAY)), headers = {
                        @Header(name = "X-Total-Count", description = "Number of objects in list") }) })
        @GET
        public Response getMails() {
                List<Mail> mail = this.mailingBean.getMails();
                return Response.status(Response.Status.OK).entity(mail).build();
        }

        @APIResponses({
                @APIResponse(responseCode = "200", description = "List of attachments", content = @Content(schema = @Schema(implementation = Attachment.class, type = SchemaType.ARRAY)), headers = {
                        @Header(name = "X-Total-Count", description = "Number of objects in list") }) })
        @GET
        @Path("attachments")
        public Response getAttachments() {
                List<Attachment> attachments = this.mailingBean.getAttachements();
                return Response.status(Response.Status.OK).entity(attachments).build();
        }

        @Operation(description = "Send mail and store it in DB.", summary = "Send mail")
        @APIResponses({
                @APIResponse(responseCode = "201", description = "Mail successfully added."),
                @APIResponse(responseCode = "405", description = "Validation error .")
        })
        @POST
        public Response sendMail(MailingDto mailingDto) {
                // TODO: Add userId check
                if(mailingDto.getType() == null) {
                        return Response.status(Response.Status.BAD_REQUEST).build();
                }

                Mail mail = this.mailingBean.sendEmail(mailingDto);
                return Response.status(Response.Status.OK).entity(mail).build();
        }
}
