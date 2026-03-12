package com.appsdevelopersblog.app.ws.ui.controller;

import com.appsdevelopersblog.app.ws.io.entity.UserEntity;
import com.appsdevelopersblog.app.ws.service.impl.UserServiceImpl;
import com.appsdevelopersblog.app.ws.shared.dto.AddressDto;
import com.appsdevelopersblog.app.ws.shared.dto.UserDto;
import com.appsdevelopersblog.app.ws.ui.model.response.UserRest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

//This class is for testing our UserController class. We will use all the same information from UserServiceImplTest.
public class UserControllerTest {

    @Mock
    UserServiceImpl userService;

    @InjectMocks
    UserController userController;

    UserDto userDto;

    final String userId = "fnrk4mgpodj432";

    @BeforeEach
    void setUp() throws Exception{

        MockitoAnnotations.openMocks(this);


        userDto = new UserDto();

        userDto.setFirstName("Necmettin");
        userDto.setLastName("Gedikli");
        userDto.setUserId(userId);
        userDto.setEmail("necmettingedikli611@gmail.com");
        userDto.setAddresses(getAddressesDto());
    }

    @Test
    final void testGetUser(){

        when(userService.getUserByUserId(anyString())).thenReturn(userDto);

        UserRest userRest = userController.getUser(userId);

        assertNotNull(userRest);

        assertEquals(userDto.getFirstName(), userRest.getFirstName());
        assertEquals(userDto.getLastName(), userRest.getLastName());
        assertEquals(userId, userRest.getUserId());
        //We also check the size of addresses in both instances.
        assertTrue(userDto.getAddresses().size() == userRest.getAddresses().size());

    }

    private List<AddressDto> getAddressesDto(){

        AddressDto shippingAddressDto = new AddressDto();
        shippingAddressDto.setType("shipping");
        shippingAddressDto.setCity("Istanbul");
        shippingAddressDto.setCountry("Turkey");
        shippingAddressDto.setPostalCode("12345");
        shippingAddressDto.setStreetName("1st street");

        AddressDto billingAddressDto = new AddressDto();
        billingAddressDto.setType("billing");
        billingAddressDto.setCity("Istanbul");
        billingAddressDto.setCountry("Turkey");
        billingAddressDto.setPostalCode("12345");
        billingAddressDto.setStreetName("1st street");

        List<AddressDto> addresses = new ArrayList<>();
        addresses.add(shippingAddressDto);
        addresses.add(billingAddressDto);

        return addresses;
    }
}
