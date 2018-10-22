package fr.devlogic.examples.ws;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class WSUtils {
    private WSUtils() {
    }

    public static <O extends OutputStream> O objectToXml(final Object o, final O os) throws JAXBException {
        final JAXBContext jaxbContext = JAXBContext.newInstance(o.getClass());
        final Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.marshal(o, os);
        return os;
    }

    public static <T> T xmlToObject(final InputStream is, final Class<T> c) throws JAXBException {
        final  JAXBContext jaxbContext = JAXBContext.newInstance(c);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (T) unmarshaller.unmarshal(is);
    }


    public static <O extends OutputStream> O objectToJson(final Object o, final O os) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(os, o);
        return os;
    }

    public static <T> T jsonToObject(final InputStream is, final Class<T> c) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return (T) mapper.readValue(is, c);
    }
}
