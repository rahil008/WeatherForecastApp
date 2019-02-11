package mhk.com.weatherforecast.data.network;

import mhk.com.domain.entities.ForecastData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/*
 * Copyright (c) 2017 McDonald's. All rights reserved.
 * Created by Mujahid H Khan on 2/09/19.
 */
interface APIInterface {

    @GET("/data/2.5/forecast?mode=json&appid=a1110ad32ecc97c8f0820f0246ffc4ec")
    Call<ForecastData> getForecastData(@Query("q") String city);

//    @POST("/api/users")
//    Call<User> createUser(@Body User user);
//
//    @GET("/api/users?")
//    Call<UserList> doGetUserList(@Query("page") String page);
//
//    @FormUrlEncoded
//    @POST("/api/users?")
//    Call<UserList> doCreateUserWithField(@Field("name") String name, @Field("job") String job);
}
