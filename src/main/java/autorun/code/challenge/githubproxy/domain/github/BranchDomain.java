package autorun.code.challenge.githubproxy.domain.github;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BranchDomain {
    private String name;
    private CommitDomain commit;
}
