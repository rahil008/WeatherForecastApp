package mhk.com.presentation.converter;

/*
 * Copyright (c) 2017 McDonald's. All rights reserved.
 * Created by Mujahid H Khan on 2/10/19.
 */
public interface Converter<T,R> {

    R convert(T t);
}
