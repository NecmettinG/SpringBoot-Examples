package com.appsdevelopersblog.app.ws.service.impl;

import com.appsdevelopersblog.app.ws.exceptions.UserServiceException;
import com.appsdevelopersblog.app.ws.io.entity.UserEntity;
import com.appsdevelopersblog.app.ws.io.repository.UserRepository;
import com.appsdevelopersblog.app.ws.service.UserService;
import com.appsdevelopersblog.app.ws.shared.Utils;
import com.appsdevelopersblog.app.ws.shared.dto.UserDto;
import com.appsdevelopersblog.app.ws.ui.model.response.ErrorMessages;
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

    //We are going to get particular user with particular email. We are also going to use this method to get userId in json response header.
    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);

        if(userEntity == null){
            throw new UsernameNotFoundException("email not found:" + email);
        }

        UserDto returnValue = new UserDto();

        BeanUtils.copyProperties(userEntity, returnValue);

        return returnValue;
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if(userEntity == null){
            throw new UsernameNotFoundException("User not found:" + userId);
        }

        UserDto returnValue = new UserDto();

        BeanUtils.copyProperties(userEntity, returnValue);

        return returnValue;
    }

    //We could use @Transactional here. It would be enough to change the values of attributes via setters and directly save them to database.
    //There is no need to use .save() method if you annotate this method with @Transactional.
    @Override
    public UserDto updateUser(String userId, UserDto user){

        UserEntity userEntity = userRepository.findByUserId(userId);

        if(userEntity == null){
            //Teacher decided to use our UserServiceException with custom error message from ErrorMessages enum but We could use-
            //-UsernameNotFoundException, that comes from Spring, as well.
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }

        UserDto returnValue = new UserDto();

        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        //We can also update our password and encrypt that password again. Teacher didn't write this but I decided to add this.
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        UserEntity updatedUserDetails = userRepository.save(userEntity);

        BeanUtils.copyProperties(updatedUserDetails, returnValue);

        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //username represents email!!!

        UserEntity userEntity = userRepository.findByEmail(username);

        if(userEntity == null){

            throw new UsernameNotFoundException("email not found:" + username);
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
