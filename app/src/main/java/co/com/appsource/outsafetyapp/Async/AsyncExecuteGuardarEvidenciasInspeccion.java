package co.com.appsource.outsafetyapp.Async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.protocol.HTTP;

import co.com.appsource.outsafetyapp.ObservacionInspeccionFragment;
import co.com.appsource.outsafetyapp.SincronzarFragment;
import co.com.appsource.outsafetyapp.complete.GuardarEvidenciaComplete;
import co.com.appsource.outsafetyapp.complete.GuardarInspeccionComplete;
import co.com.appsource.outsafetyapp.util.GuardarEvidenciasResult;
import co.com.appsource.outsafetyapp.util.OutSafetyUtils;
import co.com.appsource.outsafetyapp.util.RequestMethod;
import co.com.appsource.outsafetyapp.util.RestClient;

/**
 * Created by JANUS on 03/03/2016.
 */
public class AsyncExecuteGuardarEvidenciasInspeccion extends AsyncTask<String, Void, GuardarEvidenciasResult> {
    private Context mContext;
    ProgressDialog mProgress;
    private GuardarEvidenciaComplete mCallback;

    String strMensajePersonalizado = "Guardando evidencias de la inspección!!";


    public AsyncExecuteGuardarEvidenciasInspeccion(android.support.v4.app.Fragment objFragment, Context context) {
        this.mContext = context;
        this.mCallback = (GuardarEvidenciaComplete) objFragment;

        if (objFragment.getClass().toString().equals(OutSafetyUtils.CONS_CLASS_SINCRONIZAR)) {
            this.strMensajePersonalizado = "Guardando evidencia " + ((SincronzarFragment) objFragment).contadorEvidencias + " de " + ((SincronzarFragment) objFragment).lstInsp_InspeccionGuardar.size() + " evidencias.\nPara la inspección " +
                    ((SincronzarFragment) objFragment).contadorInspeccioes + " de " + ((SincronzarFragment) objFragment).lstInsp_InspeccionGuardar.size() + " inspecciones!!";
        } else {
            strMensajePersonalizado = "Guardando evidencia " + ((ObservacionInspeccionFragment) objFragment).contadorEvidencias + " de " + ((ObservacionInspeccionFragment) objFragment).lstEvidencias.size() + " evidencias!!";
        }
    }

    @Override
    protected GuardarEvidenciasResult doInBackground(String... params) {

        RestClient objRestClient;
        Boolean boolEvidenciasGuardada = true;

        String strIdInspeccionCampo = params[0];
        String strByteArray = params[1];
        String strModoUso = params[2];

        String serviceResponse = "";
        try {
            objRestClient = new RestClient(String.format(OutSafetyUtils.CONS_URL_RESTFUL_SAVE_ENVIDENCIAS_JSON));

            objRestClient.AddHeader(HTTP.UTF_8, "application/json");
            objRestClient.AddParamString(strIdInspeccionCampo + "|" + strByteArray);
            objRestClient.Execute(RequestMethod.POST_STRING);
            serviceResponse = objRestClient.getResponse();

            boolEvidenciasGuardada = Boolean.parseBoolean(objRestClient.getResponse().replace("\n", "").replace("\"", ""));

        } catch (Exception e) {
            boolEvidenciasGuardada = false;
        }

        GuardarEvidenciasResult objGuardarEvidenciasResult = new GuardarEvidenciasResult();
        objGuardarEvidenciasResult.setIntIdInspeccion(Long.parseLong(strIdInspeccionCampo));
        objGuardarEvidenciasResult.setExito(boolEvidenciasGuardada);


        if (OutSafetyUtils.MODO_USO_OFFLINE.equals(strModoUso)) {

            objGuardarEvidenciasResult.setExito(true);
            return objGuardarEvidenciasResult;
        }

        if (params.length > 3) {
            Long idInspeccionLocal = Long.parseLong(params[3]);
            objGuardarEvidenciasResult.setIntIdInspeccionLocal(idInspeccionLocal);
        }
        return objGuardarEvidenciasResult;
    }

    @Override
    protected void onPreExecute() {

        mProgress = new ProgressDialog(mContext);
        mProgress.setMessage(strMensajePersonalizado);
        mProgress.show();
    }

    @Override
    protected void onPostExecute(GuardarEvidenciasResult result) {
        mProgress.dismiss();
        mCallback.onTaskComplete(result);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        // TODO Auto-generated method stub
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(GuardarEvidenciasResult result) {
        // TODO Auto-generated method stub
        super.onCancelled(result);
    }

    @Override
    protected void onCancelled() {
        // TODO Auto-generated method stub
        super.onCancelled();
    }
}
