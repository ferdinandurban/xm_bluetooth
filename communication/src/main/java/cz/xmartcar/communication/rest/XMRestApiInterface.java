
package cz.xmartcar.communication.rest;

import cz.xmartcar.communication.model.RestResults;
import cz.xmartcar.communication.model.XMData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface XMRestApiInterface {

    @GET("/search/users")
    Call<RestResults> getUsersNamed(@Query("q") String name);

    @POST("/user/create")
    Call<XMData> createUser(@Body String name, @Body String email);

    @PUT("/user/{id}/update")
    Call<XMData> updateUser(@Path("id") String id , @Body XMData user);
}