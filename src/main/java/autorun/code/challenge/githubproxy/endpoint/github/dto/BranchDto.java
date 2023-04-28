package autorun.code.challenge.githubproxy.endpoint.github.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BranchDto {
    private String name;
    private CommitDto commit;
}
