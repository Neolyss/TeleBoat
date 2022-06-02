package fr.ensim.interop.introrest.model.openWeather;

import java.security.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class OpenWeatherForecast implements OpenWeather {
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
        StringBuilder builder = new StringBuilder("The weather for 7 days is : ");
        builder.append("\n");
        for(Daily oneDaily : daily) {
            builder.append("-\t").append(oneDaily);
            builder.append("\n");
        }
        return builder.toString();
    }
}

class Daily{
    public long dt;
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
        long timestampLong = dt*1000;
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));
        System.out.println(dt);
        cal.setTimeInMillis(timestampLong);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEEE");
        String dayOfWeek = dateFormat.format(cal.getTime());
        return "Forecast of " + dayOfWeek + temp.toString();
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

    public int convertToCelsius(double kelvin) {
        return (int) Math.round(kelvin - 273.15);
    }

    @Override
    public String toString() {
            return " temp. " + convertToCelsius(day) + " | " +
                " min temp." + convertToCelsius(min) + " | " +
                " max temp." + convertToCelsius(max) + " | " +
                " night temp." + convertToCelsius(night);
    }
}
