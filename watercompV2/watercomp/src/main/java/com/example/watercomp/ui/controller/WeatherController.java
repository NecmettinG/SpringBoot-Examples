package com.example.watercomp.ui.controller;

import com.example.watercomp.service.WeatherService;
import com.example.watercomp.shared.dto.WeatherForecastResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @Autowired
    private WeatherService weatherService;

    /**
     * Test endpoint to verify AccuWeather API configuration
     */
    @GetMapping("/test")
    public ResponseEntity<String> testApiConnection() {
        return ResponseEntity.ok(weatherService.testApiConnection());
    }

    /**
     * Konya için 1 günlük hava durumu tahmini (Ücretsiz API)
     */
    @GetMapping("/konya/1day")
    public ResponseEntity<String> get1DayForecast() {
        try {
            String forecast = weatherService.get1DayForecastRaw();
            logger.info("Controller received raw forecast length: {}", forecast != null ? forecast.length() : "null");
            if (forecast == null || forecast.isEmpty()) {
                logger.warn("Forecast returned null or empty");
                return ResponseEntity.status(204).body("No data returned from AccuWeather API");
            }
            return ResponseEntity.ok(forecast);
        } catch (Exception e) {
            logger.error("Error in get1DayForecast controller: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    /**
     * Konya için 5 günlük hava durumu tahmini (Ücretsiz API)
     */
    @GetMapping("/konya/5day")
    public ResponseEntity<WeatherForecastResponseDTO> get5DayForecast() {
        try {
            WeatherForecastResponseDTO forecast = weatherService.get5DayForecast();
            return ResponseEntity.ok(forecast);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Konya için 10 günlük hava durumu tahmini (Ücretli API)
     */
    @GetMapping("/konya/10day")
    public ResponseEntity<WeatherForecastResponseDTO> get10DayForecast() {
        try {
            WeatherForecastResponseDTO forecast = weatherService.get10DayForecast();
            return ResponseEntity.ok(forecast);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Konya için 15 günlük hava durumu tahmini (Ücretli API)
     */
    @GetMapping("/konya/15day")
    public ResponseEntity<WeatherForecastResponseDTO> get15DayForecast() {
        try {
            WeatherForecastResponseDTO forecast = weatherService.get15DayForecast();
            return ResponseEntity.ok(forecast);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Konya için 30 günlük hava durumu tahmini
     * NOT: AccuWeather API'si 30 günlük tahmin desteklememektedir.
     * Bu endpoint en fazla 15 günlük tahmin döndürür (AccuWeather maksimumu).
     * 15 günlük tahmin için ücretli plan (Standard veya Premium) gereklidir.
     */
    @GetMapping("/konya/30day")
    public ResponseEntity<WeatherForecastResponseDTO> get30DayForecast() {
        try {
            WeatherForecastResponseDTO forecast = weatherService.get30DayForecast();
            if (forecast == null || forecast.getDailyForecasts() == null || forecast.getDailyForecasts().isEmpty()) {
                logger.warn("30-day forecast returned empty. AccuWeather may require a paid plan for 15-day forecasts.");
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(forecast);
        } catch (Exception e) {
            logger.error("Error in get30DayForecast controller: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Belirli bir lokasyon için hava durumu tahmini
     * @param locationKey AccuWeather location key
     * @param days Gün sayısı (1, 5, 10, 15, 30)
     */
    @GetMapping("/forecast")
    public ResponseEntity<WeatherForecastResponseDTO> getForecastByLocation(
            @RequestParam(defaultValue = "316940") String locationKey,
            @RequestParam(defaultValue = "5") int days) {
        try {
            WeatherForecastResponseDTO forecast = weatherService.getForecastByLocation(locationKey, days);
            return ResponseEntity.ok(forecast);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}

