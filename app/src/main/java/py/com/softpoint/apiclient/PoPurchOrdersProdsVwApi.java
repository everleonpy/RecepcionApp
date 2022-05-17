package py.com.softpoint.apiclient;

import java.util.List;

import py.com.softpoint.pojos.PoPurchOrdersProdsVw;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Api para recuperar los items a ser recepcionados
 */
public interface PoPurchOrdersProdsVwApi {
    @GET("/RecepcionBE/api/itemsoc/get/{nroOC}")
    Call<List<PoPurchOrdersProdsVw>>  getItemsOC(@Path("nroOC") Long nroOC);


    @GET("/RecepcionBE/api/itemsoc/get/{orderId}/{itemId}")
    Call<PoPurchOrdersProdsVw>  getItemOc(@Path("orderId") Long orderId,@Path("itemId") Long itemId);
}
