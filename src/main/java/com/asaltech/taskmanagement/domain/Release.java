package com.asaltech.taskmanagement.domain;

import com.asaltech.taskmanagement.domain.enumeration.ReleaseStatus;
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
 * A Release.
 */
@Document(collection = "release")
public class Release implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(min = 3, max = 255)
    @Field("title")
    private String title;

    @NotNull
    @Field("type")
    private String type;

    @NotNull
    @Field("status")
    private ReleaseStatus status;

    @Field("deadline")
    private Instant deadline;

    @DBRef
    @Field("task")
    private Set<Task> tasks = new HashSet<>();

    @DBRef
    @Field("teams")
    private Set<User> teams = new HashSet<>();

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

    public Release title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public Release type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ReleaseStatus getStatus() {
        return status;
    }

    public Release status(ReleaseStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ReleaseStatus status) {
        this.status = status;
    }

    public Instant getDeadline() {
        return deadline;
    }

    public Release deadline(Instant deadline) {
        this.deadline = deadline;
        return this;
    }

    public void setDeadline(Instant deadline) {
        this.deadline = deadline;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public Release tasks(Set<Task> tasks) {
        this.tasks = tasks;
        return this;
    }

    public Release addTask(Task task) {
        this.tasks.add(task);
        task.setRelease(this);
        return this;
    }

    public Release removeTask(Task task) {
        this.tasks.remove(task);
        task.setRelease(null);
        return this;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Set<User> getTeams() {
        return teams;
    }

    public void setTeams(Set<User> users) {
        this.teams = users;
    }

    public Release teams(Set<User> users) {
        this.teams = users;
        return this;
    }

    public Release addTeam(User user) {
        this.teams.add(user);
        return this;
    }

    public Release removeTeam(User user) {
        this.teams.remove(user);
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Release)) {
            return false;
        }
        return id != null && id.equals(((Release) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Release{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            ", deadline='" + getDeadline() + "'" +
            "}";
    }
}
