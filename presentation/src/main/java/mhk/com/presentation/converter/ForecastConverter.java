package mhk.com.presentation.converter;

import android.util.Log;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import mhk.com.domain.entities.Forecast;
import mhk.com.domain.entities.ForecastData;
import mhk.com.domain.entities.Main;
import mhk.com.domain.entities.Weather;
import mhk.com.domain.entities.Wind;
import mhk.com.presentation.uimodel.ForecastViewData;
import mhk.com.presentation.utils.AbstractImageResourceResolver;
import mhk.com.presentation.utils.AbstractWindTypeResolver;
import mhk.com.presentation.utils.ImageResourceResolver;
import mhk.com.presentation.utils.WindTypeResolver;

/*
 * Copyright (c) 2017 McDonald's. All rights reserved.
 * Created by Mujahid H Khan on 2/10/19.
 */
public class ForecastConverter implements Converter<ForecastData, List<ForecastViewData>> {

    private static double K_TO_C_CONVERSION_FACTOR = 273.15;
    private static final String HTML_FORMATTED_TEXT = "<b>Max/Min </b>%s/%s<br> <b>Pressure </b>%s hPa<br><b>Wind </b>%s<br><b>Humidity </b>%s %%";
    private static final String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
    private AbstractWindTypeResolver resolver;
    private AbstractImageResourceResolver backgroundResolver;

    public ForecastConverter() {
        // should use DI here
        this.resolver = new WindTypeResolver();
        backgroundResolver = new ImageResourceResolver();
    }

    public ForecastConverter(AbstractWindTypeResolver resolver, AbstractImageResourceResolver imageResourceResolver) {
        // should use DI here
        this.resolver = resolver;
        backgroundResolver = imageResourceResolver;
    }

    @Override
    public List<ForecastViewData> convert(ForecastData forecastData) {
        List<ForecastViewData> list = new ArrayList<>();
        if (forecastData != null) {
            List<Forecast> forecasts = forecastData.getForecast();
            if (forecasts != null && forecasts.size() > 0) {
                for (Forecast forecast : forecasts) {
                    ForecastViewData forecastViewData = new ForecastViewData();
                    forecastViewData.setDateString(getFormattedDate(forecast.getDtTxt()));

                    Main mainWeatherData = forecast.getMain();
                    setMainData(mainWeatherData, forecastViewData);

                    Wind wind = forecast.getWind();
                    setWindData(wind, forecastViewData);

                    List<Weather> weather = forecast.getWeather();
                    Weather weather1 = weather.get(0);
                    String description = weather1.getDescription();
                    forecastViewData.setWeatherDescription(description);
                    forecastViewData.setForeground(backgroundResolver.resolveForeground(description));
                    forecastViewData.setBackground(backgroundResolver.resolveBackground(isDay(forecast)));

                    String formattedDataText = getFormattedDataText(forecast, wind, mainWeatherData);
                    forecastViewData.setHtmlFormattedWeatherText(formattedDataText);

                    list.add(forecastViewData);
                }
            }
        }
        return list;
    }

    private String getFormattedDataText(Forecast forecast, Wind wind, Main main) {
        ForecastViewData.Temperature tempInCelsius = getTempInCelsius(main.getTemp(),
                main.getTempMax(), main.getTempMin());
        String maxTemp = tempInCelsius.tempMax;
        String minTemp = tempInCelsius.tempMin;
        return String.format(HTML_FORMATTED_TEXT, maxTemp, minTemp, forecast.getMain().getPressure(),
                getWindText(wind), forecast.getMain().getHumidity());
    }

    private String getWindText(Wind wind) {
        return String.format("%s mps, %s", wind.getSpeed(), resolver.resolveWindType(wind.getSpeed()));
    }

    private boolean isDay(Forecast forecast) {
        String dtTxt = forecast.getDtTxt();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
            Date date = sdf.parse(dtTxt);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            if (hour >= 6 && hour <= 18) {
                return false;
            }
        } catch (ParseException e) {
            Log.e("", "", e);
        }
        return true;
    }

    private String getFormattedDate(String ugly) {
        String formattedDateString = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("EEE dd-MMM-yyyy", Locale.getDefault());
            Date date = sdf.parse(ugly);
            formattedDateString = outputFormat.format(date);
            return formattedDateString;
        } catch (ParseException e) {
            Log.e("", "", e);
        }
        return formattedDateString;
    }

    private void setWindData(Wind wind, ForecastViewData forecastViewData) {
        if (wind != null) {
            Double speed = wind.getSpeed();
            forecastViewData.setWindSpeed("" + speed);
        }
    }

    private void setMainData(Main mainWeatherData, ForecastViewData forecastViewData) {
        if (mainWeatherData != null) {
            forecastViewData.setHumidity("" + mainWeatherData.getHumidity());
            forecastViewData.setPressure("" + mainWeatherData.getPressure());
            Double temp = mainWeatherData.getTemp();
            Double tempMax = mainWeatherData.getTempMax();
            Double tempMin = mainWeatherData.getTempMin();
            ForecastViewData.Temperature tempInCelsius = getTempInCelsius(temp, tempMax, tempMin);
            forecastViewData.setTemperature(tempInCelsius);
        }
    }

    private ForecastViewData.Temperature getTempInCelsius(Double temp, Double tempMax, Double tempMin) {
        DecimalFormat numberFormat = new DecimalFormat("#");
        temp = temp - K_TO_C_CONVERSION_FACTOR;
        tempMax = tempMax - K_TO_C_CONVERSION_FACTOR;
        tempMin = tempMin - K_TO_C_CONVERSION_FACTOR;
        String tempText = numberFormat.format(temp);
        String tempMaxText = numberFormat.format(tempMax);
        String tempMinText = numberFormat.format(tempMin);

        return new ForecastViewData.Temperature(tempText, tempMaxText, tempMinText);
    }
}
