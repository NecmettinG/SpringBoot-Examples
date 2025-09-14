package com.appsdevelopersblog.app.ws.service.impl;

import com.appsdevelopersblog.app.ws.io.entity.UserEntity;
import com.appsdevelopersblog.app.ws.io.repository.UserRepository;
import com.appsdevelopersblog.app.ws.service.UserService;
import com.appsdevelopersblog.app.ws.shared.Utils;
import com.appsdevelopersblog.app.ws.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto user){

        if(userRepository.findByEmail(user.getEmail()) != null){

            throw new IllegalStateException("Email already exists");
        }

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        String publicUserId = utils.generateUserId(30);
        //"0ghuZ7cuevzzCy4AbR3nIsMLXNx1Pc" is an example public userId.

        userEntity.setUserId(publicUserId);

        //We are going to use Spring Security to encrypt the password.
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        UserEntity storedUserDetails = userRepository.save(userEntity); //This save method also returns Entity object btw.

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedUserDetails, returnValue);

        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByEmail(username);

        if(userEntity == null){

            throw new UsernameNotFoundException("email not found.");
        }

        return new User(username, userEntity.getEncryptedPassword(), new ArrayList<>());
        //We are supposed to return a User instance in loadUserByUsername function. User implements UserDetails from spring.
        //First parameter will be username(email), second is encrypted password that will be decoded automatically.
        //and the third is a list of granted authorities, which are users roles and permissions.
        //We are going to check if these parameters are matching with the user data from login screen.

        //When we send a http request for login with username and password data,
        // Spring will invoke this method to locate user details from database.
    }
}
