package mhk.com.presentation.utils;

import android.support.annotation.StringDef;

/*
 * Copyright (c) 2017 McDonald's. All rights reserved.
 * Created by Mujahid H Khan on 2/10/19.
 */
public interface AbstractWindTypeResolver {

    String resolveWindType(double windSpeed);

    @StringDef({Constants.CALM, Constants.LIGHT_AIR, Constants.LIGHT_BREEZE, Constants.FRESH_BREEZE,
            Constants.STRONG_GALE, Constants.WHOLE_GALE, Constants.STORM, Constants.HURRICANE,
            Constants.UNRESOLVED})
    @interface WindType {

    }
}
