package co.com.microservices.oferta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = { "co.com.microservices.oferta", "co.com.microservices.oferta.api" })
public class MicroserviceOferta {

    public static void main(String[] args) throws Exception {
        new SpringApplication(MicroserviceOferta.class).run(args);
    }
}
