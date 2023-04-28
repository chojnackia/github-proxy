package autorun.code.challenge.githubproxy.endpoint.github.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RepositoryDto {
    private String name;
    private OwnerDto owner;
    private List<BranchDto> branches;
}
