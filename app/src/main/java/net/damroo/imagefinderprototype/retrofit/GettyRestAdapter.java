package net.damroo.imagefinderprototype.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class GettyRestAdapter {

    protected Retrofit retrofit;
    protected GettyApiCalls gettyApiCalls;
    protected final String BASE_URL = "https://api.gettyimages.com:443";
    protected final String TOKEN = "4x3mqfykgft2uj2zynnw4b9w";


    public GettyRestAdapter() {
        Gson gson = new GsonBuilder()
                // no date-format needed so far!  .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                .create();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder() //
                .baseUrl(BASE_URL) //
                .client(client) //
                .addConverterFactory(GsonConverterFactory.create(gson)) //
                .build();
    }

    public Call<ImagesPage> getImageService(Map<String, String> options) {
        gettyApiCalls = retrofit.create(GettyApiCalls.class);
        return gettyApiCalls.getOrders(TOKEN, options);
    }

}