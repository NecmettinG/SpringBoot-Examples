package com.neco.auth_tutorial.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data //This is a lombok annotation which adds getter-setters to our class according to attributes.
@Builder // This lombok annotation will help us to built our object in easy way with using design pattern builder.
@NoArgsConstructor //This lombok annotation creates a constructor with no parameters for this class.
@AllArgsConstructor//This lombok annotation creates a constructor with all parameters for this class. @Builder needs this to operate.
@Entity
@Table(name = "_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue//strategy = GenerationType.AUTO is default. Strategy will be SEQUENCE automatically because we use PostgreSQL.
    private Integer id;

    private String firstname;

    private String lastname;

    private String email;

    private String passw;/*I wrote password attribute like this because Lombok's and UserDetails' getPassword() methods are-
    conflicting each other If I declare this attribute as "password". Currently, Lombok will have public String getPassw().
    You can change it back to "password" right after Overriding String getPassword() from UserDetails,
    but I'm gonna keep it like this :)*/

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return passw;
    }

    @Override
    public String getUsername() { //We will use email attribute as username.
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; //return UserDetails.super.isAccountNonExpired(); is default implementation.
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; //return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; //return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return true; //return UserDetails.super.isEnabled();
    }
}
