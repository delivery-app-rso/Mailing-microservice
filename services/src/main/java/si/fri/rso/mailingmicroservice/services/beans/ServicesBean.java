package si.fri.rso.mailingmicroservice.services.beans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;

import org.json.JSONObject;

import si.fri.rso.mailingmicroservice.services.config.ServicesProperties;

@ApplicationScoped
public class ServicesBean {

    @Inject
    ServicesProperties servicesProperties;

    private Client httpClient;

    @PostConstruct
    public void init() {
        this.httpClient = ClientBuilder.newClient();

    }

    public JSONObject getItem(Integer itemId) {
        try {
            String itemStringObject = this.httpClient
                    .target(servicesProperties.getItemsServiceHost() + "/v1/items/" + itemId)
                    .request().get(new GenericType<String>() {
                    });

            JSONObject itemJsonObject = new JSONObject(itemStringObject);
            System.out.println(itemJsonObject);
            return itemJsonObject;
        } catch (WebApplicationException | ProcessingException e) {
            System.out.println(e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }
}
