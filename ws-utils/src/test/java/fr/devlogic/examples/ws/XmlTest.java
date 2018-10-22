package fr.devlogic.examples.ws;

import fr.devlogic.examples.ws.model.User;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class XmlTest {

    @Test
    public void xml() throws JAXBException {
        final User user = new User();
        user.setUser("ljoyeux");
        user.setPassword("coucou");

        final String xml = WSUtils.objectToXml(user, new ByteArrayOutputStream()).toString();
        System.out.println(xml);

        final User xmlUser = WSUtils.xmlToObject(new ByteArrayInputStream(xml.getBytes()), User.class);

        Assert.assertEquals(user, xmlUser);
    }

    @Test
    public void json() throws IOException {
        final User user = new User();
        user.setUser("ljoyeux");
        user.setPassword("coucou");

        final String json = WSUtils.objectToJson(user, new ByteArrayOutputStream()).toString();
        System.out.println(json);

        final User xmlUser = WSUtils.jsonToObject(new ByteArrayInputStream(json.getBytes()), User.class);

        Assert.assertEquals(user, xmlUser);
    }


}
