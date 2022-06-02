package fr.ensim.interop.introrest.model.openWeather;

import java.util.ArrayList;


public class OpenWeatherForcast implements OpenWeather {
    private float lat;
    private float lon;
    private String timezone;
    private float timezone_offset;
    private ArrayList<Daily> daily = new ArrayList<Daily>();


    // Getter Methods

    public float getLat() {
        return lat;
    }

    public float getLon() {
        return lon;
    }

    public String getTimezone() {
        return timezone;
    }

    public float getTimezone_offset() {
        return timezone_offset;
    }

    // Setter Methods

    public void setLat( float lat ) {
        this.lat = lat;
    }

    public void setLon( float lon ) {
        this.lon = lon;
    }

    public void setTimezone( String timezone ) {
        this.timezone = timezone;
    }

    public void setTimezone_offset( float timezone_offset ) {
        this.timezone_offset = timezone_offset;
    }

    public ArrayList<Daily> getDaily() {
        return daily;
    }

    public void setDaily(ArrayList<Daily> daily) {
        this.daily = daily;
    }

    @Override
    public String toString() {
        return "OpenWeatherForcast{" +
                "lat=" + lat +
                ", lon=" + lon +
                ", timezone='" + timezone + '\'' +
                ", timezone_offset=" + timezone_offset +
                ", daily=" + daily +
                '}';
    }
}

class Daily{
    public int dt;
    public int sunrise;
    public int sunset;
    public int moonrise;
    public int moonset;
    public double moon_phase;
    public Temp temp;
    public FeelsLike feels_like;
    public int pressure;
    public int humidity;
    public double dew_point;
    public double wind_speed;
    public int wind_deg;
    public double wind_gust;
    public ArrayList<Weather> weather;
    public int clouds;
    public double pop;
    public double uvi;
    public double rain;

    @Override
    public String toString() {
        return "Daily{" +
                "dt=" + dt +
                ", sunrise=" + sunrise +
                ", sunset=" + sunset +
                ", moonrise=" + moonrise +
                ", moonset=" + moonset +
                ", moon_phase=" + moon_phase +
                ", temp=" + temp +
                ", feels_like=" + feels_like +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", dew_point=" + dew_point +
                ", wind_speed=" + wind_speed +
                ", wind_deg=" + wind_deg +
                ", wind_gust=" + wind_gust +
                ", weather=" + weather +
                ", clouds=" + clouds +
                ", pop=" + pop +
                ", uvi=" + uvi +
                ", rain=" + rain +
                '}';
    }
}

class FeelsLike{
    public double day;
    public double night;
    public double eve;
    public double morn;

    @Override
    public String toString() {
        return "FeelsLike{" +
                "day=" + day +
                ", night=" + night +
                ", eve=" + eve +
                ", morn=" + morn +
                '}';
    }
}

class Temp{
    public double day;
    public double min;
    public double max;
    public double night;
    public double eve;
    public double morn;

    @Override
    public String toString() {
        return "Temp{" +
                "day=" + day +
                ", min=" + min +
                ", max=" + max +
                ", night=" + night +
                ", eve=" + eve +
                ", morn=" + morn +
                '}';
    }
}

//public class Weather{
//    public int id;
//    public String main;
//    public String description;
//    public String icon;
//}

