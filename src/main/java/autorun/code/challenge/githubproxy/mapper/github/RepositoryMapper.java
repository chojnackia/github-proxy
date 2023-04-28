package autorun.code.challenge.githubproxy.mapper.github;

import autorun.code.challenge.githubproxy.domain.github.RepositoryDomain;
import autorun.code.challenge.githubproxy.endpoint.github.dto.RepositoryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {
                BranchMapper.class,
                OwnerMapper.class,
        })
public interface RepositoryMapper {
    RepositoryDto mapFromDomainToDto(RepositoryDomain repositoryDomain);
}
