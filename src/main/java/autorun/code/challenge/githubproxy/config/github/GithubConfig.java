package autorun.code.challenge.githubproxy.config.github;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "github")
@Setter
@Getter
public class GithubConfig {
    private String apiUrl;
    private String token;
    private String apiVersion;
}
