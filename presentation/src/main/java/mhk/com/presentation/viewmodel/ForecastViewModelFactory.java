package mhk.com.presentation.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import mhk.com.domain.repositories.WeatherForecastRepo;

/*
 * Copyright (c) 2017 McDonald's. All rights reserved.
 * Created by Mujahid H Khan on 2/10/19.
 */
public class ForecastViewModelFactory implements ViewModelProvider.Factory {

    private WeatherForecastRepo mRepo;

    public ForecastViewModelFactory(WeatherForecastRepo mRepo) {
        this.mRepo = mRepo;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new ForecastViewModel(mRepo);
    }
}
