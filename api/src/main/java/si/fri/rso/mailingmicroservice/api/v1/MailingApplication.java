package si.fri.rso.mailingmicroservice.api.v1;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(title = "Mailing API", version = "v1", contact = @Contact(email = "rso@fri.uni-lj.si"), license = @License(name = "dev"), description = "API for Mailing."), servers = @Server(url = "http://localhost:8003/"))
@ApplicationPath("/v1")
public class MailingApplication extends Application {

}
