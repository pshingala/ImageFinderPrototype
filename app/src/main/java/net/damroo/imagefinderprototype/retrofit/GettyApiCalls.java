package net.damroo.imagefinderprototype.retrofit;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.QueryMap;

public interface GettyApiCalls {

    @GET("/v3/search/images/")
    Call<ImagesPage> getOrders(@Header("Api-Key") String token, @QueryMap Map<String, String> options
    );


}