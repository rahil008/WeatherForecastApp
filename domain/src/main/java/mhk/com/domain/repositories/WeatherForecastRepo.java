package mhk.com.domain.repositories;

import io.reactivex.Single;
import mhk.com.domain.entities.ForecastData;

/*
 * Copyright (c) 2017 McDonald's. All rights reserved.
 * Created by Mujahid H Khan on 2/09/19.
 */
public interface WeatherForecastRepo {

    Single<ForecastData> getForecastByCityName(String city);

    Single<ForecastData> getForecastByCityLocation(long lat, long lon);
}
