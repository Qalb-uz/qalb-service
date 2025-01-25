package org.monstis.group.qalbms;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;


@SpringBootApplication(scanBasePackages = {"org.monstis.group.qalbms"})
@EnableConfigurationProperties()
@EnableElasticsearchRepositories
@OpenAPIDefinition(info = @Info(title = "Qalb ms project", version = "${application.version}", description = "Qalb poject API documentation"))
public class QalbMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(QalbMsApplication.class, args);

    }

}
