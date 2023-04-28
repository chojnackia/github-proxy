package autorun.code.challenge.githubproxy.domain.github;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class RepositoryDomain {
    private String name;
    private OwnerDomain owner;
    private List<BranchDomain> branches;
    private boolean fork;
}
