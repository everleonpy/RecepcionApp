package py.com.softpoint.apiclient;

import java.util.List;

import py.com.softpoint.pojos.ListaOCParam;
import py.com.softpoint.pojos.PoPurchaseOrdersVw;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PoPurchaseOrdersWSApi {

    @GET("/RecepcionBE/api/oc/all/{vendorId}}/{siteId}/{fechaDesde}/{fechaHasta}")
    Call<List<PoPurchaseOrdersVw>> getOCPendientes(@Path("vendorId") Long vendorId, @Path("siteId") Long siteID,
                                                   @Path("fechaDesde") String fechaDesde, @Path("fechaHasta") String fechaHasta);

    @GET("/RecepcionBE/api/oc/update/{orderId}/{receivingId}")
    Call<Void> updateReceivingId(@Path("orderId") Long orderId, @Path("receivingId") Long receivingId);

    @POST("/RecepcionBE/api/oc/getall")
    Call<List<PoPurchaseOrdersVw>> postOCPendites(@Body ListaOCParam para);
}
