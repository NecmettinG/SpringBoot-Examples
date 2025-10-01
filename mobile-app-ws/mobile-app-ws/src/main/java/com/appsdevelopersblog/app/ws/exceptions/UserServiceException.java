package com.appsdevelopersblog.app.ws.exceptions;

//We created custom User service exception class for using our custom exception in User related classes (UserController).
//We will make this class to extend RuntimeException, because this is an exception that we are creating.
public class UserServiceException extends RuntimeException{

    private static final long serialVersionUID = 1348771109171435607L;

    public UserServiceException(String message)
    {
        super(message);
    }
}