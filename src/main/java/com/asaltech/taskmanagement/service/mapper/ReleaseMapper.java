package com.asaltech.taskmanagement.service.mapper;


import com.asaltech.taskmanagement.domain.Release;
import com.asaltech.taskmanagement.service.dto.ReleaseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Release} and its DTO {@link ReleaseDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ReleaseMapper extends EntityMapper<ReleaseDTO, Release> {


    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "removeTask", ignore = true)
    @Mapping(target = "removeTeamMember", ignore = true)
    Release toEntity(ReleaseDTO releaseDTO);

    default Release fromId(String id) {
        if (id == null) {
            return null;
        }
        Release release = new Release();
        release.setId(id);
        return release;
    }
}
