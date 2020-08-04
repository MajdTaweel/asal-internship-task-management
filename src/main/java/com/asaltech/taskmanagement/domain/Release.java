package com.asaltech.taskmanagement.domain;

import com.asaltech.taskmanagement.domain.enumeration.Status;
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
    @Field("date_created")
    private String dateCreated;

    @NotNull
    @Field("created_by")
    private String createdBy;

    @NotNull
    @Field("type")
    private String type;

    @NotNull
    @Field("status")
    private Status status;

    @Field("deadline")
    private Instant deadline;

    @DBRef
    @Field("task")
    private Set<Task> tasks = new HashSet<>();

    @DBRef
    @Field("users")
    private Set<User> users = new HashSet<>();

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

    public void setTitle(String title) {
        this.title = title;
    }

    public Release title(String title) {
        this.title = title;
        return this;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Release dateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Release createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Release type(String type) {
        this.type = type;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Release status(Status status) {
        this.status = status;
        return this;
    }

    public Instant getDeadline() {
        return deadline;
    }

    public void setDeadline(Instant deadline) {
        this.deadline = deadline;
    }

    public Release deadline(Instant deadline) {
        this.deadline = deadline;
        return this;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Release users(Set<User> users) {
        this.users = users;
        return this;
    }

    public Release addUser(User user) {
        this.users.add(user);
        return this;
    }

    public Release removeUser(User user) {
        this.users.remove(user);
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
            ", dateCreated='" + getDateCreated() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", type='" + getType() + "'" +
            ", status='" + getStatus() + "'" +
            ", deadline='" + getDeadline() + "'" +
            "}";
    }
}
