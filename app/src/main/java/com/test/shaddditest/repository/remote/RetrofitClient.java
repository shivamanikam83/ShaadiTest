package com.test.shaddditest.repository.remote;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.test.shaddditest.json.UserResponseJson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {


    private static final String BASE_URL = "https://randomuser.me/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    private RetrofitClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance(){
        if(mInstance == null){
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public ApiService getApi(){
        return retrofit.create(ApiService.class);
    }


    public MutableLiveData<UserResponseJson> getUsers() {
        final MutableLiveData<UserResponseJson> data = new MutableLiveData<>();

        getApi().getUsers().enqueue(new Callback<UserResponseJson>() {
            @Override
            public void onResponse(Call<UserResponseJson> call, Response<UserResponseJson> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<UserResponseJson> call, Throwable t) {
                Log.d("ViewMode", ""+t.toString());
            }
        });

        return data;
    }
}