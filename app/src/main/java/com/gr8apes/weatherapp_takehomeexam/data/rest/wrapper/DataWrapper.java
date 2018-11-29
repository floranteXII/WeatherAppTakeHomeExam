package com.gr8apes.weatherapp_takehomeexam.data.rest.wrapper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataWrapper<T> implements Serializable {

    @SerializedName("data")
    @Expose
    public T data;

}
