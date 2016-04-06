package com.ivanthescientist.projectmanager.domain.model;

import com.ivanthescientist.projectmanager.domain.DomainException;
import com.ivanthescientist.projectmanager.domain.SecuredEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tasks")
public class Task implements SecuredEntity {
    @Id
    @Column
    @GeneratedValue
    private long id;

    @Column
    private String name;

    @Column
    private String description;

    @ManyToOne(targetEntity = Project.class)
    private Project project;

    @ManyToOne(targetEntity = User.class)
    private User assignee;

    @Column
    private TaskStatus status;

    public Task() {}
    public Task(String name, String description, Project project)
    {
        this.name = name;
        this.description = description;
        this.project = project;
        this.status = TaskStatus.OPEN;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Project getProject() {
        return project;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void assign(User assignee) {

        if(!project.isParticipant(assignee)) {
            throw new DomainException("User is not project participant");
        }

        this.assignee = assignee;
    }

    public boolean isInProgress() {
        return this.status == TaskStatus.IN_PROGRESS;
    }

    public boolean isInReview() {
        return this.status == TaskStatus.IN_REVIEW;
    }

    public boolean isClosed() {
        return this.status == TaskStatus.CLOSED;
    }

    public void start() {
        if(this.status != TaskStatus.OPEN) {
            throw new DomainException("Task is already in progress");
        }

        this.status = TaskStatus.IN_PROGRESS;
    }

    public void finish()
    {
        if(this.status != TaskStatus.IN_PROGRESS) {
            throw  new DomainException("Task hasn't been started yet");
        }

        this.status = TaskStatus.IN_REVIEW;
    }

    public void accept()
    {
        if(this.status != TaskStatus.IN_REVIEW) {
            throw  new DomainException("Task is not in review");
        }

        this.status = TaskStatus.CLOSED;
    }

    public User getAssignee()
    {
        return assignee;
    }

    public enum TaskStatus {
        OPEN,
        IN_PROGRESS,
        IN_REVIEW,
        CLOSED
    }
}
