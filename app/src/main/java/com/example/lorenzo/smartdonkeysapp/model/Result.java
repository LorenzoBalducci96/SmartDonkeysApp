package com.example.lorenzo.smartdonkeysapp.model;

import java.io.Serializable;

public class Result implements Serializable {
    String result;
    //other useful data....

    public String getResult() {
        return result;
    }

    public Result(String result) {

        this.result = result;
    }
}
