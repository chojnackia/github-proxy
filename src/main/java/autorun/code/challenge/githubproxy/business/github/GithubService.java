package autorun.code.challenge.githubproxy.business.github;

import autorun.code.challenge.githubproxy.domain.github.RepositoryDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GithubService {

    private final GithubApiClient githubApiClient;

    public List<RepositoryDomain> getUsersNotForkedRepositories(String username) {
        List<RepositoryDomain> repositories = githubApiClient.getUserRepositories(username)
                .stream()
                .filter(repository -> !repository.isFork())
                .toList();

        repositories.forEach(repository -> repository.setBranches(
                githubApiClient.getBranchesData(repository.getName(), repository.getOwner().getLogin())));

        return repositories;
    }
}
