package com.example.sujaynaik.myapplication.util;

/**
 * Created by sujaynaik on 5/23/17.
 */

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class RestClient {

    private static String TAG = "Retrofit";

    private static final HttpLoggingInterceptor.Level LOG_LEVEL
            = Constant.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE;

    /**
     * @return REST Adapter for API's
     */
    private static Retrofit getRestAdapter() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(LOG_LEVEL);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        return new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public static MyApplicationApi getMyApplicationApi() {
        return getRestAdapter().create(MyApplicationApi.class);
    }

    /**
     * All API calls
     */
    public interface MyApplicationApi {
        //Register User
        /*@POST(Constant.API_REGISTER)
        Call<MyResponse> register(
                @Body Register register
        );

        @POST(Constant.API_LOGIN)
        Call<MyResponse> login(
                @Body Login login
        );*/
    }
}
