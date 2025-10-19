package com.appsdevelopersblog.app.ws.service;

import com.appsdevelopersblog.app.ws.shared.dto.AddressDto;

import java.util.List;

public interface AddressService{

    public List<AddressDto> getAddresses(String userId);
    public AddressDto getAddress(String addressId);
}
