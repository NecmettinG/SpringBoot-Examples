package com.example.watercomp.shared.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherForecastResponseDTO {

    @JsonProperty("Headline")
    private Headline headline;

    @JsonProperty("DailyForecasts")
    private List<WeatherDataDTO> dailyForecasts;

    public Headline getHeadline() {
        return headline;
    }

    public void setHeadline(Headline headline) {
        this.headline = headline;
    }

    public List<WeatherDataDTO> getDailyForecasts() {
        return dailyForecasts;
    }

    public void setDailyForecasts(List<WeatherDataDTO> dailyForecasts) {
        this.dailyForecasts = dailyForecasts;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Headline {
        @JsonProperty("EffectiveDate")
        private String effectiveDate;

        @JsonProperty("Text")
        private String text;

        @JsonProperty("Category")
        private String category;

        public String getEffectiveDate() {
            return effectiveDate;
        }

        public void setEffectiveDate(String effectiveDate) {
            this.effectiveDate = effectiveDate;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }
    }
}

