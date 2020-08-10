package com.asaltech.taskmanagement.service.mapper;


import com.asaltech.taskmanagement.domain.Task;
import com.asaltech.taskmanagement.service.dto.TaskDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Task} and its DTO {@link TaskDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ReleaseMapper.class})
public interface TaskMapper extends EntityMapper<TaskDTO, Task> {

    @Mapping(source = "release.id", target = "releaseId")
    TaskDTO toDto(Task task);

    @Mapping(target = "removeAssignee", ignore = true)
    @Mapping(source = "releaseId", target = "release")
    Task toEntity(TaskDTO taskDTO);

    default Task fromId(String id) {
        if (id == null) {
            return null;
        }
        Task task = new Task();
        task.setId(id);
        return task;
    }
}
