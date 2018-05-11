package fr.devlogic.exemples.rs;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:/spring.xml")
public class Server {
    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }
}
