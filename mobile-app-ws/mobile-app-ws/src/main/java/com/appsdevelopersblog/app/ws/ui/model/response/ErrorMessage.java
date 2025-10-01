package com.appsdevelopersblog.app.ws.ui.model.response;

import java.util.Date;

//This class is responsible for custom error message representation. We will only return error message and timestamp with using this class.
//This class is a simple, plain Java class and will be used as error message. The object of this class will be converted into Json or Xml in-
//-AppExceptionsHandler.
public class ErrorMessage {
    private Date timestamp;
    private String message;

    //No arg constructor.
    public ErrorMessage() {}

    public ErrorMessage(Date timestamp, String message)
    {
        this.timestamp = timestamp;
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}