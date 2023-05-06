package autorun.code.challenge.githubproxy.business.github;

import autorun.code.challenge.githubproxy.config.github.GithubConfig;
import autorun.code.challenge.githubproxy.domain.github.BranchDomain;
import autorun.code.challenge.githubproxy.domain.github.RepositoryDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GithubApiClient {

    private final GithubConfig githubConfig;
    private final WebClient webClient;

    public List<RepositoryDomain> getUserRepositories(String username) {
        String url = githubConfig.getApiUrl() + "/users/" + username + "/repos";

        List<RepositoryDomain> repositories = webClient.get()
                .uri(url)
                .headers(headers -> createHeaders())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new HttpClientErrorException(response.statusCode())))
                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new HttpServerErrorException(response.statusCode())))
                .bodyToMono(new ParameterizedTypeReference<List<RepositoryDomain>>() {
                })
                .block();

        if (repositories == null) {
            return Collections.emptyList();
        }

        return repositories;
    }

    public List<BranchDomain> getBranchesData(String repository, String owner) {
        String url = githubConfig.getApiUrl() + "/repos/" + owner + "/" + repository + "/branches";

        List<BranchDomain> branches = webClient.get()
                .uri(url)
                .headers(headers -> createHeaders())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new HttpClientErrorException(response.statusCode())))
                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new HttpServerErrorException(response.statusCode())))
                .bodyToMono(new ParameterizedTypeReference<List<BranchDomain>>() {
                })
                .block();

        if (branches == null) {
            return Collections.emptyList();
        }

        return branches;
    }


    private void createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/vnd.github+json");
        headers.set("Authorization", "Bearer " + githubConfig.getToken());
        headers.set("X-GitHub-Api-Version", githubConfig.getApiVersion());
    }
}
