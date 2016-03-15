/*
 *
 * @author Ferdinand Urban
 * Copyright (C) 2016  Ferdinand Urban
 *
 *
 */

package cz.xmartcar.communication.rest.sso;

import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cz.xmartcar.communication.base.XMConstants;
import cz.xmartcar.communication.model.SSOResults;
import cz.xmartcar.communication.model.XMUser;
import cz.xmartcar.communication.rest.RestClient;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SSOService extends RestClient implements Callback<SSOResults> {

    private static final Logger log = LoggerFactory.getLogger(SSOService.class);

    private static String mSSOUrlPath = XMConstants.PLATFORM_SSO_ENDPOINT;

    // exception for SSOService server -- self-signed certificate
    private static String mHostname = "vcartestid1";
    private static Retrofit mRetrofit;
    private static XMSsoApi xmSsoApi;

    OkHttpClient okHttpClient = new OkHttpClient();

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public SSOService(){
//        OkHttpClient okClient = getUnsafeOkHttpClient().newBuilder().addInterceptor(
//                new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        okhttp3.Response response = chain.proceed(chain.request());
//                        return response;
//                    }
//                }).build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(mSSOUrlPath)
//                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        xmSsoApi = mRetrofit.create(XMSsoApi.class);
    }

    public void registerUser(XMUser user){
        Gson gson = new Gson();
        String data = gson.toJson(user);

        Call<SSOResults> call = xmSsoApi.register(data);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<SSOResults> call, Response<SSOResults> response) {
        log.debug(response.toString());

        if(response.isSuccess()){
            SSOResults ssoResults = response.body();
        } else {
            log.error("SSO Response Error " + response.errorBody().toString());
        }
    }

    @Override
    public void onFailure(Call<SSOResults> call, Throwable t) {
        log.error("SSO Error - " + t.getMessage());
    }


//    public static XMSsoApi getSSOService() {
//        if (XMSsoApi == null) {
//
//            OkHttpClient okClient = getUnsafeOkHttpClient().newBuilder().addInterceptor(
//                    new Interceptor() {
//                        @Override
//                        public Response intercept(Chain chain) throws IOException {
//                            Response response = chain.proceed(chain.request());
//                            return response;
//                        }
//                    }).build();
//
//            Retrofit client = new Retrofit.Builder()
//                    .baseUrl(mSSOUrlPath)
//                    .client(okClient)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//            XMSsoApi = client.create(XMSsoApi.class);
//        }
//
//        return XMSsoApi;
//    }



}
