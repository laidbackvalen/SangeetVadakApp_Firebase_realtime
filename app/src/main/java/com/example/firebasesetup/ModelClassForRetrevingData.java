package com.example.firebasesetup;

import java.io.Serializable;

public class ModelClassForRetrevingData implements Serializable {
    String name;
    String countrycode;
    String phone;
    String email;

    String key;
public ModelClassForRetrevingData(){

}
    public ModelClassForRetrevingData(String name, String countrycode, String phone, String email, String key) {
        this.name = name;
        this.countrycode = countrycode;
        this.phone = phone;
        this.email = email;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }


    public String getKey() {
        return key;
    }
}
