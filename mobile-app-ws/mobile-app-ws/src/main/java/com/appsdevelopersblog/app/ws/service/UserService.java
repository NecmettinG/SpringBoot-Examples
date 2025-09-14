package com.appsdevelopersblog.app.ws.service;

import com.appsdevelopersblog.app.ws.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    public UserDto createUser(UserDto user);
}
