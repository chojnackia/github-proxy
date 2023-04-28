package autorun.code.challenge.githubproxy.mapper.github;

import autorun.code.challenge.githubproxy.domain.github.BranchDomain;
import autorun.code.challenge.githubproxy.endpoint.github.dto.BranchDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        uses = {
                CommitMapper.class,
        })
public interface BranchMapper {
    BranchDto mapFromDomainToDto(BranchDomain branchDomain);
}
