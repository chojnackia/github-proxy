package autorun.code.challenge.githubproxy.mapper.github;

import autorun.code.challenge.githubproxy.domain.github.OwnerDomain;
import autorun.code.challenge.githubproxy.endpoint.github.dto.OwnerDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OwnerMapper {
    OwnerDto mapFromDomainToDto(OwnerDomain ownerDomain);
}
