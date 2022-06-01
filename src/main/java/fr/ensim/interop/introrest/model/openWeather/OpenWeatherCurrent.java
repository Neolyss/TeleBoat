package fr.ensim.interop.introrest.model.openWeather;

import java.util.List;

public class OpenWeatherCurrent implements OpenWeather{

    private List<Weather> weather;

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    @Override
    public String toString() {
        return "OpenWeatherCurrent{" +
                "weather=" + weather +
                '}';
    }
}
