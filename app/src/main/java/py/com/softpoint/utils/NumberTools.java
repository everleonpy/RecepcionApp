package py.com.softpoint.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberTools {

    public static String nroFormat(Double amount) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.getDefault());
        formatter.applyPattern("###,###,###.##");
        String resp = formatter.format(amount).trim() ;
        return resp;
    }

}
