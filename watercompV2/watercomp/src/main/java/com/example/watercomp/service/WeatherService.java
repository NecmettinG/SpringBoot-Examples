package com.example.watercomp.service;

import com.example.watercomp.shared.dto.WeatherForecastResponseDTO;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    @Value("${accuweather.api.key}")
    private String apiKey;

    // Konya, Turkey - AccuWeather Location Key: 316940
    private static final String KONYA_LOCATION_KEY = "316940";

    private static final String ACCUWEATHER_BASE_URL = "https://dataservice.accuweather.com";

    private final RestTemplate restTemplate;

    public WeatherService() {
        this.restTemplate = new RestTemplate();
    }

    public String testApiConnection() {
        StringBuilder result = new StringBuilder();
        result.append("=== AccuWeather API Diagnostic ===\n\n");

        // Check API key
        result.append("1. API Key Check:\n");
        if (apiKey == null || apiKey.isEmpty()) {
            result.append("   ❌ API key is NULL or EMPTY\n");
            result.append("   Please set 'accuweather.api.key' in application.properties\n\n");
            return result.toString();
        }

        result.append("   API Key (masked): ").append(apiKey.substring(0, Math.min(10, apiKey.length()))).append("***\n");

        if (apiKey.startsWith("zpka_")) {
            result.append("   ⚠️ WARNING: Key has 'zpka_' prefix - this is NOT a valid AccuWeather format!\n");
            result.append("   AccuWeather keys are typically 32-character alphanumeric strings.\n");
            result.append("   Get a valid key from: https://developer.accuweather.com\n\n");
        } else {
            result.append("   ✅ API key format looks correct\n\n");
        }

        // Test API call
        result.append("2. API Connection Test:\n");
        String testUrl = String.format("%s/forecasts/v1/daily/1day/%s?apikey=%s&metric=true",
                ACCUWEATHER_BASE_URL, KONYA_LOCATION_KEY, apiKey);
        result.append("   URL: ").append(testUrl.replace(apiKey, "***")).append("\n");

        try {
            org.springframework.http.ResponseEntity<String> response = restTemplate.getForEntity(testUrl, String.class);
            result.append("   Status: ").append(response.getStatusCode()).append("\n");
            String body = response.getBody();
            if (body != null && !body.isEmpty()) {
                result.append("   ✅ Response received (").append(body.length()).append(" chars)\n");
                result.append("   Preview: ").append(body.substring(0, Math.min(200, body.length()))).append("...\n");
            } else {
                result.append("   ⚠️ Response body is empty\n");
            }
        } catch (org.springframework.web.client.HttpClientErrorException e) {
            result.append("   ❌ HTTP Error: ").append(e.getStatusCode()).append("\n");
            result.append("   Response: ").append(e.getResponseBodyAsString()).append("\n");
            if (e.getStatusCode().value() == 401) {
                result.append("   -> Invalid API key. Please get a valid key from AccuWeather.\n");
            } else if (e.getStatusCode().value() == 503) {
                result.append("   -> Service unavailable. API quota may be exceeded.\n");
            }
        } catch (org.springframework.web.client.ResourceAccessException e) {
            result.append("   ❌ Connection Error: ").append(e.getMessage()).append("\n");
            result.append("   -> Check your internet connection or firewall settings.\n");
        } catch (Exception e) {
            result.append("   ❌ Error: ").append(e.getMessage()).append("\n");
        }

        return result.toString();
    }

    @PostConstruct
    public void init() {
        logger.info("WeatherService initialized with API key: {}",
            apiKey != null && !apiKey.isEmpty() ? apiKey.substring(0, Math.min(10, apiKey.length())) + "***" : "EMPTY OR NULL");
        if (apiKey == null || apiKey.isEmpty()) {
            logger.error("AccuWeather API key is not configured! Please set 'accuweather.api.key' in application.properties");
        }
        // AccuWeather API keys are typically 32 characters without special prefixes
        if (apiKey != null && apiKey.startsWith("zpka_")) {
            logger.warn("WARNING: Your API key has 'zpka_' prefix which is NOT a valid AccuWeather format!");
            logger.warn("Please get a valid API key from https://developer.accuweather.com");
        }
    }

    public WeatherForecastResponseDTO get1DayForecast() {
        String url = String.format("%s/forecasts/v1/daily/1day/%s?apikey=%s&metric=true&language=tr-tr&details=true",
                ACCUWEATHER_BASE_URL, KONYA_LOCATION_KEY, apiKey);
        logger.info("Requesting 1-day forecast, URL: {}", url.replace(apiKey, "***"));
        logger.info("API Key being used: {}", apiKey != null ? apiKey.substring(0, Math.min(10, apiKey.length())) + "***" : "NULL");
        try {
            String rawResponse = restTemplate.getForObject(url, String.class);
            logger.info("Raw API response length: {}", rawResponse != null ? rawResponse.length() : "NULL");
            logger.info("Raw API response: {}", rawResponse);

            // Make second call to parse the response
            WeatherForecastResponseDTO result = restTemplate.getForObject(url, WeatherForecastResponseDTO.class);
            if (result != null) {
                logger.info("Parsed result - headline: {}, forecasts: {}",
                    result.getHeadline() != null ? result.getHeadline().getText() : "null",
                    result.getDailyForecasts() != null ? result.getDailyForecasts().size() : 0);
            } else {
                logger.warn("Parsed result is NULL");
            }
            return result;
        } catch (Exception e) {
            logger.error("Error fetching 1-day forecast: {}", e.getMessage(), e);
            throw e;
        }
    }

    public String get1DayForecastRaw() {
        String url = String.format("%s/forecasts/v1/daily/1day/%s?apikey=%s&metric=true&language=tr-tr&details=true",
                ACCUWEATHER_BASE_URL, KONYA_LOCATION_KEY, apiKey);
        logger.info("Requesting raw 1-day forecast, URL: {}", url.replace(apiKey, "***"));
        logger.info("API Key being used: {}", apiKey != null ? apiKey.substring(0, Math.min(10, apiKey.length())) + "***" : "NULL");

        try {
            org.springframework.http.ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            logger.info("Response Status Code: {}", response.getStatusCode());
            logger.info("Response Headers: {}", response.getHeaders());
            String body = response.getBody();
            logger.info("Response Body length: {}", body != null ? body.length() : "NULL");
            logger.info("Response Body: {}", body);
            return body;
        } catch (org.springframework.web.client.HttpClientErrorException e) {
            logger.error("HTTP Client Error: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("AccuWeather API error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString(), e);
        } catch (org.springframework.web.client.HttpServerErrorException e) {
            logger.error("HTTP Server Error: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("AccuWeather server error: " + e.getStatusCode(), e);
        } catch (org.springframework.web.client.ResourceAccessException e) {
            logger.error("Network/Connection Error: {}", e.getMessage());
            throw new RuntimeException("Cannot connect to AccuWeather API: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage(), e);
            throw new RuntimeException("Unexpected error calling AccuWeather API: " + e.getMessage(), e);
        }
    }

    public WeatherForecastResponseDTO get5DayForecast() {
        String url = String.format("%s/forecasts/v1/daily/5day/%s?apikey=%s&metric=true&language=tr-tr&details=true",
                ACCUWEATHER_BASE_URL, KONYA_LOCATION_KEY, apiKey);
        return restTemplate.getForObject(url, WeatherForecastResponseDTO.class);
    }

    public WeatherForecastResponseDTO get10DayForecast() {
        String url = String.format("%s/forecasts/v1/daily/10day/%s?apikey=%s&metric=true&language=tr-tr&details=true",
                ACCUWEATHER_BASE_URL, KONYA_LOCATION_KEY, apiKey);
        return restTemplate.getForObject(url, WeatherForecastResponseDTO.class);
    }

    public WeatherForecastResponseDTO get15DayForecast() {
        String url = String.format("%s/forecasts/v1/daily/15day/%s?apikey=%s&metric=true&language=tr-tr&details=true",
                ACCUWEATHER_BASE_URL, KONYA_LOCATION_KEY, apiKey);
        return restTemplate.getForObject(url, WeatherForecastResponseDTO.class);
    }

    public WeatherForecastResponseDTO get30DayForecast() {
        // AccuWeather API does NOT support 30-day forecasts.
        // Maximum available is 15-day forecast (requires paid plan).
        // Using 15-day endpoint as the maximum available option.
        String url = String.format("%s/forecasts/v1/daily/15day/%s?apikey=%s&metric=true&language=tr-tr&details=true",
                ACCUWEATHER_BASE_URL, KONYA_LOCATION_KEY, apiKey);
        logger.info("Requesting 15-day forecast (maximum available from AccuWeather) from: {}", url.replace(apiKey, "***"));
        logger.warn("Note: AccuWeather does not provide 30-day forecasts. Using 15-day forecast (maximum available).");

        try {
            WeatherForecastResponseDTO response = restTemplate.getForObject(url, WeatherForecastResponseDTO.class);
            if (response != null) {
                logger.info("Parsed response - Headline: {}, DailyForecasts count: {}",
                    response.getHeadline() != null ? response.getHeadline().getText() : "null",
                    response.getDailyForecasts() != null ? response.getDailyForecasts().size() : 0);
            } else {
                logger.warn("Response was null");
            }
            return response;
        } catch (Exception e) {
            logger.error("Error fetching 15-day forecast: {}", e.getMessage(), e);
            throw e;
        }
    }

    public WeatherForecastResponseDTO getForecastByLocation(String locationKey, int days) {
        String endpoint;
        switch (days) {
            case 1:
                endpoint = "1day";
                break;
            case 5:
                endpoint = "5day";
                break;
            case 10:
                endpoint = "10day";
                break;
            case 15:
            case 30: // AccuWeather doesn't support 30-day, use 15-day maximum
                endpoint = "15day";
                if (days == 30) {
                    logger.warn("AccuWeather does not support 30-day forecasts. Using 15-day forecast instead.");
                }
                break;
            default:
                endpoint = "5day";
        }

        String url = String.format("%s/forecasts/v1/daily/%s/%s?apikey=%s&metric=true&language=tr-tr&details=true",
                ACCUWEATHER_BASE_URL, endpoint, locationKey, apiKey);
        return restTemplate.getForObject(url, WeatherForecastResponseDTO.class);
    }
}

