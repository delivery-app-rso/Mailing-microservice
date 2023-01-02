package si.fri.rso.mailingmicroservice.api.v1.resources;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
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

        @Operation(description = "Get all mail items.", summary = "Get all mail items")
        @APIResponses({
                        @APIResponse(responseCode = "200", description = "List of mails", content = @Content(schema = @Schema(implementation = Mail.class, type = SchemaType.ARRAY)), headers = {
                                        @Header(name = "X-Total-Count", description = "Number of objects in list") }) })
        @GET
        public Response getMails() {
                List<Mail> mail = this.mailingBean.getMails();
                return Response.status(Response.Status.OK).entity(mail).build();
        }

        @Operation(description = "Get status", summary = "Get status")
        @APIResponses({ @APIResponse(responseCode = "200", description = "status") })
        @GET
        @Path("/status")
        public Response getStatus() {
                return Response.status(Response.Status.OK).build();
        }

        @Operation(description = "Get attachments", summary = "Get attachments")
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
                        @APIResponse(responseCode = "200", description = "Mail successfully sent and stored."),
                        @APIResponse(responseCode = "400", description = "Bad request error.")
        })
        @POST
        public Response sendMail(
                        @RequestBody(description = "DTO object with Mailing data.", required = true, content = @Content(schema = @Schema(implementation = MailingDto.class))) MailingDto mailingDto) {
                if (mailingDto.getType() == null && !this.mailingBean.emailDataOk(mailingDto)) {
                        return Response.status(Response.Status.BAD_REQUEST).build();
                }

                Mail mail = this.mailingBean.sendEmail(mailingDto);
                return Response.status(Response.Status.OK).entity(mail).build();
        }
}
