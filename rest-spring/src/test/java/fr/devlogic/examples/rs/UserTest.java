package fr.devlogic.examples.rs;

import fr.devlogic.exemples.rs.Server;
import fr.devlogic.exemples.rs.model.User;
import fr.devlogic.exemples.rs.model.UserInformation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpInputMessage;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Server.class)
@WebAppConfiguration
public class UserTest {
    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    private WebApplicationContext webApplicationContext;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void dummyTest() throws Exception {
        final User user = new User();
        user.setUser("ljoyeux");
        user.setPassword("123456");

        final String json = json(user);
        System.out.println("user json " + json);

        // WARNING : calls order is important : first set content type and accept, THEN provide the content !
        final ResultActions resultActions = mockMvc.perform(post("/user/info").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json));

        final String content = resultActions.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        System.out.println("content: " + content);

        final UserInformation userInformation = object(UserInformation.class, content);
        System.out.println(userInformation);

        Assert.assertEquals("ljoyeux is ok", userInformation.getStatus());
    }

    protected <T> T object(final Class<T> c, final String str) throws IOException {
        MockHttpInputMessage mockHttpInputMessage = new MockHttpInputMessage(str.getBytes());
        return (T) mappingJackson2HttpMessageConverter.read(c, mockHttpInputMessage);
    }

    protected String json(final Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
