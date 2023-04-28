package autorun.code.challenge.githubproxy.endpoint.github.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommitDto {
    private String sha;
}
