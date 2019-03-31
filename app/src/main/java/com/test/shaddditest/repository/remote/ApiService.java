package com.test.shaddditest.repository.remote;

import com.test.shaddditest.json.UserResponseJson;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("api/")
    Call<UserResponseJson> getUsers(
            @Query("results") int size
    );
}
