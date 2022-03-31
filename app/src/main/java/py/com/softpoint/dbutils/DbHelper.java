package py.com.softpoint.dbutils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "RecepcionApp.db";
    public static final String TABLE_PAY_VENDORS = "pay_vendors";

    public DbHelper(@Nullable Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Tabla Proveedores PAY_VENDORS
        db.execSQL("create table if not exists "+TABLE_PAY_VENDORS+" (" +
                "identifier integer primary key, " +
                "orgid integer not null, " +
                "unitid integer not null, " +
                "name text not null, " +
                "code text," +
                "taxnumber text not null," +
                "IdentityNumber text not null " +
                ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
