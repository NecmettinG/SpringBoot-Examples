package com.appsdevelopersblog.app.ws.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.appsdevelopersblog.app.ws.io.entity.AuthorityEntity;
import com.appsdevelopersblog.app.ws.io.entity.RoleEntity;
import com.appsdevelopersblog.app.ws.io.entity.UserEntity;

public class UserPrincipal implements UserDetails {

    private static final long serialVersionUID = -7530187709860249942L;

    private UserEntity userEntity;

    private String userId;

    public UserPrincipal(UserEntity userEntity) {
        this.userEntity = userEntity;
        this.userId = userEntity.getUserId();
    }

    /*
     * getAuthorities:
     * This method is used by Spring Security to get the authorities (roles and privileges) granted to the user.
     * Spring Security uses this collection of GrantedAuthority objects to perform endpoint authorization 
     * (e.g., checking @Secured, @PreAuthorize, or HttpSecurity configurations).
     * 
     * 1. We extract the roles assigned to the user entity.
     * 2. For each role, we create a SimpleGrantedAuthority and add it to our list of authorities.
     * 3. We also collect all specific privileges (authorities) associated with each role 
     *    and map them to SimpleGrantedAuthority as well.
     * 
     * Doing this allows Spring Security to handle both role-based (e.g., hasRole('ADMIN')) 
     * and authority-based (e.g., hasAuthority('READ_AUTHORITY')) access control smoothly.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<>();
        List<AuthorityEntity> authorityEntities = new ArrayList<>();

        //Get User Roles:

        Collection<RoleEntity> roles = userEntity.getRoles();

        if(roles == null) return authorities;

        roles.forEach((role)-> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            authorityEntities.addAll(role.getAuthorities());
        });

        authorityEntities.forEach((authorityEntity)-> {
            authorities.add(new SimpleGrantedAuthority(authorityEntity.getName()));

        });

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.userEntity.getEncryptedPassword();
    }

    @Override
    public String getUsername() {
        return this.userEntity.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.userEntity.getEmailVerificationStatus();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {

        this.userId = userId;
    }

}