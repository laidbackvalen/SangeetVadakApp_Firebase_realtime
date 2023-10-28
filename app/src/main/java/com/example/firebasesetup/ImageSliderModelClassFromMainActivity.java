package com.example.firebasesetup;

import java.io.Serializable;
//VIEW PAGER MODEL CLASS
public class ImageSliderModelClassFromMainActivity implements Serializable {
    //Here you can use string variable to store url
    //if you want to load image from the internet
    int image;

    public ImageSliderModelClassFromMainActivity(int image) {
        this.image = image;
    }

    public int getImage() {
        return image;
    }
}
