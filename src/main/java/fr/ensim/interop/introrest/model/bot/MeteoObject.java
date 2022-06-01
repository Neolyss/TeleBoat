package fr.ensim.interop.introrest.model.bot;

public class MeteoObject {

    private String cityName;
    private String dayOfWeek;

    public MeteoObject() {
    }

    public MeteoObject(String cityName, String dayOfWeek) {
        this.cityName = cityName;
        this.dayOfWeek = dayOfWeek;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}

