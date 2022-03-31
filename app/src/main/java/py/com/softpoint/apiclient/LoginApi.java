package py.com.softpoint.apiclient;


import py.com.softpoint.pojos.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LoginApi {

    @GET("/RecepcionBE/api/user/{userName}")
    Call<User> getUser(@Path("userName") String userName);

}

