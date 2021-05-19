package com.example.polycustomer.event;


import com.example.polycustomer.Model.NotifyModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface RetrofitService {
    @Headers("AAAAU1NOJeg:APA91bFSkzSYoJKwKoXrZRJcW_yMdghGaHFrYxKQLL_jd5yaInmbiOdxtlWaOcyD2tvSPPZNhFQNRj4-uKsmlH4ERghSvynLvmjGTR-Ol8MmNwEyZuCQdwXol6ZnDm7wSiIlqN9lbOcT")
    @POST("fcm/send")
    Call<NotifyModel> sendNotifycation(@Body NotifyModel notify);
}