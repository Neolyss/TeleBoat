package fr.ensim.interop.introrest.model.openWeather;

import java.util.List;

public class OpenWeatherCurrent implements OpenWeather{

    private String name;
    private List<Weather> weather;
    private Temperature main;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Temperature getMain() {
        return main;
    }

    public void setMain(Temperature main) {
        this.main = main;
    }

    @Override
    public String toString() {
        return "The current weather at " + name +  " is " + weather.get(0).getDescription() + " " + main;
    }
}
