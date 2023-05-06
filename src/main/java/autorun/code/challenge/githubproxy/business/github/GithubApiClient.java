package autorun.code.challenge.githubproxy.business.github;

import autorun.code.challenge.githubproxy.config.github.GithubConfig;
import autorun.code.challenge.githubproxy.domain.github.BranchDomain;
import autorun.code.challenge.githubproxy.domain.github.RepositoryDomain;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GithubApiClient {

    private final GithubConfig githubConfig;
    private final WebClient webClient;

    public Flux<RepositoryDomain> getUserRepositories(String username) {
        String url = githubConfig.getApiUrl() + "/users/" + username + "/repos";
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToFlux(RepositoryDomain.class)
                .onErrorResume(WebClientResponseException.class,
                        e -> Mono.error(new HttpClientErrorException(e.getStatusCode())))
                .onErrorResume(HttpClientErrorException.class,
                        e -> Mono.error(new HttpClientErrorException(e.getStatusCode())));
    }

    public Mono<List<BranchDomain>> getBranchesData(String repository, String owner) {
        String url = githubConfig.getApiUrl() + "/repos/" + owner + "/" + repository + "/branches";
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToFlux(BranchDomain.class)
                .collectList()
                .onErrorResume(WebClientResponseException.class,
                        e -> Mono.error(new HttpClientErrorException(e.getStatusCode())))
                .onErrorResume(HttpClientErrorException.class,
                        e -> Mono.error(new HttpClientErrorException(e.getStatusCode())));
    }

    @PostConstruct
    private void createHeaders() {
        WebClient.builder()
                .defaultHeader(HttpHeaders.ACCEPT, "application/vnd.github+json")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + githubConfig.getToken())
                .defaultHeader("X-GitHub-Api-Version", githubConfig.getApiVersion())
                .build();
    }
}
