package mhk.com.presentation.utils;

import mhk.com.presentation.R;

/*
 * Copyright (c) 2017 McDonald's. All rights reserved.
 * Created by Mujahid H Khan on 2/10/19.
 */
public class ImageResourceResolver implements AbstractImageResourceResolver {

    @Override
    public int resolveForeground(String weatherDescription) {
        if (weatherDescription != null) {
            weatherDescription = weatherDescription.toLowerCase();
            if (weatherDescription.contains(Constants.Background.CLOUDY)) {
                return R.drawable.cloud;
            } else if (weatherDescription.contains(Constants.Background.HAZY)) {
                return R.drawable.fog;
            } else if (weatherDescription.contains(Constants.Background.RAIN)) {
                return R.drawable.rain;
            } else if (weatherDescription.contains(Constants.Background.SNOW)) {
                return R.drawable.snow;
            } else if (weatherDescription.contains(Constants.Background.CLEAR)) {
                return R.drawable.sunny;
            } else {
                return -1;
            }
        }
        return -1;
    }

    @Override
    public int resolveBackground(boolean isDay) {
       if(isDay) {
           return R.drawable.clear_day;
       }
       return R.drawable.clear_night;
    }
}
