package com.appsdevelopersblog.app.ws.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.appsdevelopersblog.app.ws.ui.model.response.ErrorMessage;

//This class is created for handling exception in our RESTful web services application.
//@ControllerAdvice annotation is needed to handle exceptions.
@ControllerAdvice
public class AppExceptionsHandler {

    //We are going to handle our custom, specific user service exception in this function. It needs to return a generic response entity.
    //This method will accept two arguments. First one is the exception that we need to handle. That exception is our user service exception.
    //Second argument is WebRequest data type. It gives us access to Http requests and we access cookies and headers and request parameters, etc.
    //We need to annotate this method with a special annotation that is configured to handle our UserServiceException. Annotation is @ExceptionHandler.
    //value will take the class of our custom exception which is UserServiceException. It can take more than one exception by the way.
    @ExceptionHandler(value = {UserServiceException.class})
    public ResponseEntity<Object> handleUserServiceException(UserServiceException ex, WebRequest request)
    {
        //getMessage() is a method of RuntimeException class for getting error message. UserServiceException extends RuntimeException.
        //And this error message is passed to this exception in UserController class, in createUser function.
        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());

        //ResponseEntity takes a few parameters. First is the error message class. Second one is HttpHeaders, which is an empty object.
        //Third one is HttpStatus. We can select one of the Http status codes that we want to return with this response.
        // INTERNAL_SERVER_ERROR is Http status 500. ErrorMessage object will be converted to Json or Xml automatically.-
        // -it includes error message and timestamp.
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    //This method is for handling other types of exceptions. All other exceptions except UserServiceException will be handled here!!!
    //Exception is a generic class for exceptions and I guess all other exceptions inherit from this class.
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest request)
    {
        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());

        //The response format of the exception will be same with handleUserServiceException.
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}