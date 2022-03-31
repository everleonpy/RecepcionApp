package py.com.softpoint.apiclient;

import java.util.List;

import py.com.softpoint.pojos.PoPurchaseOrdersVw;
import retrofit2.Call;
import retrofit2.http.GET;

public interface OrdenCompras {

    @GET("/RecepcionBE/api/oc/all/{nroOC}}/{siteId}/{fechaDesde}/{fechaHasta}")
    Call<List<PoPurchaseOrdersVw>> getOC(Long nroOC, Long siteID,String fechaDesde, String fechaHasta);
}
