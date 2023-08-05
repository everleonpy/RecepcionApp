package py.com.softpoint.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Cliente de Conexion a las Apis
 */
public class Cliente {

    private static Retrofit retrofit = null;
    private static Gson gson = null;

    public static Retrofit getClient(String base_url)
    {

        HttpLoggingInterceptor loggin = new HttpLoggingInterceptor();
        loggin.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Se setean los parametros de coneccion via http y timeout de coneccion
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(8 , TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS);

        httpClient.addInterceptor(loggin);

            if ( retrofit == null)
            {
                gson = new GsonBuilder()
                        .setLenient()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                        .create();

                retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient.build()) // <-- usamos el log level
                    .build();

            }

        return retrofit ;
    }

}
