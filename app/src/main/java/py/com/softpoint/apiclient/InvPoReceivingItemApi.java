package py.com.softpoint.apiclient;

import java.util.List;

import py.com.softpoint.pojos.InvPoReceivingItem;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface InvPoReceivingItemApi {

    @GET("/RecepcionBE/api/itemreceiving/items/{receivingId}/{purchOrderId}")
    Call<List<InvPoReceivingItem>> getItems(@Path("receivingId") Long receivingId, @Path("purchOrderId") Long purchOrderId);

    @POST("/RecepcionBE/api/itemreceiving")
    Call<Integer> createItem(@Body InvPoReceivingItem item);

    @DELETE("/RecepcionBE/api/itemreceiving/{id}")
    Call<String> deleteItem(@Path("id") Long id);

}

