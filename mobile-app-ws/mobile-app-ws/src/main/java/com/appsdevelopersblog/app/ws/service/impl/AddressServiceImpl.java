package com.appsdevelopersblog.app.ws.service.impl;

import com.appsdevelopersblog.app.ws.io.entity.AddressEntity;
import com.appsdevelopersblog.app.ws.io.entity.UserEntity;
import com.appsdevelopersblog.app.ws.io.repository.AddressRepository;
import com.appsdevelopersblog.app.ws.io.repository.UserRepository;
import com.appsdevelopersblog.app.ws.service.AddressService;
import com.appsdevelopersblog.app.ws.shared.dto.AddressDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Override
    public List<AddressDto> getAddresses(String userId){

        List<AddressDto> returnValue = new ArrayList<>();

        UserEntity userEntity = userRepository.findByUserId(userId);

        if(userEntity == null) return returnValue;

        //Iterable datatype is some kind of list that is used in enhanced for. You can see the implementation of enhanced for below.
        Iterable<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);

        ModelMapper modelMapper = new ModelMapper();

        for(AddressEntity addressEntity : addresses){
            //We are going to map each AddressEntity object that is in Iterable list to AddressDto object and store them into List of-
            //-AddressDto objects which is returnValue.
            returnValue.add(modelMapper.map(addressEntity, AddressDto.class));
        }

        return returnValue;
    }

    @Override
    public AddressDto getAddress(String addressId){

        //We initialized returnValue as null. You can initialize your values like this as well.
        AddressDto returnValue = null;

        AddressEntity addressEntity = addressRepository.findByAddressId(addressId);

        if(addressEntity != null){

            returnValue = new ModelMapper().map(addressEntity, AddressDto.class);
        }

        return returnValue;
    }
}
