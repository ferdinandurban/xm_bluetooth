/*
 *
 * @author Ferdinand Urban
 * Copyright (C) 2016  Ferdinand Urban
 *
 *
 */

package cz.xmartcar.communication.rest.sso;

import org.apache.http.conn.ssl.X509HostnameVerifier;

import java.io.IOException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import cz.xmartcar.communication.rest.RestClient;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SSO extends RestClient {

    private static String mSSOUrlPath = "https://vcartestid1/xMartOnSSO/connect/";
    // exception for SSO server -- self-signed certificate
    private static String mHostname = "10.0.20.161";
    private static XMSSOInterface xmssoInterface;
    OkHttpClient okHttpClient = new OkHttpClient();

    HostnameVerifier hostNameVerifier = new X509HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            try {
                verifyHost(hostname);
                return true;
            } catch (SSLException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
            verifyHost(host);
        }

        @Override
        public void verify(String host, X509Certificate cert) throws SSLException {
            verifyHost(host);
        }

        @Override
        public void verify(String host, SSLSocket ssl) throws IOException {
            verifyHost(host);
        }

        private void verifyHost(String sourceHost) throws SSLException {
            if (!mHostname.equals(sourceHost)) {
                throw new SSLException("Hostname '10.0.20.161' was not verified");
            }
        }
    };


//    okHttpClient.setHostnameVerifier(hostNameVerifier);
//    OkClient okClient = new OkClient(okHttpClient);

//    RestAdapter restAdapter = new RestAdapter.Builder()
//            **.setClient(okClient)** //this is where u bind the httpClient
//            .build(); //make sure you specify endpoint, headerInterceptor etc ...

    public static XMSSOInterface getSSOService() {
        if (xmssoInterface == null) {

            OkHttpClient okClient = new OkHttpClient.Builder().addInterceptor(
                    new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Response response = chain.proceed(chain.request());
                            return response;
                        }
                    }).build();

            Retrofit client = new Retrofit.Builder()
                    .baseUrl(mSSOUrlPath)
                    .client(okClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            xmssoInterface = client.create(XMSSOInterface.class);
        }

        return xmssoInterface;
    }



}
