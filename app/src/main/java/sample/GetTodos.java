package sample;

/**
 * Created by Android on 5/5/2017.
 */

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetTodos {

    @GET("/schoolapi/api/App/GetUserDetails?UserName=vinoth&Password=vinoth1")
    Call<Ds> all();


}