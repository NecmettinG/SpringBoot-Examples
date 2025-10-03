package com.appsdevelopersblog.app.ws.ui.model.response;

//This class is created for delete request in UserController class.
public class OperationStatusModel {

    private String operationResult;
    private String operationName;

    public String getOperationResult(){

        return operationResult;
    }

    public void setOperationResult(String operationResult){

        this.operationResult = operationResult;
    }

    public String getOperationName(){

        return operationName;
    }

    public void setOperationName(String operationName){

        this.operationName = operationName;
    }
}
