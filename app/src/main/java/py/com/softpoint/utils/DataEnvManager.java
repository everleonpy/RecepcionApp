package py.com.softpoint.utils;


import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Clase encargada de manipular los archivos properties de configuracion
 */
public class DataEnvManager {


    public static Properties getEnv(Context context)
    {
        Properties resp = new Properties();
            try {
                AssetManager assetManager = context.getAssets();
                InputStream inputStream = assetManager.open("env.properties");
                resp.load(inputStream);
            }catch (IOException io){ io.printStackTrace(); }
        return resp;
    }

}
