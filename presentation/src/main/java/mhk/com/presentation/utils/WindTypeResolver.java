package mhk.com.presentation.utils;

/*
 * Copyright (c) 2017 McDonald's. All rights reserved.
 * Created by Mujahid H Khan on 2/10/19.
 */
public class WindTypeResolver implements AbstractWindTypeResolver {

    @Override
    public @WindType String resolveWindType(double windSpeed) {
        if(windSpeed < 0.6) {
            return Constants.CALM;
        } else if(windSpeed >= 0.6 && windSpeed <= 3.1) {
            return Constants.LIGHT_AIR;
        } else if(windSpeed >= 3.1 && windSpeed <= 6.8) {
            return Constants.LIGHT_BREEZE;
        } else if(windSpeed >= 6.8 && windSpeed <= 23.6) {
            return Constants.FRESH_BREEZE;
        } else if(windSpeed >= 23.6 && windSpeed <= 31) {
            return Constants.STRONG_GALE;
        } else if(windSpeed >= 31 && windSpeed <= 62.13) {
            return Constants.WHOLE_GALE;
        } else if(windSpeed > 39 && windSpeed <= 71) {
            return Constants.STORM;
        } else if(windSpeed > 71) {
            return Constants.HURRICANE;
        } else
            return Constants.UNRESOLVED;
    }
}
