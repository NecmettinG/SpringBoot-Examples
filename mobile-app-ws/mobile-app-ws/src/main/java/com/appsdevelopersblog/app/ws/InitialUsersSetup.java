package com.appsdevelopersblog.app.ws;


import com.appsdevelopersblog.app.ws.io.entity.AuthorityEntity;
import com.appsdevelopersblog.app.ws.io.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class InitialUsersSetup {

    @Autowired
    AuthorityRepository authorityRepository;

    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event){

        System.out.println("From Application Ready Event...\nonApplication method is started.");

        AuthorityEntity readAuthority = createAuthority("READ_AUTHORITY");
        AuthorityEntity writeAuthority = createAuthority("WRITE_AUTHORITY");
        AuthorityEntity deleteAuthority = createAuthority("DELETE_AUTHORITY");
    }

    private AuthorityEntity createAuthority(String name){

        AuthorityEntity authority = authorityRepository.findByName(name);

        if(authority == null){

            authority = new AuthorityEntity(name);
            authorityRepository.save(authority);
        }
        return authority;
    }
}
