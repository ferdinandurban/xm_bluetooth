
package cz.xmartcar.communication.rest.sso;

import cz.xmartcar.communication.model.SSOResults;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface XMSsoApi {
    @Headers("Authorization: Basic dGVzdGNsaWVudDoyMUI1Rjc5OC1CRTU1LTQyQkMtOEFBOC0wMDI1QjkwM0RDM0I=\n" +
            "Content-Type: application/x-www-form-urlencoded")
    @POST("token")
    Call<SSOResults> getToken(@Body String data);


    @Headers("Content-Type: application/json")
    @POST("token")
    Call<SSOResults> register(@Body String data);

}
