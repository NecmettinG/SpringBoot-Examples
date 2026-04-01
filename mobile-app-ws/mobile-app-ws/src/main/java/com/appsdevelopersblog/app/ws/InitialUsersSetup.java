package com.appsdevelopersblog.app.ws;


import com.appsdevelopersblog.app.ws.io.entity.AuthorityEntity;
import com.appsdevelopersblog.app.ws.io.entity.RoleEntity;
import com.appsdevelopersblog.app.ws.io.repository.AuthorityRepository;
import com.appsdevelopersblog.app.ws.io.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;

@Component
public class InitialUsersSetup {

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    RoleRepository roleRepository;

    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event){

        System.out.println("From Application Ready Event...\nonApplication method is started.");

        AuthorityEntity readAuthority = createAuthority("READ_AUTHORITY");
        AuthorityEntity writeAuthority = createAuthority("WRITE_AUTHORITY");
        AuthorityEntity deleteAuthority = createAuthority("DELETE_AUTHORITY");

        RoleEntity userRole = createRole("ROLE_USER", Arrays.asList(readAuthority, writeAuthority));
        RoleEntity adminRole = createRole("ROLE_ADMIN", Arrays.asList(readAuthority, writeAuthority, deleteAuthority));
    }

    @Transactional
    protected AuthorityEntity createAuthority(String name){

        AuthorityEntity authority = authorityRepository.findByName(name);

        if(authority == null){

            authority = new AuthorityEntity(name);
            authorityRepository.save(authority);
        }
        return authority;
    }

    @Transactional
    protected RoleEntity createRole(String name, Collection<AuthorityEntity> authorities){

        RoleEntity role = roleRepository.findByName(name);

        if(role == null){

            role = new RoleEntity(name);
            role.setAuthorities(authorities);
            roleRepository.save(role);
        }

        return role;
    }
}
