package autorun.code.challenge.githubproxy.business;

import autorun.code.challenge.githubproxy.business.github.GithubApiClient;
import autorun.code.challenge.githubproxy.business.github.GithubService;
import autorun.code.challenge.githubproxy.domain.github.BranchDomain;
import autorun.code.challenge.githubproxy.domain.github.OwnerDomain;
import autorun.code.challenge.githubproxy.domain.github.RepositoryDomain;
import autorun.code.challenge.githubproxy.mapper.github.RepositoryMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GithubServiceTest {


    @Mock
    private GithubApiClient githubApiClient;

    @Mock
    private RepositoryMapper repositoryMapper;

    @InjectMocks
    private GithubService githubService;

    @Test
    void getUsersNotForkedRepositories_shouldReturnListOfRepositories() {
        // arrange
        String username = "testuser";
        RepositoryDomain repositoryDomain = RepositoryDomain.builder()
                .name("test-repo")
                .fork(false)
                .build();
        OwnerDomain ownerDomain = OwnerDomain.builder()
                .login("testuser")
                .build();
        repositoryDomain.setOwner(ownerDomain);

        List<RepositoryDomain> repositories = new ArrayList<>();
        repositories.add(repositoryDomain);

        List<BranchDomain> branches = new ArrayList<>();
        BranchDomain branchDomain = BranchDomain.builder()
                .name("test-branch")
                .build();
        branches.add(branchDomain);

        given(githubApiClient.getUserRepositories(username)).willReturn(repositories);
        given(githubApiClient.getBranchesData("test-repo", "testuser")).willReturn(branches);

        // act
        List<RepositoryDomain> result = githubService.getUsersNotForkedRepositories(username);

        // assert
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(1);
        verify(githubApiClient, times(1)).getUserRepositories(username);
        verify(githubApiClient, times(1)).getBranchesData("test-repo", "testuser");
    }

    @Test
    void getUsersNotForkedRepositories_shouldReturnEmptyListWhenUserHasNoRepositories() {
        // arrange
        String username = "testuser";
        List<RepositoryDomain> repositories = new ArrayList<>();

        given(githubApiClient.getUserRepositories(username)).willReturn(repositories);

        // act
        List<RepositoryDomain> result = githubService.getUsersNotForkedRepositories(username);

        // assert
        assertThat(result).isEmpty();
        verify(githubApiClient, times(1)).getUserRepositories(username);
        verify(githubApiClient, never()).getBranchesData(anyString(), anyString());
        verify(repositoryMapper, never()).mapFromDomainToDto(any(RepositoryDomain.class));
    }

    @Test
    void getUsersNotForkedRepositories_shouldReturnEmptyListWhenAllUserRepositoriesAreForks() {
        // arrange
        String username = "testuser";
        RepositoryDomain repositoryDomain = new RepositoryDomain();
        repositoryDomain.setName("test-repo");
        repositoryDomain.setFork(true);

        List<RepositoryDomain> repositories = new ArrayList<>();
        repositories.add(repositoryDomain);

        given(githubApiClient.getUserRepositories(username)).willReturn(repositories);

        // act
        List<RepositoryDomain> result = githubService.getUsersNotForkedRepositories(username);

        // assert
        assertThat(result).isEmpty();
        verify(githubApiClient, times(1)).getUserRepositories(username);
        verify(githubApiClient, never()).getBranchesData(anyString(), anyString());
        verify(repositoryMapper, never()).mapFromDomainToDto(any(RepositoryDomain.class));
    }
}