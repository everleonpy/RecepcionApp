package py.com.softpoint.apiclient;

import java.util.List;

import py.com.softpoint.pojos.InvPoReceivingItem;
import py.com.softpoint.pojos.InvPoReceivingItemQTY;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
* http://192.168.20.1:8080/RecepcionBE/api/itemreceiving/qtyitem/1/55211156/58755
* @author eleon
*/
public interface InvPoReceivingItemApi
{

    @GET("/RecepcionBE/api/itemreceiving/items/{receivingId}/{purchOrderId}")
    Call<List<InvPoReceivingItem>> getItems(@Path("receivingId") Long receivingId,
                                            @Path("purchOrderId") Long purchOrderId);


    @GET("/RecepcionBE/api/itemreceiving/qtyitem/{itemUomId}/{invMatrialId}/{purchOrderId}")
    Call<InvPoReceivingItemQTY>  getQtyItemProduct(@Path("itemUomId") Long itemUomId,
                                                   @Path("invMatrialId") Long invMatrialId,
                                                   @Path("purchOrderId") Long purchOrderId);

    @POST("/RecepcionBE/api/itemreceiving")
    Call<Integer> createItem(@Body InvPoReceivingItem item);

    @DELETE("/RecepcionBE/api/itemreceiving/{id}")
    Call<String> deleteItem(@Path("id") Long id);

}

