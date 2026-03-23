package com.appsdevelopersblog.app.ws.io.repository;

import com.appsdevelopersblog.app.ws.io.entity.AddressEntity;
import com.appsdevelopersblog.app.ws.io.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


//This class is an integration test and it will communicate with the database. It will save data, fetch data etc.
//We have to use @ExtendWith(SpringExtension.class) because we need to bring up the environment and make spring context available. So we can-
//autowire beans and we can use them to work with property files or a database.
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() throws Exception {

        // Ensure clean state for each test method.
        userRepository.deleteAll();

        //We are going to create a user here beforehand to test our methods.
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("Necmettin");
        userEntity.setLastName("Gedikli");
        userEntity.setUserId("219010");
        userEntity.setEncryptedPassword("hire-me-bro");
        userEntity.setEmail("necmettingedikli611@gmail.com");
        userEntity.setEmailVerificationStatus(true);

        //We are going to create address for user here beforehand to test our methods.
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setType("BILLING");
        addressEntity.setAddressId("347548");
        addressEntity.setCity("Istanbul");
        addressEntity.setCountry("Turkey");
        addressEntity.setPostalCode("45275");
        addressEntity.setStreetName("Aydinlar Street");

        List<AddressEntity> addresses = new ArrayList<>();
        addresses.add(addressEntity);
        userEntity.setAddresses(addresses);

        userRepository.save(userEntity);

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setFirstName("Necmettin");
        userEntity2.setLastName("Gedikli");
        userEntity2.setUserId("219010111sjgfhkjdsgfh1");
        userEntity2.setEncryptedPassword("hire-me-bro");
        userEntity2.setEmail("necmettingedikli611+2@gmail.com");
        userEntity2.setEmailVerificationStatus(true);

        //We are going to create address for user here beforehand to test our methods.
        AddressEntity addressEntity2 = new AddressEntity();
        addressEntity2.setType("SHIPPING");
        addressEntity2.setAddressId("347548676767ghhgh");
        addressEntity2.setCity("Istanbul");
        addressEntity2.setCountry("Turkey");
        addressEntity2.setPostalCode("45275");
        addressEntity2.setStreetName("Aydinlar Street");

        List<AddressEntity> addresses2 = new ArrayList<>();
        addresses2.add(addressEntity2);
        userEntity2.setAddresses(addresses2);

        userRepository.save(userEntity2);
    }

    @Test
    final void testGetVerifiedUsers(){

        //first parameter is the start number for pages and second parameter is size for each page.
        Pageable pageableRequest = PageRequest.of(1, 1);

        Page<UserEntity> page = userRepository.findAllUsersWithConfirmedEmailAddress(pageableRequest);

        assertNotNull(page);

        List<UserEntity> userEntities = page.getContent();
        assertNotNull(userEntities);
        assertTrue(userEntities.size() == 1);

    }
}
