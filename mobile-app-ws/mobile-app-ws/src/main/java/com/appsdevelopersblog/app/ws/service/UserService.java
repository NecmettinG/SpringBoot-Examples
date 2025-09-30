package com.appsdevelopersblog.app.ws.service;

import com.appsdevelopersblog.app.ws.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {//UserDetailsService comes from spring and it is used for authentication.

    public UserDto createUser(UserDto user);
    public UserDto getUser(String email); //We created this method to get user details from database with using email information.
    public UserDto getUserByUserId(String userId);
}
