package autorun.code.challenge.githubproxy.endpoint;

import autorun.code.challenge.githubproxy.business.github.GithubService;
import autorun.code.challenge.githubproxy.domain.github.BranchDomain;
import autorun.code.challenge.githubproxy.domain.github.OwnerDomain;
import autorun.code.challenge.githubproxy.domain.github.RepositoryDomain;
import autorun.code.challenge.githubproxy.endpoint.github.dto.RepositoryDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class GithubControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GithubService githubService;

    @Test
    public void testGetUserRepos() throws Exception {
        // Set up test data
        List<RepositoryDomain> repositories = Arrays.asList(
                new RepositoryDomain("repo1", OwnerDomain.builder().login("user1").build(), List.of(BranchDomain.builder().build()), false),
                new RepositoryDomain("repo2", OwnerDomain.builder().login("user1").build(), List.of(BranchDomain.builder().build()), false)
        );
        given(githubService.getUsersNotForkedRepositories("user1")).willReturn(repositories);

        // Perform GET request to endpoint
        MvcResult result = mockMvc.perform(get("/api/github/repos/user1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Verify response body matches expected data
        List<RepositoryDto> response = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });
        assertEquals(2, response.size());
        assertEquals("repo1", response.get(0).getName());
        assertEquals("repo2", response.get(1).getName());
        assertEquals("user1", response.get(0).getOwner().getLogin());
        assertEquals("user1", response.get(1).getOwner().getLogin());
    }

}

