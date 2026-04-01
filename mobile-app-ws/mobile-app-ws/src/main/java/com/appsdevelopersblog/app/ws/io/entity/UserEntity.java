package com.appsdevelopersblog.app.ws.io.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name= "users")/*I added @Table(name = "users") because if I write @Entity(name = "users"), I need to write:
"select user from users user where user.userId = :userId" to our JPQL query. I want to use my default class name in the query like this:
"select user from UserEntity user where user.userId = :userId"*/
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 5313493413859894403L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable=false, length=120, unique=true)//We are preventing duplicates for emails with unique=true but we need to handle exceptions.
    private String email;

    @Column(nullable = false)
    private String encryptedPassword;

    private String emailVerificationToken;

    @Column(nullable = false)
    private Boolean emailVerificationStatus = false;

    //We declared one to many relationship between UserEntity and AddressEntity. One user can have multiple addresses.
    //In a One-To-Many relationship, one side must be the "owning" side that holds the foreign key, and the other side is the "inverse" side.
    //Here, AddressEntity is chosen as the owning side (it defines @JoinColumn for "users_id").
    //"mappedBy" is placed on the inverse side (UserEntity) and takes the name of the attribute from the owning side (AddressEntity) that owns the relationship-
    //-which in this case is "userDetails". This tells Hibernate that AddressEntity manages the foreign key, so it doesn't need to create an additional join table.
    //CascadeType.ALL means whenever we delete a user, their address records from "addresses" table will also be deleted. But for avoiding-
    //-errors whenever we try to delete directly from SQL, we have to add orphanRemoval = true.
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userDetails", orphanRemoval = true)
    private List<AddressEntity> addresses;

    /*Annotation for many to many relationship. If user is deleted, role won't be affected due to cascade = {CascadeType.PERSIST}. If we used-
    cascade = CascadeType.ALL, whenever we delete a user, its role would also be deleted.
    fetch = FetchType.EAGER performs aggressive fetch. When user details are read from the database, user roles are fetched from the database-
    right away. Setting fetch type as eager is needed for authentication. Spring Framework will require that user roles.
    
    If we used fetch = FetchType.LAZY, it would perform on-demand fetch. When user details are read from the database, user roles would not be-
    fetched immediately. They would only be fetched when the roles collection is explicitly accessed for the first time. This improves-
    performance when related entities are not always needed, but in our case, we need them eagerly for authentication.*/
    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
    /*
    We are creating a joint table with this annotation with @JoinTable. In joinColumns parameter, We will create a column that will have a-
    reference to the id of the user entity. name parameter in the @JoinColumn takes the column name and referencedColumnName parameter takes the-
    attribute name in userEntity. Since we reference primary key of UserEntity in this column, we will write the attribute called "id".
    inverseJoinColumns will reference the column of other entity. roles_id column will reference the id of a role record(entity).
    */
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "users_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id",referencedColumnName = "id"))
    //One user can have many roles so they need to be in a Collection.
    private Collection<RoleEntity> roles;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getEmailVerificationToken() {
        return emailVerificationToken;
    }

    public void setEmailVerificationToken(String emailVerificationToken) {
        this.emailVerificationToken = emailVerificationToken;
    }

    public Boolean getEmailVerificationStatus() {
        return emailVerificationStatus;
    }

    public void setEmailVerificationStatus(Boolean emailVerificationStatus) {
        this.emailVerificationStatus = emailVerificationStatus;
    }

    public List<AddressEntity> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressEntity> addresses) {
        this.addresses = addresses;
    }

    public Collection<RoleEntity> getRoles(){

        return roles;
    }

    public void setRoles(Collection<RoleEntity> roles){

        this.roles = roles;
    }
}
