package py.com.softpoint.apiclient;

import py.com.softpoint.pojos.InvPoReceivingItem;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface InvPoReceivingItemApi {

    @POST("/RecepcionBE/api/itemreceiving")
    Call<Integer> createItem(@Body InvPoReceivingItem item);

}

