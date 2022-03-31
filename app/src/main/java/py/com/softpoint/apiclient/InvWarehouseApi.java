package py.com.softpoint.apiclient;


import java.util.List;

import py.com.softpoint.pojos.InvWarehouse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Obtener la lista de depositos
 */
public interface InvWarehouseApi {
    @GET("/RecepcionBE/api/deposito/{siteId}")
    Call<List<InvWarehouse>>  getAll(@Path("siteId") Long siteId);
}
