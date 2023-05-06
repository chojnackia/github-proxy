package autorun.code.challenge.githubproxy.business.github;

import autorun.code.challenge.githubproxy.domain.github.RepositoryDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GithubService {

    private final GithubApiClient githubApiClient;

    public Mono<List<RepositoryDomain>> getUsersNotForkedRepositories(String username) {
        return githubApiClient.getUserRepositories(username)
                .filter(repository -> !repository.isFork())
                .flatMap(repository ->
                        githubApiClient.getBranchesData(repository.getName(), repository.getOwner().getLogin())
                                .map(branches -> {
                                    repository.setBranches(branches);
                                    return repository;
                                }))
                .collectList();
    }
}
