package py.com.softpoint.apiclient;

import py.com.softpoint.pojos.InvReceiving;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface InvReceivingApi {

    @GET("/RecepcionBE/api/recepcion/{idReceiving}")
    Call<InvReceiving> getReceiving(@Path("idReceiving") Long idReceiving);

    @POST("/RecepcionBE/api/recepcion/create")
    Call<Long> createReceiving(@Body InvReceiving data);
}
