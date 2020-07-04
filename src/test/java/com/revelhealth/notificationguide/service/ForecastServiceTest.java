package com.revelhealth.notificationguide.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.List;

import com.revelhealth.notificationguide.model.Forecast;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ForecastServiceTest {

    private ForecastService service;
    private final String currentDirectory = System.getProperty("user.dir");

    @BeforeEach
    public void setup() {
        service = new ForecastService();
    }

    @Test
    public void testGetJSONTextFromFile() {
        String expectedString = "{\"clouds\": { \"all\": 20}}";
        String jsonString = service.getJSONTextFromFile(currentDirectory + "/src/test/clouds.json");

        assertEquals(expectedString, jsonString);
    }

    @Test
    public void testGetListOfForecastsReturnsFiveDays() {
        List<Forecast> forecasts = service.getListOfForecasts(currentDirectory + "/src/test/5dayforecast.json");

        assertEquals(5, forecasts.size());
    }

    @Test
    public void testIsAfternoonForecast() throws JSONException {
        String dateText = "2020-07-07 15:00:00";
        
        JSONObject rawForecast = mock(JSONObject.class);
        when(rawForecast.getString("dt_txt")).thenReturn(dateText);
        
        assertTrue(service.isAfternoonForecast(rawForecast));
    }

    @Test
    public void testIsNotAfternoonForecast() throws JSONException {
        String dateText = "2020-07-07 18:00:00";

        JSONObject rawForecast = mock(JSONObject.class);
        when(rawForecast.getString("dt_txt")).thenReturn(dateText);
        
        assertFalse(service.isAfternoonForecast(rawForecast));
    }
}