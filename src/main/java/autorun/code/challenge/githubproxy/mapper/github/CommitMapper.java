package autorun.code.challenge.githubproxy.mapper.github;

import autorun.code.challenge.githubproxy.domain.github.CommitDomain;
import autorun.code.challenge.githubproxy.endpoint.github.dto.CommitDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommitMapper {
    CommitDto mapFromDomainToDto(CommitDomain commitDomain);

}
