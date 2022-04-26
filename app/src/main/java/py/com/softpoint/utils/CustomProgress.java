package py.com.softpoint.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import py.com.softpoint.R;

public class CustomProgress {

    public static CustomProgress customProgress = null;
    private ProgressDialog mProgressDialog;

    public static CustomProgress getInstance()
    {
        if( customProgress == null )
            {
                customProgress = new CustomProgress();
            }
        return customProgress;
    }


    /**
    * Mostrar un cuadro de dialogo progresivo
    * @param context
    * @param message
    * @param cancelable
    */
    public void showPB(Context context, String message, boolean cancelable)
    {
        mProgressDialog = new ProgressDialog(context);
        //mProgressDialog.setMax(100);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage(""+message);
        mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        // show it
        mProgressDialog.show();

    }

    /**
    * Ocultar Mensaje
    */
    public void hideDialog()
    {
        if( mProgressDialog != null )
        {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}
