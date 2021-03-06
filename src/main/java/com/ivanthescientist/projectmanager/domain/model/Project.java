package com.ivanthescientist.projectmanager.domain.model;

import com.ivanthescientist.projectmanager.domain.DomainException;
import com.ivanthescientist.projectmanager.domain.SecuredEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "projects")
public class Project implements SecuredEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String name;

    @Column
    private String description;

    @ManyToOne(targetEntity = Organization.class)
    private Organization organization;

    @OneToMany(targetEntity = User.class, fetch = FetchType.EAGER)
    private List<User> participants = new ArrayList<>();

    public Project()
    {
    }

    public Project(String name, String description, Organization organization)
    {
        this.name = name;
        this.description = description;
        this.organization = organization;
    }

    public void updateInfo(String name, String description)
    {
        this.name = name;
        this.description = description;
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

    public List<User> getParticipants()
    {
        return participants;
    }

    public boolean isParticipant(User user)
    {
        return this.participants.contains(user);
    }

    public void addParticipant(User user)
    {
        if(this.participants.contains(user)) {
            throw new DomainException("Already participant");
        }

        if(!organization.isMember(user)) {
            throw new DomainException("User does not belong to project's organization");
        }

        participants.add(user);
    }

    public void removeParticipant(User user)
    {
        if(!this.participants.contains(user)) {
            throw new DomainException("Not a participant");
        }

        participants.remove(user);
    }

    public Organization getOrganization() {
        return organization;
    }
}
