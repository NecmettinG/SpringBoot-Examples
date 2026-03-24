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

import static org.junit.jupiter.api.Assertions.*;


//This class is an integration test and it will communicate with the database. It will save data, fetch data etc.
//We have to use @ExtendWith(SpringExtension.class) because we need to bring up the environment and make spring context available. So we can-
//autowire beans and we can use them to work with property files or a database.
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    static boolean recordsCreated = false;

    @BeforeEach
    void setUp() throws Exception {

        if(!recordsCreated) {
            createRecords();
        }
    }

    @Test
    final void testGetVerifiedUsers(){

        //first parameter is the start number for pages and second parameter is size for each page.
        Pageable pageableRequest1 = PageRequest.of(0, 1);

        Page<UserEntity> page1 = userRepository.findAllUsersWithConfirmedEmailAddress(pageableRequest1);

        //instead of changing the page number in each test execution, I created a new Pageable for checking the second page.
        Pageable pageableRequest2 = PageRequest.of(1, 1);

        Page<UserEntity> page2 = userRepository.findAllUsersWithConfirmedEmailAddress(pageableRequest1);

        assertNotNull(page1);
        assertNotNull(page2);

        List<UserEntity> userEntities1 = page1.getContent();
        List<UserEntity> userEntities2 = page1.getContent();

        assertNotNull(userEntities1);
        assertNotNull(userEntities2);

        assertTrue(userEntities1.size() == 1);
        assertTrue(userEntities2.size() == 1);

    }

    @Test
    final void testFindUserByFirstName(){

        String firstName = "Necmettin";

        List<UserEntity> users = userRepository.findUserByFirstName(firstName);

        assertNotNull(users);

        assertTrue(users.size() == 2);

        UserEntity user = users.get(0);

        assertTrue(user.getFirstName().equals(firstName));
    }

    @Test
    final void testFindUserByLastName(){

        String lastName = "Gedikli";

        List<UserEntity> users = userRepository.findUserByLastName(lastName);

        assertNotNull(users);

        assertTrue(users.size() == 2);

        UserEntity user = users.get(0);

        assertTrue(user.getLastName().equals(lastName));
    }

    @Test
    final void testFindUsersByKeyword(){

        String keyword = "dikli";

        List<UserEntity> users = userRepository.findUserByKeyword(keyword);

        assertNotNull(users);

        assertTrue(users.size() == 2);

        UserEntity user = users.get(0);

        //contains check if the string contains certain substring.
        assertTrue(user.getLastName().contains(keyword) || user.getFirstName().contains(keyword));
    }

    @Test
    final void testFindUserByFirstNameAndLastNameByKeyword(){

        String keyword = "Necm";

        List<Object[]> users = userRepository.findUserByFirstNameAndLastNameByKeyword(keyword);

        assertNotNull(users);

        assertTrue(users.size() == 2);

        Object[] user = users.get(0);

        //arrays use .length, List uses .size(). Memorize them :)
        assertTrue(user.length == 2);

        //index 0 will contain firstname and index 1 will contain lastname because we wrote "select u.first_name, u.last_name from Users u..."
        String userFirstName = String.valueOf(user[0]);
        String userLastName = String.valueOf(user[1]);

        assertNotNull(userFirstName);
        assertNotNull(userLastName);

        System.out.println("First Name = " + userFirstName+"\nLast Name = " + userLastName);

        assertTrue(userFirstName.contains(keyword) || userLastName.contains(keyword));
    }

    private void createRecords(){

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
        //The reason we must register another email is becuase we annotated email attribute as unique in UserEntity class!
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

        recordsCreated = true;
    }
}
