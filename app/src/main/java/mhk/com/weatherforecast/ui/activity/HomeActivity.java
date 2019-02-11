package mhk.com.weatherforecast.ui.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import mhk.com.domain.repositories.WeatherForecastRepo;
import mhk.com.presentation.uimodel.ForecastViewData;
import mhk.com.presentation.viewmodel.ForecastViewModel;
import mhk.com.presentation.viewmodel.ForecastViewModelFactory;
import mhk.com.weatherforecast.R;
import mhk.com.weatherforecast.data.network.WeatherForecastDataNetworkRepo;
import mhk.com.weatherforecast.ui.adapter.WeatherFragmentPagerAdapter;
import mhk.com.weatherforecast.ui.fragment.WeatherFragment;

/*
 * Copyright (c) 2017 McDonald's. All rights reserved.
 * Created by Mujahid H Khan on 2/09/19.
 */
public class HomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private SearchView searchView;
    private WeatherForecastRepo repo;
    private WeatherFragmentPagerAdapter weatherFragmentPagerAdapter;
    private ForecastViewModel model;
    private ProgressBar progressBar;
    private LinearLayout sliderDotspanel;

    Observer weatherDataObserver = new Observer<List<ForecastViewData>>() {
        @Override
        public void onChanged(@Nullable List<ForecastViewData> forecastViewData) {
            progressBar.setVisibility(View.GONE);
            model.setUpDots(HomeActivity.this, sliderDotspanel, forecastViewData.size(),
                    ContextCompat.getDrawable(getApplicationContext(),
                            mhk.com.presentation.R.drawable.active_dot),
                    ContextCompat.getDrawable(getApplicationContext(),
                            mhk.com.presentation.R.drawable.non_active_dot));
            createFragmentData(forecastViewData);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        // should use DI here
        repo = new WeatherForecastDataNetworkRepo();
//        repo = new DummyRepo();

        viewPager = findViewById(R.id.viewPager);
        progressBar = findViewById(R.id.progressBar);
        sliderDotspanel = findViewById(R.id.sliderDots);
        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                hideSoftKeyboard();
                progressBar.setVisibility(View.VISIBLE);
                fetchWeatherData(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        model = ViewModelProviders.of(this, new ForecastViewModelFactory(repo))
                .get(ForecastViewModel.class);
        if (model.getForecastLiveData() != null) {
            viewPager.setOnPageChangeListener(model.pageChangeListener);
            model.getForecastLiveData().observe(this, weatherDataObserver);
        }
    }

    private void hideSoftKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(HomeActivity.this.getCurrentFocus().getWindowToken(),
                InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    private void fetchWeatherData(String s) {
        model.getForecastByCity(s);
    }

    private void createFragmentData(List<ForecastViewData> viewDataList) {
        List<Fragment> fragments = new ArrayList<>();
        for (ForecastViewData forecastViewData : viewDataList) {
            Fragment fragment = WeatherFragment.getInstance(forecastViewData);
            fragments.add(fragment);
        }
        weatherFragmentPagerAdapter = new WeatherFragmentPagerAdapter(getSupportFragmentManager(),
                fragments);
        viewPager.setAdapter(weatherFragmentPagerAdapter);
    }
}
