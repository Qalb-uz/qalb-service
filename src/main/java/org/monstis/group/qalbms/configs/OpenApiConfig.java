package org.monstis.group.qalbms.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addServersItem(new Server().url("http://www.qalb.uz:8080/").description("Local server"))
                 .addServersItem(new Server().url("https://api.example.com").description("Production server"));
    }
}