package autorun.code.challenge.githubproxy.endpoint.github;

import autorun.code.challenge.githubproxy.business.github.GithubService;
import autorun.code.challenge.githubproxy.endpoint.github.dto.RepositoryDto;
import autorun.code.challenge.githubproxy.mapper.github.RepositoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/github")
@RequiredArgsConstructor
public class GithubController {

    private final GithubService githubService;
    private final RepositoryMapper repositoryMapper;

    @GetMapping(value = "/repos/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<List<RepositoryDto>>> getUserRepos(@PathVariable String username,
                                                                  @RequestHeader(value = "Accept", defaultValue = "application/json") String acceptHeader) {
        Mono<List<RepositoryDto>> repositoryDtos = githubService.getUsersNotForkedRepositories(username)
                .flatMapMany(Flux::fromIterable)
                .map(repositoryMapper::mapFromDomainToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(repositoryDtos);
    }
}
