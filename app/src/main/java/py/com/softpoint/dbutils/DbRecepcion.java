package py.com.softpoint.dbutils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import py.com.softpoint.pojos.PayVendor;

/**
* @author Ever Leon
* Manager de Base de datos RecepcionApp.db
*/
public class DbRecepcion extends DbHelper{

    Context context;

    public DbRecepcion(@Nullable Context context) {
        super(context);
        this.context = context;
    }


    /**
    * Metodo para agregar un nuevo registro en la tabla pay_vendors
    *
    */
    public long add(Long identifier, Long orgId, Long unitId, String name,
                                 String code, String taxnumber, String identitynumber){

        long resp = 0;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

            try {
                cv.put("identifier", identifier);
                cv.put("orgid", orgId);
                cv.put("unitid", unitId);
                cv.put("name", name);
                cv.put("code", code);
                cv.put("taxnumber", taxnumber);
                cv.put("IdentityNumber", identitynumber);

                resp = db.insert(TABLE_PAY_VENDORS, null, cv);

            }catch (Exception e){
                e.toString();
            } finally {
                db.close();
            }

        return resp;
    }


    /**
     * Recupera todos los proveedores activos
     * @return
     */
    public ArrayList<PayVendor> getAll(){

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<PayVendor> resp = new ArrayList<>();
        Cursor cursor;

        cursor = db.rawQuery("select identifier, orgid, unitid, name, code, taxnumber, IdentityNumber" +
                " from "+ TABLE_PAY_VENDORS,null);

            try {
                if (cursor.moveToFirst()) {
                    PayVendor tmp;
                    do {
                        tmp = new PayVendor();
                        tmp.setIdentifier(cursor.getLong(0));
                        tmp.setOrgId(cursor.getLong(1));
                        tmp.setUnitId(cursor.getLong(2));
                        tmp.setName(cursor.getString(3));
                        tmp.setTaxNumber(cursor.getString(4));
                        tmp.setIdentityNumber(cursor.getString(5));

                        resp.add(tmp);

                    } while (cursor.moveToNext());
                }
            } catch (Exception e){
                e.toString();
            } finally {
                db.close();
            }

        return resp;
    }


    /**
    * Obtiene un registro del proveedor a partir del identificador
    * @param identifier
    * @return
    */
    public PayVendor get(Long identifier){

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        PayVendor resp = null;

        Cursor cursor;

            try {
                cursor = db.rawQuery("select identifier, orgid, unitid, name, code, taxnumber, IdentityNumber" +
                        " from " + TABLE_PAY_VENDORS + " where identifier = ?", new String[]{identifier + ""});

                if (cursor.moveToFirst()) {
                    resp = new PayVendor();
                    resp.setIdentifier(cursor.getLong(0));
                    resp.setOrgId(cursor.getLong(1));
                    resp.setUnitId(cursor.getLong(2));
                    resp.setName(cursor.getString(3));
                    resp.setTaxNumber(cursor.getString(4));
                    resp.setIdentityNumber(cursor.getString(5));
                }

            }catch (Exception e){
                e.toString();
            }finally {
                db.close();
            }

        return resp;
    }



}
