package py.com.softpoint.apiclient;

import java.util.List;

import py.com.softpoint.pojos.PayVendor;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PayVendorApi {

    @GET("/RecepcionBE/api/proveedor/all/{idSitio}")
    Call<List<PayVendor>> getProveedores(@Path("idSitio") Long idSitio);

}
