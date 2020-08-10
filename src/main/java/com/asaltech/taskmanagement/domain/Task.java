package com.asaltech.taskmanagement.domain;

import com.asaltech.taskmanagement.domain.enumeration.TaskStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Task.
 */
@Document(collection = "task")
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(min = 3, max = 255)
    @Field("title")
    private String title;

    @NotNull
    @Field("status")
    private TaskStatus status;

    @Field("description")
    private String description;

    @Field("deadline")
    private Instant deadline;

    @DBRef
    @Field("assignees")
    private Set<User> assignees = new HashSet<>();

    @DBRef
    @Field("release")
    @JsonIgnoreProperties(value = "tasks", allowSetters = true)
    private Release release;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Task title(String title) {
        this.title = title;
        return this;
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

    public Task status(TaskStatus status) {
        this.status = status;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Task description(String description) {
        this.description = description;
        return this;
    }

    public Instant getDeadline() {
        return deadline;
    }

    public Task deadline(Instant deadline) {
        this.deadline = deadline;
        return this;
    }

    public void setDeadline(Instant deadline) {
        this.deadline = deadline;
    }

    public Set<User> getAssignees() {
        return assignees;
    }

    public void setAssignees(Set<User> users) {
        this.assignees = users;
    }

    public Task assignees(Set<User> users) {
        this.assignees = users;
        return this;
    }

    public Task addAssignee(User user) {
        this.assignees.add(user);
        return this;
    }

    public Task removeAssignee(User user) {
        this.assignees.remove(user);
        return this;
    }

    public Release getRelease() {
        return release;
    }

    public Task release(Release release) {
        this.release = release;
        return this;
    }

    public void setRelease(Release release) {
        this.release = release;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Task)) {
            return false;
        }
        return id != null && id.equals(((Task) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Task{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", status='" + getStatus() + "'" +
            ", description='" + getDescription() + "'" +
            ", deadline='" + getDeadline() + "'" +
            "}";
    }
}
