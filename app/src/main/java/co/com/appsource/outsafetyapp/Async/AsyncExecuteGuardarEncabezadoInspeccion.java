package co.com.appsource.outsafetyapp.Async;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import org.apache.http.protocol.HTTP;

import java.io.File;
import java.util.HashSet;

import co.com.appsource.outsafetyapp.ObservacionInspeccionFragment;
import co.com.appsource.outsafetyapp.SincronzarFragment;
import co.com.appsource.outsafetyapp.complete.GuardarInspeccionComplete;
import co.com.appsource.outsafetyapp.db_helper.Tables.Insp_Evidencia;
import co.com.appsource.outsafetyapp.db_helper.Tables.Insp_EvidenciaDataSource;
import co.com.appsource.outsafetyapp.util.GuardarInspeccionResult;
import co.com.appsource.outsafetyapp.util.OutSafetyUtils;
import co.com.appsource.outsafetyapp.util.RequestMethod;
import co.com.appsource.outsafetyapp.util.RestClient;

/**
 * Created by JANUS on 02/03/2016.
 */
public class AsyncExecuteGuardarEncabezadoInspeccion extends AsyncTask<String, Void, GuardarInspeccionResult> {
    private Context mContext;
    private GuardarInspeccionComplete mCallback;
    private SincronzarFragment objSincronzarFragment = null;
    private ObservacionInspeccionFragment objObservacionInspeccionFragment = null;

    String strMensajePersonalizado = "Guardando Inspección(es)!!...Puede tardar varios minutos";


    public AsyncExecuteGuardarEncabezadoInspeccion(android.support.v4.app.Fragment objFragment, Context context) {
        this.mContext = context;
        this.mCallback = (GuardarInspeccionComplete) objFragment;

        if (objFragment.getClass().toString().equals(OutSafetyUtils.CONS_CLASS_OBSERVACION)) {
            this.objObservacionInspeccionFragment = (ObservacionInspeccionFragment) objFragment;
            this.strMensajePersonalizado = "Guardando Inspección NO INTERRUMPIR!!";
        } else {
            this.objSincronzarFragment = (SincronzarFragment) objFragment;
            this.strMensajePersonalizado = "Guardando  inspección " +
                    ((SincronzarFragment) objFragment).contadorInspeccioes + " de " + ((SincronzarFragment) objFragment).lstInsp_InspeccionGuardar.size() + " inspecciones!!";
        }
    }

    @Override
    protected GuardarInspeccionResult doInBackground(String... params) {

        RestClient objRestClient;
        Boolean boolInspeccionGuardada = true;


        String strGuardarInspeccionRequest = params[0];
        String strModoUso = params[1];

        if (OutSafetyUtils.MODO_USO_OFFLINE == strModoUso) {
            if (this.objObservacionInspeccionFragment != null) {
                this.objObservacionInspeccionFragment.GuardarInspeccionOffline();

                return null;
            }
        }

        int outputIdInspeccionCampo = 0;
        String serviceResponse = "";
        try {
            objRestClient = new RestClient(String.format(OutSafetyUtils.CONS_URL_RESTFUL_SAVE_JSON));

            objRestClient.AddHeader(HTTP.UTF_8, "application/json");
            objRestClient.AddParamString(strGuardarInspeccionRequest);
            //objRestClient.AddParam("request", "|");
            objRestClient.Execute(RequestMethod.POST_STRING);

            serviceResponse = objRestClient.getResponse();
            outputIdInspeccionCampo = Integer.parseInt(objRestClient.getResponse().replace("\n", "").replace("\"", ""));

        } catch (Exception e) {
            e.printStackTrace();
        }

        GuardarInspeccionResult objGuardarInspeccionResult = new GuardarInspeccionResult();
        objGuardarInspeccionResult.setIntIdInspeccion(outputIdInspeccionCampo);

        if (outputIdInspeccionCampo > 0) {
            objGuardarInspeccionResult.setExito(true);
        } else {
            objGuardarInspeccionResult.setExito(false);
        }

        if (OutSafetyUtils.MODO_USO_OFFLINE.equals(strModoUso)) {

            objGuardarInspeccionResult.setExito(true);
            return objGuardarInspeccionResult;
        }

        if (params.length > 2) {
            Long idInspeccionLocal = Long.parseLong(params[2]);
            objGuardarInspeccionResult.setIntIdInspeccionLocal(idInspeccionLocal);
        }

        return objGuardarInspeccionResult;
    }

    @Override
    protected void onPreExecute() {

        if (objObservacionInspeccionFragment != null) {
            objObservacionInspeccionFragment.mProgress = new ProgressDialog(mContext);
            objObservacionInspeccionFragment.mProgress.setMessage(strMensajePersonalizado);
            objObservacionInspeccionFragment.mProgress.show();
        } else {
            objSincronzarFragment.mProgressUploadInspecciones = new ProgressDialog(mContext);
            objSincronzarFragment.mProgressUploadInspecciones.setMessage(strMensajePersonalizado);
            objSincronzarFragment.mProgressUploadInspecciones.show();
        }
    }

    @Override
    protected void onPostExecute(GuardarInspeccionResult result) {
        if (objSincronzarFragment != null) {
            objSincronzarFragment.mProgressUploadInspecciones.dismiss();
        }
        mCallback.onTaskComplete(result);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        // TODO Auto-generated method stub
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(GuardarInspeccionResult result) {
        // TODO Auto-generated method stub
        super.onCancelled(result);
    }

    @Override
    protected void onCancelled() {
        // TODO Auto-generated method stub
        super.onCancelled();
    }

}
