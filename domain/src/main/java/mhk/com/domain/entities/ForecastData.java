
package mhk.com.domain.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastData {

    @SerializedName("list")
    private List<Forecast> forecast = null;

    public List<Forecast> getForecast() {
        return forecast;
    }

    public void setForecast(List<Forecast> forecast) {
        this.forecast = forecast;
    }
}
