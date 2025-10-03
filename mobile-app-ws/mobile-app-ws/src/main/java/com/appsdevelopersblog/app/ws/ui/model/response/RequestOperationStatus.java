package com.appsdevelopersblog.app.ws.ui.model.response;

//We created this enum for different operations like delete, create, update etc. We are going to use this in delete request in UserController.
//We are going to use these enums for "operationResult" attribute in OperationStatusModel class.
public enum RequestOperationStatus {

    ERROR, SUCCESS
}
