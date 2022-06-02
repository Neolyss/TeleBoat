package fr.ensim.interop.introrest.model.openWeather;

public class Temperature {

    private float temp;
    private float feels_like;
    private float temp_min;
    private float temp_max;
    private float humidity;

    public Temperature() {
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(float feels_like) {
        this.feels_like = feels_like;
    }

    public float getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(float temp_min) {
        this.temp_min = temp_min;
    }

    public float getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(float temp_max) {
        this.temp_max = temp_max;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public int convertToCelsius(float kelvin) {
        return Math.round(kelvin - 273.15f);
    }

    @Override
    public String toString() {
        return "with a temperature of " + convertToCelsius(getTemp())
                + " feels like " + convertToCelsius(getFeels_like())
                + " and humidity as " + getHumidity();
    }
}
