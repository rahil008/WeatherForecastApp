package mhk.com.presentation.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import mhk.com.domain.entities.ForecastData;
import mhk.com.domain.repositories.WeatherForecastRepo;
import mhk.com.domain.usecases.FetchForecastInteractor;
import mhk.com.domain.usecases.FetchForecastUseCase;
import mhk.com.presentation.converter.Converter;
import mhk.com.presentation.converter.ForecastConverter;
import mhk.com.presentation.uimodel.ForecastViewData;

/*
 * Copyright (c) 2017 McDonald's. All rights reserved.
 * Created by Mujahid H Khan on 2/10/19.
 */
public class ForecastViewModel extends ViewModel {

    private MutableLiveData<List<ForecastViewData>> mForecastLiveData;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    // should use DI here
    private Converter<ForecastData, List<ForecastViewData>> mConverter;
    private FetchForecastUseCase mFetchForecastUseCase;
    private int dotscount;
    private ImageView[] dots;
    private Drawable activeDotDrawable;
    private Drawable nonActiveDotDrawable;

    ForecastViewModel(WeatherForecastRepo repo) {
        // Lets not clutter calling code with knowledge of these converter and interactor used,
        // Instead, DI should be used here
        mForecastLiveData = new MutableLiveData<>();
        mFetchForecastUseCase = new FetchForecastInteractor(repo);
        mConverter = new ForecastConverter();
    }

    public void setUpDots(Context context, LinearLayout sliderDotspanel, int count,
                          Drawable activeDotDrawable, Drawable nonActiveDotDrawable) {
        this.activeDotDrawable = activeDotDrawable;
        this.nonActiveDotDrawable = nonActiveDotDrawable;
        sliderDotspanel.removeAllViews();
        dotscount = count;
        dots = new ImageView[dotscount];
        for (int i = 0; i < dotscount; i++) {
            dots[i] = new ImageView(context);
            dots[i].setImageDrawable(nonActiveDotDrawable);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            sliderDotspanel.addView(dots[i], params);

        }
        dots[0].setImageDrawable(activeDotDrawable);
    }

    public void getForecastByCity(String city) {
        getForecast(city);
    }

    private void getForecast(String city) {
        Disposable disposable = mFetchForecastUseCase.getForecastByCityName(city)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(new Consumer<ForecastData>() {
                    @Override
                    public void accept(ForecastData forecasts) {
                        // convert to view specific model
                        List<ForecastViewData> list = mConverter.convert(forecasts);
                        mForecastLiveData.setValue(list);
                    }
                })
                .subscribe();
        mCompositeDisposable.add(disposable);
    }

    public MutableLiveData<List<ForecastViewData>> getForecastByLocation(long lat, long lon) {
        if (mForecastLiveData == null) {
            mForecastLiveData = new MutableLiveData<>();
            getForecast(lat, lon);
        }
        return mForecastLiveData;
    }

    private void getForecast(long lat, long lon) {
        Disposable disposable = mFetchForecastUseCase.getForecastByCityLocation(lat, lon)
                .doOnSuccess(new Consumer<ForecastData>() {
                    @Override
                    public void accept(ForecastData forecasts) {
                        // convert to view specific model
                        List<ForecastViewData> list = mConverter.convert(forecasts);
                        mForecastLiveData.setValue(list);
                    }
                })
                .subscribe();
        mCompositeDisposable.add(disposable);
    }

    public MutableLiveData<List<ForecastViewData>> getForecastLiveData() {
        return mForecastLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.clear();
    }

    public ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < dotscount; i++) {
                dots[i].setImageDrawable(nonActiveDotDrawable);
            }
            dots[position].setImageDrawable(activeDotDrawable);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

}
