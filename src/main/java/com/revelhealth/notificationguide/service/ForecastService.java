package com.revelhealth.notificationguide.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.json.JSONArray;
import org.json.JSONObject;

import com.revelhealth.notificationguide.model.Forecast;

public class ForecastService {

    static final int AFTERNOON = 15;
   
    public String getJSONTextFromFile(String fileName) {

        String jsonText = "";

        try {
            Stream<String> stream = Files.lines(Paths.get(fileName));
            jsonText = stream.map(s -> s.toString()).collect(Collectors.joining(","));
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonText;
    }

    public List<Forecast> getListOfForecasts(String fileName) {
        String forecastJSON = getJSONTextFromFile(fileName);
        JSONArray rawForecasts = new JSONObject(forecastJSON).getJSONArray("list");
        List<Forecast> forecasts = new ArrayList<Forecast>();
        for(int i=0; i < rawForecasts.length(); i++) {
            JSONObject rawForecast = rawForecasts.getJSONObject(i);
            if(isAfternoonForecast(rawForecast)){
                Forecast forecast = ForecastBuilder.build(rawForecast);
                forecasts.add(forecast);
            }   
        }

        return forecasts;
    }

    protected boolean isAfternoonForecast(JSONObject rawForecast) {
        String dateText = rawForecast.getString("dt_txt").replace(' ', 'T');
        return LocalDateTime.parse(dateText).getHour() == AFTERNOON;
    }
}