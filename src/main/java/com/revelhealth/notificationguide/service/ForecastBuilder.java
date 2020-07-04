package com.revelhealth.notificationguide.service;

import java.time.LocalDateTime;

import com.revelhealth.notificationguide.model.Forecast;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ForecastBuilder {

    protected static final List<String> sunnyCodes = Collections.unmodifiableList(Arrays.asList("800", "801", "802"));
    protected static final List<String> rainyCodes = Collections.unmodifiableList(Arrays.asList("500", "501", "502", "503", "504", "511", "520", "521","522"));

    public static Forecast build(JSONObject rawForecast) {
        String dayOfWeek = getDayOfWeekFromJSON(rawForecast);
        double temp = getTemperatureFromJSON(rawForecast);
        String conditionCode = getConditionCodeFromJSON(rawForecast);
        Forecast forecast = new Forecast(dayOfWeek, temp, conditionCode);
        determineContactMethod(forecast);
        return forecast;
    }

    public static void determineContactMethod(Forecast forecast) {
        String conditionCode = forecast.getConditionCode();
        double temperature = forecast.getTemperature();

        if(isRaining(conditionCode)){
            forecast.setContactMethod(Forecast.ContactMethod.PHONE);
        } else if(temperature > 75 && isSunny(conditionCode)){
            forecast.setContactMethod(Forecast.ContactMethod.TEXT);
        } else if (temperature <= 75 && forecast.getTemperature() >= 55) {
            forecast.setContactMethod(Forecast.ContactMethod.EMAIL);
        } else if (temperature < 55) {
            forecast.setContactMethod(Forecast.ContactMethod.PHONE);
        }
    }

    protected static boolean isSunny(String conditionCode) {
        return sunnyCodes.contains(conditionCode);
    }

    protected static boolean isRaining(String conditionCode) {
        return rainyCodes.contains(conditionCode);
    }

    protected static String getDayOfWeekFromJSON(JSONObject rawForecast) {
        String dateText = rawForecast.getString("dt_txt").replace(' ', 'T');
        return LocalDateTime.parse(dateText).getDayOfWeek().toString();
    }

    protected static double getTemperatureFromJSON(JSONObject rawForecast) {
        return rawForecast.getJSONObject("main").getDouble("temp");
    }

    protected static String getConditionCodeFromJSON(JSONObject rawForecast) {
        JSONObject weather = rawForecast.getJSONArray("weather").getJSONObject(0);
        String conditionCode = weather.get("id").toString();
        return conditionCode;
    }
}