package py.com.softpoint.utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Cliente {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String base_url)
    {

        HttpLoggingInterceptor loggin = new HttpLoggingInterceptor();
        loggin.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Se setean los parametros de coneccion via http y timeout de coneccion
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(10 , TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS);

        httpClient.addInterceptor(loggin);

            if ( retrofit == null)
            {
                retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build()) // <-- usamos el log level
                    .build();

            }

        return retrofit ;
    }

}
