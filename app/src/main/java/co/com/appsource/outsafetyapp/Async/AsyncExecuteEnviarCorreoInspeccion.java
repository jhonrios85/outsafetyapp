package co.com.appsource.outsafetyapp.Async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import co.com.appsource.outsafetyapp.complete.EnviarMailComplete;
import co.com.appsource.outsafetyapp.custom_objects.RestfulResponse;
import co.com.appsource.outsafetyapp.util.OutSafetyUtils;
import co.com.appsource.outsafetyapp.util.RequestMethod;
import co.com.appsource.outsafetyapp.util.RestClient;

/**
 * Created by Administrador on 31/03/2016.
 */
public class AsyncExecuteEnviarCorreoInspeccion extends AsyncTask<String, Void, Boolean> {

    RestClient objRestClient;
    Gson gson;

    String strModoUso = "";
    String strMensajePreExecute = "Enviando Mail!!...";

    private Context mContext;
    ProgressDialog mProgress;
    private EnviarMailComplete mCallback;

    public AsyncExecuteEnviarCorreoInspeccion(android.support.v4.app.Fragment objFragment, Context context, String strMensajePreExecute) {
        this.mContext = context;
        this.mCallback = (EnviarMailComplete) objFragment;
        this.strMensajePreExecute = strMensajePreExecute;
    }

    public AsyncExecuteEnviarCorreoInspeccion(android.support.v4.app.Fragment objFragment, Context context) {
        this.mContext = context;
        this.mCallback = (EnviarMailComplete) objFragment;
    }

    @Override
    protected Boolean doInBackground(String... params) {

        strModoUso = params[1];
        String intIdInspeccion = params[0];

        Boolean esEnviado = false;

        if (OutSafetyUtils.MODO_USO_OFFLINE.equals(strModoUso)) {
            return true;
        }

        objRestClient = new RestClient(String.format(OutSafetyUtils.CONS_URL_RESTFUL_SAVE_ENVIAR_MAIL, intIdInspeccion));
        RestfulResponse objRestfulResponse = null;

        try {
            objRestClient.Execute(RequestMethod.GET);
            esEnviado = true;
        } catch (Exception e) {
            esEnviado = false;
            e.printStackTrace();
        }

        return esEnviado;
    }

    @Override
    protected void onPreExecute() {
        mProgress = new ProgressDialog(mContext);
        mProgress.setMessage(strMensajePreExecute);
        mProgress.show();
    }

    @Override
    protected void onPostExecute(Boolean result) {
        mProgress.dismiss();
        mCallback.onTaskComplete(result);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        // TODO Auto-generated method stub
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(Boolean result) {
        // TODO Auto-generated method stub
        super.onCancelled(result);
    }

    @Override
    protected void onCancelled() {
        // TODO Auto-generated method stub
        super.onCancelled();
    }
}
