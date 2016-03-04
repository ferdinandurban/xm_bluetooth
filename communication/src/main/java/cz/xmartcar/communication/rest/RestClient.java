
package cz.xmartcar.communication.rest;

import java.io.IOException;

import cz.xmartcar.communication.Model.RestResults;
import cz.xmartcar.communication.Model.XMData;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public class RestClient {

    private static XMRestApiInterface xmRestApiInterface;

    //TODO change to real URL
    private static String baseUrl = "https://api.github.com" ;

    public static XMRestApiInterface getClient() {
        if (xmRestApiInterface == null) {

            OkHttpClient okClient = new OkHttpClient.Builder().addInterceptor(
                new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response response = chain.proceed(chain.request());
                    return response;
                }
            }).build();

            Retrofit client = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            xmRestApiInterface = client.create(XMRestApiInterface.class);
        }
        return xmRestApiInterface;
    }

    public interface XMRestApiInterface {

        @Headers("User-Agent: XmartCarApp")
        @GET("/search/users")
        Call<RestResults> getUsersNamedTom(@Query("q") String name);

        @POST("/user/create")
        Call<XMData> createUser(@Body String name, @Body String email);

        @PUT("/user/{id}/update")
        Call<XMData> updateUser(@Path("id") String id , @Body XMData user);
    }

}