package autorun.code.challenge.githubproxy.domain.github;

import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class OwnerDomain {
    private String login;
}
