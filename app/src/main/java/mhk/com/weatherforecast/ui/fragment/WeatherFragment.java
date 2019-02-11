package mhk.com.weatherforecast.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import mhk.com.presentation.uimodel.ForecastViewData;
import mhk.com.weatherforecast.R;

/*
 * Copyright (c) 2017 McDonald's. All rights reserved.
 * Created by Mujahid H Khan on 2/09/19.
 */
public class WeatherFragment extends Fragment {

    private TextView tempText;
    private TextView tempUnitText;
    private TextView weatherText;
    private TextView weatherDataText;
    private TextView dateText;
    private ImageView weatherImage;

    public static Fragment getInstance(ForecastViewData forecastViewData) {
        Fragment fragment = new WeatherFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("weatherViewData", forecastViewData);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_weather, container, false);
        initViews(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        extractData();
    }

    private void initViews(View rootView) {
        tempText = rootView.findViewById(R.id.tempText);
        tempUnitText = rootView.findViewById(R.id.tempUnitText);
        weatherText = rootView.findViewById(R.id.weatherText);
        weatherDataText = rootView.findViewById(R.id.weatherDataText);
        dateText = rootView.findViewById(R.id.dateText);
        weatherImage = rootView.findViewById(R.id.weatherImage);
    }

    private void extractData() {
        Bundle arguments = getArguments();
        ForecastViewData weatherViewData = (ForecastViewData) arguments.getSerializable("weatherViewData");
        setData(weatherViewData);
    }

    private void setData(ForecastViewData weatherViewData) {
        tempText.setText(weatherViewData.getTemperature().temp);
        tempUnitText.setText("\u2103");
        weatherText.setText(weatherViewData.getWeatherDescription());
        weatherDataText.setText(Html.fromHtml(weatherViewData.getHtmlFormattedWeatherText()));
        dateText.setText(weatherViewData.getDateString());
        weatherImage.setBackgroundResource(weatherViewData.getForeground());
    }
}
