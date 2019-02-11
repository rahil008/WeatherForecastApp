package mhk.com.domain.repositories;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import mhk.com.domain.entities.Forecast;
import mhk.com.domain.entities.ForecastData;
import mhk.com.domain.entities.Main;
import mhk.com.domain.entities.Weather;
import mhk.com.domain.entities.Wind;

/*
 * Copyright (c) 2017 McDonald's. All rights reserved.
 * Created by Mujahid H Khan on 2/10/19.
 */
public class DummyRepo implements WeatherForecastRepo {
    @Override
    public Single<ForecastData> getForecastByCityName(String city) {
        return Single.create(new SingleOnSubscribe<ForecastData>() {
            @Override
            public void subscribe(SingleEmitter<ForecastData> emitter) throws Exception {
                List<Forecast> data = new ArrayList<>();

                Forecast forecast = new Forecast();
                forecast.setDtTxt("2019-01-30 09:00:00");

                Wind wind = new Wind();
                wind.setSpeed(2.5);
                forecast.setWind(wind);

                Weather weather = new Weather();
                weather.setDescription("Cloudy");

                Main main = new Main();
                main.setHumidity(50);
                main.setPressure(123.0);
                main.setTemp(56.0);
                main.setTempMax(70.0);
                main.setTempMin(30.0);

                ArrayList<Weather> weathers = new ArrayList<>();
                weathers.add(weather);
                forecast.setWeather(weathers);
                forecast.setMain(main);

                data.add(forecast);

                forecast = new Forecast();
                forecast.setDtTxt("2019-01-30 12:00:00");

                wind = new Wind();
                wind.setSpeed(5.0);
                forecast.setWind(wind);

                weather = new Weather();
                weather.setDescription("Cloudy");

                main = new Main();
                main.setHumidity(50);
                main.setPressure(123.0);
                main.setTemp(56.0);
                main.setTempMax(70.0);
                main.setTempMin(30.0);

                ArrayList<Weather> weathers2 = new ArrayList<>();
                weathers2.add(weather);
                forecast.setWeather(weathers2);
                forecast.setMain(main);

                data.add(forecast);

                forecast = new Forecast();
                forecast.setDtTxt("2019-01-31 15:00:00");

                wind = new Wind();
                wind.setSpeed(10.0);
                forecast.setWind(wind);

                weather = new Weather();
                weather.setDescription("Rainy");

                main = new Main();
                main.setHumidity(50);
                main.setPressure(123.0);
                main.setTemp(56.0);
                main.setTempMax(70.0);
                main.setTempMin(30.0);

                ArrayList<Weather> weathers4 = new ArrayList<>();
                weathers4.add(weather);
                forecast.setWeather(weathers4);
                forecast.setMain(main);

                data.add(forecast);

                forecast = new Forecast();
                forecast.setDtTxt("2019-01-31 12:00:00");

                wind = new Wind();
                wind.setSpeed(50.0);
                forecast.setWind(wind);

                weather = new Weather();
                weather.setDescription("Sunny");

                main = new Main();
                main.setHumidity(100);
                main.setPressure(123.0);
                main.setTemp(56.0);
                main.setTempMax(100.0);
                main.setTempMin(30.0);

                ArrayList<Weather> weathers3 = new ArrayList<>();
                weathers3.add(weather);
                forecast.setWeather(weathers3);
                forecast.setMain(main);

                data.add(forecast);

                ForecastData forecastData = new ForecastData();
                forecastData.setForecast(data);

                emitter.onSuccess(forecastData);
            }
        });
    }

    @Override
    public Single<ForecastData> getForecastByCityLocation(long lat, long lon) {
        return null;
    }
}
