package mhk.com.presentation.utils;

/*
 * Copyright (c) 2017 McDonald's. All rights reserved.
 * Created by Mujahid H Khan on 2/10/19.
 */
public interface AbstractImageResourceResolver {

    int resolveForeground(String weatherDescription);
    int resolveBackground(boolean isDay);
}
