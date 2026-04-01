package com.appsdevelopersblog.app.ws.io.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name="roles")
public class RoleEntity implements Serializable {

    private static final long serialVersionUID = 153134934138596457L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, length = 20)
    private String roleName;

    //We declared many to many relationship between RoleEntity and UserEntity. One role can belong to multiple users.
    //In a Many-To-Many relationship, one side must be the "owning" side that defines the join table, and the other side is the "inverse" side.
    //Here, UserEntity is chosen as the owning side (it defines @JoinTable for "users_roles").
    //"mappedBy" is placed on the inverse side (RoleEntity) and takes the name of the attribute from the owning side (UserEntity) that owns the relationship-
    //-which in this case is "roles". This tells Hibernate not to create an additional join table for this side of the relationship.
    @ManyToMany(mappedBy = "roles")
    private Collection<UserEntity> users;

    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "roles_authorities",
            joinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authorities_id", referencedColumnName = "id"))
    private Collection<AuthorityEntity> authorities;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Collection<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Collection<UserEntity> users) {
        this.users = users;
    }

    public Collection<AuthorityEntity> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<AuthorityEntity> authorities) {
        this.authorities = authorities;
    }
}
