package com.appsdevelopersblog.app.ws.ui.model.response;

import org.springframework.hateoas.RepresentationModel;

//RepresentationModel is for HATEOAS usage. The example json output for getUserAddress from UserController will be:
//{
//    "addressId": "fOyRuQJVPP9ZUSI8WsAxIBcAPBobUW",
//    "city": "Istanbul",
//    "country": "Turkey",
//    "streetName": "Aydinlar Streeet",
//    "postalCode": "34569",
//    "type": "SHIPPING",
//    "_links": {
//        "user": {
//            "href": "http://localhost:8080/mobile-app-ws/users/eY1yH2FN0c4rYMZLCvPQPyaaimoJfS"
//        },
//        "addresses": {
//            "href": "http://localhost:8080/mobile-app-ws/users/eY1yH2FN0c4rYMZLCvPQPyaaimoJfS/addresses"
//        },
//        "self": {
//            "href": "http://localhost:8080/mobile-app-ws/users/eY1yH2FN0c4rYMZLCvPQPyaaimoJfS/addresses/fOyRuQJVPP9ZUSI8WsAxIBcAPBobUW"
//        }
//    }
//}
public class AddressesRest extends RepresentationModel<AddressesRest> {//RepresentationModel is needed to use .add() method.

    private String addressId;
    private String city;
    private String country;
    private String streetName;
    private String postalCode;
    private String type;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }
}
