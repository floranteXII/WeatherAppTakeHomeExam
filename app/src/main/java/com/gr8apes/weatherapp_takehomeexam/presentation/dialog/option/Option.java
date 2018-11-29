package com.gr8apes.weatherapp_takehomeexam.presentation.dialog.option;

import java.io.Serializable;

/**
 * Created by LanarD on 06/10/2018.
 */
public class Option implements Serializable {

    String optionName;
    int optionId;

    public Option(String optionName, int optionId) {
        this.optionName = optionName;
        this.optionId = optionId;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }
}
