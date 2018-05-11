package fr.devlogic.exemples.rs;

import fr.devlogic.exemples.rs.model.User;
import fr.devlogic.exemples.rs.model.UserInformation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserService {

    @RequestMapping(method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes =  {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            path = "/info")
    public @ResponseBody UserInformation getInfo(@RequestBody final User user) {
        final UserInformation userInformation = new UserInformation();

        userInformation.setStatus(user.getUser() + " is ok");
        return userInformation;

    }
}
