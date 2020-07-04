package com.revelhealth.notificationguide.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.revelhealth.notificationguide.model.Forecast;

import org.junit.jupiter.api.Test;

public class ForecastBuilderTest {
    
    private final String SUNNY = "800";
    private final String RAINY = "500";
    private final String INVALID = "1000";

    @Test
    public void testIsSunnyReturnsCurrectAnswerForCode() {
        assertTrue(ForecastBuilder.isSunny(SUNNY));
        assertFalse(ForecastBuilder.isSunny(RAINY));
        assertFalse(ForecastBuilder.isSunny(INVALID));
    }

    @Test
    public void testIsRainingReturnsCorrectAnswerForCode() {
        assertTrue(ForecastBuilder.isRaining(RAINY));
        assertFalse(ForecastBuilder.isRaining(SUNNY));
        assertFalse(ForecastBuilder.isRaining(INVALID));
    }

    @Test
    public void testSendTextMessageWhenSunnyAndAbove75() {
        Forecast forecast = new Forecast("SATURDAY", 80, SUNNY);
        ForecastBuilder.determineContactMethod(forecast);

        assertEquals("TEXT", forecast.getContactMethodAsString());
    }

    @Test
    public void testPhoneCallWhenBelow55AndSunny() {
        Forecast forecast = new Forecast("SATURDAY", 45, SUNNY);
        ForecastBuilder.determineContactMethod(forecast);

        assertEquals("PHONE", forecast.getContactMethodAsString());
    }

    @Test
    public void testPhoneCallWhenWarmAndRainy() {
        Forecast forecast = new Forecast("SATURDAY", 65, RAINY);
        ForecastBuilder.determineContactMethod(forecast);

        assertEquals("PHONE", forecast.getContactMethodAsString());
    }
}