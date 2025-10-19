package com.appsdevelopersblog.app.ws.service.impl;

import com.appsdevelopersblog.app.ws.exceptions.UserServiceException;
import com.appsdevelopersblog.app.ws.io.entity.UserEntity;
import com.appsdevelopersblog.app.ws.io.repository.UserRepository;
import com.appsdevelopersblog.app.ws.service.UserService;
import com.appsdevelopersblog.app.ws.shared.Utils;
import com.appsdevelopersblog.app.ws.shared.dto.AddressDto;
import com.appsdevelopersblog.app.ws.shared.dto.UserDto;
import com.appsdevelopersblog.app.ws.ui.model.response.ErrorMessages;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

        //We get the AddressDto list from user and assigned the user and generated public address id to each AddressDto object.
        for(int i = 0; i < user.getAddresses().size(); i++){

            AddressDto addressDto = user.getAddresses().get(i);
            addressDto.setUserDetails(user);
            addressDto.setAddressId(utils.generateAddressId(30));
            user.getAddresses().set(i, addressDto);
        }

        //UserEntity userEntity = new UserEntity();
        //BeanUtils.copyProperties(user, userEntity);

        ModelMapper modelMapper = new ModelMapper();
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);

        String publicUserId = utils.generateUserId(30);
        //"0ghuZ7cuevzzCy4AbR3nIsMLXNx1Pc" is an example public userId.

        userEntity.setUserId(publicUserId);

        //We are going to use Spring Security to encrypt the password.
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        UserEntity storedUserDetails = userRepository.save(userEntity); //This save method also returns Entity object btw.

        //UserDto returnValue = new UserDto();
        //BeanUtils.copyProperties(storedUserDetails, returnValue);
        UserDto returnValue = modelMapper.map(storedUserDetails, UserDto.class);

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

    //We are going to return a list of users but we won't return the whole users list. page is the page we created and limit is the-
    //- quantity of users that will be returned in a list. It is like "I CAST THE RETURN OF 50 USERS FOR PAGE 0!!"
    //Imagine you have two record in database. If you enter "?page=0&limit=1" in query string, You get the first record for page 0.
    //Then, if you type "?page=1&limit=1", You get the second record for page 1. This is called pagination!!!
    @Override
    public List<UserDto> getUsers(int page, int limit){

        List<UserDto> returnValue = new ArrayList<>();

        //The page number normally starts from 0 but we manipulated this. If we assign value 1 to page, we will get first page.
        if(page>0){
            page -= 1;
        }

        //We created Pageable object to use it in findAll() jpa method.
        Pageable pageableRequest = PageRequest.of(page, limit);//PageRequest.of(page, size) method takes page and size parameters.

        //This findAll(Pageable) method also return Page object. DON'T FORGET IT!!
        Page<UserEntity> usersPage = userRepository.findAll(pageableRequest); //This findAll() jpa method is special. It takes Pageable object as parameter for pagination.

        List<UserEntity> users = usersPage.getContent(); //We can directly get users from Page object and put to List with .getContent() method.

        //Similar enhanced loop structure from getUsers that is in UserController.
        for(UserEntity userEntity : users){

            UserDto userDto = new UserDto();

            BeanUtils.copyProperties(userEntity, userDto);

            returnValue.add(userDto);
        }

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
    public void deleteUser(String userId){

        UserEntity userEntity = userRepository.findByUserId(userId);

        if(userEntity == null){
            //Teacher decided to use our UserServiceException with custom error message from ErrorMessages enum but We could use-
            //-UsernameNotFoundException, that comes from Spring, as well.
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }

        //We delete that particular user from database. .delete() method takes UserEntity as parameter.
        userRepository.delete(userEntity);
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
