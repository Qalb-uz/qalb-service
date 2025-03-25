package org.monstis.group.qalbms;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.elasticsearch.repository.config.EnableReactiveElasticsearchRepositories;


@SpringBootApplication(scanBasePackages = {"org.monstis.group.qalbms"})
@EnableConfigurationProperties()
@EnableReactiveElasticsearchRepositories
@OpenAPIDefinition(info = @Info(title = "Qalb ms project", version = "${application.version}", description = "Qalb poject API documentation"))
public class QalbMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(QalbMsApplication.class, args);

    }

}
