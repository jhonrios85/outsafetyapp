package co.com.appsource.outsafetyapp.Async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.protocol.HTTP;

import co.com.appsource.outsafetyapp.SincronzarFragment;
import co.com.appsource.outsafetyapp.complete.GuardarColaboradoresComplete;
import co.com.appsource.outsafetyapp.util.GuardarColaboradoresResult;
import co.com.appsource.outsafetyapp.util.OutSafetyUtils;
import co.com.appsource.outsafetyapp.util.RequestMethod;
import co.com.appsource.outsafetyapp.util.RestClient;

/**
 * Created by JANUS on 04/03/2016.
 */
public class AsyncExecuteGuardarColaboradoresInspeccion extends AsyncTask<String, Void, GuardarColaboradoresResult> {
    private Context mContext;
    ProgressDialog mProgress;
    private GuardarColaboradoresComplete mCallback;

    String strMensajePersonalizado = "Guardando colaboradores de la inspección!!";


    public AsyncExecuteGuardarColaboradoresInspeccion(android.support.v4.app.Fragment objFragment, Context context) {
        this.mContext = context;
        this.mCallback = (GuardarColaboradoresComplete) objFragment;

        if (objFragment.getClass().toString().equals(OutSafetyUtils.CONS_CLASS_SINCRONIZAR)) {
            this.strMensajePersonalizado = "Guardando colaboradores para la inspección " +
                    ((SincronzarFragment) objFragment).contadorInspeccioes + " de " + ((SincronzarFragment) objFragment).lstInsp_InspeccionGuardar.size() + " inspecciones!!";
        }
    }

    @Override
    protected GuardarColaboradoresResult doInBackground(String... params) {

        RestClient objRestClient;
        Boolean boolEvidenciasGuardada = true;

        String strIdInspeccionCampo = params[0];
        String strByteArray = params[1];
        String strCedula = params[2];
        String strModoUso = params[3];


        String serviceResponse = "";
        try {
            objRestClient = new RestClient(String.format(OutSafetyUtils.CONS_URL_RESTFUL_SAVE_COLABORADORES_JSON));

            objRestClient.AddHeader(HTTP.UTF_8, "application/json");
            objRestClient.AddParamString(strIdInspeccionCampo + "|" + strByteArray + "|" + strCedula);
            objRestClient.Execute(RequestMethod.POST_STRING);
            serviceResponse = objRestClient.getResponse();

            boolEvidenciasGuardada = Boolean.parseBoolean(objRestClient.getResponse().replace("\n", "").replace("\"", ""));

        } catch (Exception e) {
            boolEvidenciasGuardada = false;
        }

        GuardarColaboradoresResult objGuardarColaboradorResult = new GuardarColaboradoresResult();
        objGuardarColaboradorResult.setIntIdInspeccion(Long.parseLong(strIdInspeccionCampo));
        objGuardarColaboradorResult.setExito(boolEvidenciasGuardada);


        if (OutSafetyUtils.MODO_USO_OFFLINE.equals(strModoUso)) {

            objGuardarColaboradorResult.setExito(true);
            return objGuardarColaboradorResult;
        }

        if (params.length > 4) {
            Long idInspeccionLocal = Long.parseLong(params[4]);
            objGuardarColaboradorResult.setIntIdInspeccionLocal(idInspeccionLocal);
        }

        return objGuardarColaboradorResult;
    }

    @Override
    protected void onPreExecute() {

        mProgress = new ProgressDialog(mContext);
        mProgress.setMessage(strMensajePersonalizado);
        mProgress.show();
    }

    @Override
    protected void onPostExecute(GuardarColaboradoresResult result) {
        mProgress.dismiss();
        mCallback.onTaskComplete(result);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        // TODO Auto-generated method stub
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(GuardarColaboradoresResult result) {
        // TODO Auto-generated method stub
        super.onCancelled(result);
    }

    @Override
    protected void onCancelled() {
        // TODO Auto-generated method stub
        super.onCancelled();
    }
}
