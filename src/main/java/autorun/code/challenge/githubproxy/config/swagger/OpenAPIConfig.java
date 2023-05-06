package autorun.code.challenge.githubproxy.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI githubProxyOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("GithubProxy API")
                        .description("Github proxy application"));
    }
}