package py.com.softpoint.apiclient;

import py.com.softpoint.pojos.InvMaterial;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface InvMaterialApi {
    @GET("/RecepcionBE/api/articulo/{txtCode}")
    Call<InvMaterial> getProducto(@Path("txtCode") String txtCode);
}
