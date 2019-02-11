
package mhk.com.domain.entities;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Forecast {

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    @SerializedName("dt")
    private Integer dt;
    @SerializedName("main")
    private Main main;
    @SerializedName("weather")
    private List<Weather> weather = null;
    @SerializedName("clouds")
    private Clouds clouds;
    @SerializedName("wind")
    private Wind wind;
    @SerializedName("dt_txt")
    private String dtTxt;

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public String getDtTxt() {
        return dtTxt;
    }

    public void setDtTxt(String dtTxt) {
        this.dtTxt = dtTxt;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Forecast)) {
            return false;
        }
        if(isSameDate(this.getDtTxt(), ((Forecast)obj).getDtTxt())) {
            return true;
        }
        return false;
    }

    private boolean isSameDate(String date1, String date2) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
            return !(sdf.parse(date1).before(sdf.parse(date2)) || sdf.parse(date1).after(sdf.parse(date2)));
        } catch (ParseException e) {
            Log.e("","isSameDate", e);
        }
        return false;
    }

    @Override
    public int hashCode() {

        return Objects.hash(dt, main, weather, clouds, wind, dtTxt);
    }
}
