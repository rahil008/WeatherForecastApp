package mhk.com.weatherforecast.data.network;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.schedulers.Schedulers;
import mhk.com.domain.entities.ForecastData;
import mhk.com.domain.repositories.WeatherForecastRepo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 * Copyright (c) 2017 McDonald's. All rights reserved.
 * Created by Mujahid H Khan on 2/09/19.
 */
public class WeatherForecastDataNetworkRepo implements WeatherForecastRepo {

    APIInterface apiInterface;

    public WeatherForecastDataNetworkRepo() {
        apiInterface = APIClient.getClient().create(APIInterface.class);
    }

    @Override
    public Single<ForecastData> getForecastByCityName(final String city) {
        return Single.create(new SingleOnSubscribe<ForecastData>() {
            @Override
            public void subscribe(final SingleEmitter<ForecastData> emitter) {
                performSubscription(emitter, city);
            }
        }).subscribeOn(Schedulers.io());
    }

    private void performSubscription(final SingleEmitter<ForecastData> emitter, String city) {
        Call<ForecastData> forecastDataCall = apiInterface.getForecastData(city);
        forecastDataCall.enqueue(new Callback<ForecastData>() {
            @Override
            public void onResponse(Call<ForecastData> call, Response<ForecastData> response) {
                emitter.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ForecastData> call, Throwable t) {
                emitter.onError(t);
            }
        });

    }

    @Override
    public Single<ForecastData> getForecastByCityLocation(long lat, long lon) {
        return null;
    }
}
