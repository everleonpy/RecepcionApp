package py.com.softpoint.apiclient;

import java.util.List;

import py.com.softpoint.pojos.InvUnitOfMeasure;
import retrofit2.Call;
import retrofit2.http.GET;

public interface InvUnitOfMeasureApi {

    @GET("/RecepcionBE/api/um/")
    Call<List<InvUnitOfMeasure>> getAll();

}
