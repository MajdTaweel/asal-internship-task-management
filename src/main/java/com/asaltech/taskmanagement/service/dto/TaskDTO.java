package com.asaltech.taskmanagement.service.dto;

import com.asaltech.taskmanagement.domain.enumeration.TaskStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link com.asaltech.taskmanagement.domain.Task} entity.
 */
public class TaskDTO implements Serializable {

    private String id;

    @NotNull
    @Size(min = 3, max = 255)
    private String title;

    @NotNull
    private TaskStatus status;

    private String description;

    private Instant deadline;

    private Set<UserDTO> assignees = new HashSet<>();

    private String releaseId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getDeadline() {
        return deadline;
    }

    public void setDeadline(Instant deadline) {
        this.deadline = deadline;
    }

    public Set<UserDTO> getAssignees() {
        return assignees;
    }

    public void setAssignees(Set<UserDTO> users) {
        this.assignees = users;
    }

    public String getReleaseId() {
        return releaseId;
    }

    public void setReleaseId(String releaseId) {
        this.releaseId = releaseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskDTO)) {
            return false;
        }

        return id != null && id.equals(((TaskDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", status='" + getStatus() + "'" +
            ", description='" + getDescription() + "'" +
            ", deadline='" + getDeadline() + "'" +
            ", assignees='" + getAssignees() + "'" +
            ", releaseId='" + getReleaseId() + "'" +
            "}";
    }
}
