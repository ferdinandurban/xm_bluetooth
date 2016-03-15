
package cz.xmartcar.communication.rest;

import java.io.IOException;

import cz.xmartcar.communication.base.XMConstants;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RestClient {

    private static XMRestApiInterface xmRestApiInterface;

    // TODO change to real URL
    private static String baseUrl = XMConstants.REST_SANDBOX_ENDPOINT;

    public static XMRestApiInterface getClient(String url) {
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
                    .baseUrl(url)
                    .client(okClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            xmRestApiInterface = client.create(XMRestApiInterface.class);
        }
        return xmRestApiInterface;
    }



}