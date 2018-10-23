package fr.devlogic.examples.rs;

import fr.devlogic.examples.rs.model.User;
import fr.devlogic.examples.rs.model.UserInformation;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

public class UserIntegrationTest {
    public static final String BASE_URL = "http://localhost:8080/rest-jaxrs";

    @Test
    public void userInfoJaxRS() {
        final WebTarget webTarget = ClientBuilder.newClient().target(BASE_URL);
        final User user = new User();
        user.setUser("ljoyeux");
        user.setPassword("123456");
        final Response response = webTarget.path("/rs").path("/user").path("/info").request().accept(MediaType.APPLICATION_JSON).post(Entity.json(user));

        final UserInformation userInformation = response.readEntity(UserInformation.class);

        System.out.println(userInformation);
        Assert.assertEquals("ljoyeux is ok", userInformation.getStatus());
    }

    @Test
    public void userInfoSpring() {
        final RestTemplate restTemplate = new RestTemplate();

        final User user = new User();
        user.setUser("ljoyeux");
        user.setPassword("123456");

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

        final HttpEntity<User> httpEntity = new HttpEntity<>(user, httpHeaders);

        final UserInformation userInformation = restTemplate.postForObject(URI.create(BASE_URL + "/rs/user/info"), httpEntity, UserInformation.class);

        System.out.println(userInformation);
    }
}
