package com.revelhealth.notificationguide.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Forecast {
    private String name;
    private double temperature;
    private String conditionCode;
    private ContactMethod contactMethod = ContactMethod.NONE;

    static final List<String> sunnyCodes = Collections.unmodifiableList(Arrays.asList("800", "801", "802"));
    static final List<String> cloudyCodes = Collections.unmodifiableList(Arrays.asList("803", "804")); 
    static final List<String> rainyCodes = Collections.unmodifiableList(Arrays.asList("500", "501", "502", "503", "504", "511", "520", "521","522"));
    
    public enum ContactMethod {
        EMAIL, PHONE, TEXT, NONE
    }

    public Forecast(String name, double temperature, String conditionCode) {
        this.name = name;
        this.temperature = temperature;
        this.conditionCode = conditionCode;
    }

    public String getName() {
        return this.name;
    }

    public double getTemperature() {
        return this.temperature;
    }

    public String getConditionCode() {
        return this.conditionCode;
    }

    public String getContactMethodAsString() {
        return this.contactMethod.toString();
    }

    public void setContactMethod(ContactMethod contactMethod) {
        this.contactMethod = contactMethod;
    }

    @Override 
    public String toString() {
        return this.name + " " + this.contactMethod;
    }
}
