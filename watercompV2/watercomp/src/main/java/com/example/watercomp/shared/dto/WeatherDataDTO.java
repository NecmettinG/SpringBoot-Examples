package com.example.watercomp.shared.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDataDTO {

    @JsonProperty("Date")
    private String date;

    @JsonProperty("EpochDate")
    private Long epochDate;

    @JsonProperty("Temperature")
    private Temperature temperature;

    @JsonProperty("Day")
    private DayNight day;

    @JsonProperty("Night")
    private DayNight night;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getEpochDate() {
        return epochDate;
    }

    public void setEpochDate(Long epochDate) {
        this.epochDate = epochDate;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public DayNight getDay() {
        return day;
    }

    public void setDay(DayNight day) {
        this.day = day;
    }

    public DayNight getNight() {
        return night;
    }

    public void setNight(DayNight night) {
        this.night = night;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Temperature {
        @JsonProperty("Minimum")
        private TemperatureValue minimum;

        @JsonProperty("Maximum")
        private TemperatureValue maximum;

        public TemperatureValue getMinimum() {
            return minimum;
        }

        public void setMinimum(TemperatureValue minimum) {
            this.minimum = minimum;
        }

        public TemperatureValue getMaximum() {
            return maximum;
        }

        public void setMaximum(TemperatureValue maximum) {
            this.maximum = maximum;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TemperatureValue {
        @JsonProperty("Value")
        private Double value;

        @JsonProperty("Unit")
        private String unit;

        public Double getValue() {
            return value;
        }

        public void setValue(Double value) {
            this.value = value;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DayNight {
        @JsonProperty("Icon")
        private Integer icon;

        @JsonProperty("IconPhrase")
        private String iconPhrase;

        @JsonProperty("HasPrecipitation")
        private Boolean hasPrecipitation;

        @JsonProperty("PrecipitationType")
        private String precipitationType;

        @JsonProperty("PrecipitationIntensity")
        private String precipitationIntensity;

        @JsonProperty("RelativeHumidity")
        private HumidityValue relativeHumidity;

        @JsonProperty("Wind")
        private Wind wind;

        @JsonProperty("WindGust")
        private Wind windGust;

        @JsonProperty("CloudCover")
        private Integer cloudCover;

        public Integer getIcon() {
            return icon;
        }

        public void setIcon(Integer icon) {
            this.icon = icon;
        }

        public String getIconPhrase() {
            return iconPhrase;
        }

        public void setIconPhrase(String iconPhrase) {
            this.iconPhrase = iconPhrase;
        }

        public Boolean getHasPrecipitation() {
            return hasPrecipitation;
        }

        public void setHasPrecipitation(Boolean hasPrecipitation) {
            this.hasPrecipitation = hasPrecipitation;
        }

        public String getPrecipitationType() {
            return precipitationType;
        }

        public void setPrecipitationType(String precipitationType) {
            this.precipitationType = precipitationType;
        }

        public String getPrecipitationIntensity() {
            return precipitationIntensity;
        }

        public void setPrecipitationIntensity(String precipitationIntensity) {
            this.precipitationIntensity = precipitationIntensity;
        }

        public HumidityValue getRelativeHumidity() {
            return relativeHumidity;
        }

        public void setRelativeHumidity(HumidityValue relativeHumidity) {
            this.relativeHumidity = relativeHumidity;
        }

        public Wind getWind() {
            return wind;
        }

        public void setWind(Wind wind) {
            this.wind = wind;
        }

        public Wind getWindGust() {
            return windGust;
        }

        public void setWindGust(Wind windGust) {
            this.windGust = windGust;
        }

        public Integer getCloudCover() {
            return cloudCover;
        }

        public void setCloudCover(Integer cloudCover) {
            this.cloudCover = cloudCover;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HumidityValue {
        @JsonProperty("Minimum")
        private Integer minimum;

        @JsonProperty("Maximum")
        private Integer maximum;

        @JsonProperty("Average")
        private Integer average;

        public Integer getMinimum() {
            return minimum;
        }

        public void setMinimum(Integer minimum) {
            this.minimum = minimum;
        }

        public Integer getMaximum() {
            return maximum;
        }

        public void setMaximum(Integer maximum) {
            this.maximum = maximum;
        }

        public Integer getAverage() {
            return average;
        }

        public void setAverage(Integer average) {
            this.average = average;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Wind {
        @JsonProperty("Speed")
        private WindSpeed speed;

        @JsonProperty("Direction")
        private WindDirection direction;

        public WindSpeed getSpeed() {
            return speed;
        }

        public void setSpeed(WindSpeed speed) {
            this.speed = speed;
        }

        public WindDirection getDirection() {
            return direction;
        }

        public void setDirection(WindDirection direction) {
            this.direction = direction;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WindSpeed {
        @JsonProperty("Value")
        private Double value;

        @JsonProperty("Unit")
        private String unit;

        public Double getValue() {
            return value;
        }

        public void setValue(Double value) {
            this.value = value;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WindDirection {
        @JsonProperty("Degrees")
        private Integer degrees;

        @JsonProperty("Localized")
        private String localized;

        @JsonProperty("English")
        private String english;

        public Integer getDegrees() {
            return degrees;
        }

        public void setDegrees(Integer degrees) {
            this.degrees = degrees;
        }

        public String getLocalized() {
            return localized;
        }

        public void setLocalized(String localized) {
            this.localized = localized;
        }

        public String getEnglish() {
            return english;
        }

        public void setEnglish(String english) {
            this.english = english;
        }
    }
}

