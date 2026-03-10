package com.appsdevelopersblog.app.ws.service.impl;

import com.appsdevelopersblog.app.ws.io.entity.UserEntity;
import com.appsdevelopersblog.app.ws.io.repository.PasswordResetTokenRepository;
import com.appsdevelopersblog.app.ws.io.repository.UserRepository;
import com.appsdevelopersblog.app.ws.shared.Utils;
import com.appsdevelopersblog.app.ws.shared.dto.AddressDto;
import com.appsdevelopersblog.app.ws.shared.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

//Use the @ExtendWith annotation and remove manual initialization entirely.
//It automatically initializes @Mock and @InjectMocks fields before each test.
// No need for @BeforeEach setUp() method at all
//@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    //We will create a mock of UserRepository. Business logic inside of it doesn't concern us currently.
    //Mock Object = Fake Class which we can instantiate.
    @Mock
    UserRepository userRepository;

    @Mock
    Utils utils;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    /*We want to access getUser() method in UserServiceImpl service class but our UserServiceImpl is a class under test.
    We cant use @Mock on this class. Also we are using so many @Autowired annotations for dependency injection into many classes-
    like UserRepository, PasswordResetTokenRepository etc.So, we also need to autowire this UserServiceImpl in this test class.
    We use @InjectMocks to inject mocks into our class which is under test. It is like we are doing mock dependency injections for-
    UserRepository, PasswordResetTokenRepository etc. Because they are annotated with @Autowired. BUT ONLY UserRepository WILL BE-
    INJECTED BECAUSE IT IS THE ONLY CLASS THAT IS ANNOTATED WITH @Mock currently!*/
    @InjectMocks
    UserServiceImpl userService;

    String userId = "fnrk4mgpodj432";

    String password = "mv379pdjhn54673";

    UserEntity userEntity;


    @BeforeEach
    void setUp() throws Exception{

        //initMocks(this) is deprecated and suitable replacement is openMocks(this). Or we can use @ExtendWith(MockitoExtension.class) above.
        MockitoAnnotations.openMocks(this);

        //findByEmail returns UserEntity object. So we need to create one for this test case. This is a dummy object.
        userEntity = new UserEntity();
        //We are going to hard code attribute values.
        userEntity.setId(1L);
        userEntity.setFirstName("Necmettin");
        userEntity.setUserId(userId);
        userEntity.setEncryptedPassword("mv379pdjhn54673");
        userEntity.setEmail("necmettingedikli611@gmail.com");
        userEntity.setEmailVerificationToken("2389slkfjdgkls320948327");
    }

    //This test is for testing getUser method from UserServiceImpl
    @Test
    final void testGetUser(){

        /*we assigned custom input value and custom output value for defined method(findByEmail btw) inside of UserRepository.
        findByEmail accepts any string, any Email as method argument. anyString() comes from mockito.
        We will force findByEmail method return UserEntity with thenReturn(userEntity).
        For every parameter that is sent, the method will return UserEntity!!!
        This is called mocking.*/
        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

        //This UserDto actually returns from our UserService class!
        UserDto userDto = userService.getUser("kraziboi@test.com");

        //We assert that userDto is not null. If our userDto is null, test will fail!
        assertNotNull(userDto);

        /*We are asserting that userEntity's firstname(which is "Necmettin") and userDto's firstname are same values. If they are not,-
        -test will fail!*/
        assertEquals("Necmettin", userDto.getFirstName());
    }


    //This test is for testing UsernameNotFoundException in getUser method in UserServiceImpl.
    @Test
    final void testGetUser_UsernameNotFoundException(){

        when(userRepository.findByEmail(anyString())).thenReturn(null);

        /*We asserted Exception with assertThrows. First parameter takes the expected exception type, and second one takes the method-
        that throws exception. If expected and thrown exceptions are same, test will pass. Method will be inside of lambda expression.
        We simply handle exceptions with assertion.*/
        assertThrows(UsernameNotFoundException.class,
                ()->{
                    userService.getUser("kraziboi@test.com");
                });
    }

    @Test
    final void testCreateUser(){

        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(utils.generateAddressId(anyInt())).thenReturn("7483757asdasdas");
        when(utils.generateUserId(anyInt())).thenReturn(userId);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(password);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        AddressDto addressDto = new AddressDto();
        addressDto.setType("shipping");
        addressDto.setCity("Istanbul");

        List<AddressDto> addresses = new ArrayList<>();
        addresses.add(addressDto);

        UserDto userDto = new UserDto();
        userDto.setAddresses(addresses);

        UserDto storedUserDetails = userService.createUser(userDto);

        assertNotNull(storedUserDetails);

        assertEquals(userEntity.getFirstName(), storedUserDetails.getFirstName());
    }
}
