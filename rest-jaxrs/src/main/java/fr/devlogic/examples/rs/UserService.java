package fr.devlogic.examples.rs;

import fr.devlogic.examples.rs.model.User;
import fr.devlogic.examples.rs.model.UserInformation;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/user")
public class UserService {

    @Path("/info")
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public UserInformation getInfo(final User user) {
        final UserInformation userInformation = new UserInformation();

        userInformation.setStatus(user.getUser() + " is ok");
        return userInformation;
    }
}
