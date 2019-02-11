package mhk.com.domain.usecases;

import android.util.Log;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import mhk.com.domain.entities.Forecast;
import mhk.com.domain.entities.ForecastData;
import mhk.com.domain.repositories.WeatherForecastRepo;

/*
 * Copyright (c) 2017 McDonald's. All rights reserved.
 * Created by Mujahid H Khan on 2/09/19.
 */
public class FetchForecastInteractor implements FetchForecastUseCase {

    final String TAG = FetchForecastInteractor.class.getCanonicalName();

    private WeatherForecastRepo mRepo;

    public FetchForecastInteractor(WeatherForecastRepo repo) {
        mRepo = repo;
    }

    @Override
    public Single<ForecastData> getForecastByCityName(String city) {
        return mRepo.getForecastByCityName(city)
                .map(new Function<ForecastData, ForecastData>() {
                    @Override
                    public ForecastData apply(ForecastData data) throws Exception {
                        // some business logic,
                        // filter out elements from same dates
                        filterDataForSameDate(data);
                        return data;
                    }
                })
                .subscribeOn(Schedulers.computation());
    }

    @Override
    public Single<ForecastData> getForecastByCityLocation(long lat, long lon) {
        return mRepo.getForecastByCityLocation(lat, lon);
    }

    @Override
    public void filterDataForSameDate(ForecastData data) {
        try {
            List<Forecast> forecasts = data.getForecast();
            CheckSameDatePredicate sameDatePredicate = new CheckSameDatePredicate();
            List<Forecast> filteredForecasts = forecasts.stream().filter(sameDatePredicate).collect(Collectors.<Forecast>toList());
            data.setForecast(filteredForecasts);
        } catch (Exception e) {
            Log.e(TAG,"filterDataForSameDate",e);
        }
    }

    class CheckSameDatePredicate implements Predicate<Forecast> {

        private Forecast initialForecast;
        boolean isFirst = true;

        @Override
        public boolean test(Forecast forecast) {
            // add the first element as is.
            if (isFirst) {
                isFirst = false;
                initialForecast = forecast;
                return true;
            }
            boolean isFromSameDate = initialForecast.equals(forecast);
            initialForecast = forecast;
            // don't add two elements from same date
            return !isFromSameDate;
        }
    }
}
