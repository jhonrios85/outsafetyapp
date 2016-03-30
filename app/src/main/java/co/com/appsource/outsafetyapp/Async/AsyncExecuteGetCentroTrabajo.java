package co.com.appsource.outsafetyapp.Async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

import co.com.appsource.outsafetyapp.complete.CentroTrabajoComplete;
import co.com.appsource.outsafetyapp.custom_objects.RestfulResponse;
import co.com.appsource.outsafetyapp.db_helper.Tables.CentroTrabajo;
import co.com.appsource.outsafetyapp.db_helper.Tables.CentroTrabajoDataSource;
import co.com.appsource.outsafetyapp.db_helper.Tables.Inspeccion;
import co.com.appsource.outsafetyapp.util.OutSafetyUtils;
import co.com.appsource.outsafetyapp.util.RequestMethod;
import co.com.appsource.outsafetyapp.util.RestClient;

/**
 * Created by JANUS on 15/12/2015.
 */
public class AsyncExecuteGetCentroTrabajo extends AsyncTask<String, Void, List<CentroTrabajo>> {

    RestClient objRestClient;
    Gson gson;

    String strUsuario = "";
    String strModoUso = "";
    String strMensajePreExecute = "Consultando Centros de Trabajo!!...";
    public static final String OPERACION = "BCT";

    private Context mContext;
    ProgressDialog mProgress;
    private CentroTrabajoComplete mCallback;


    public static final String SOAP_ACTION = "http://tempuri.org/CargarCentroTrabajo";
    public static final String OPERATION_NAME = "CargarCentroTrabajo";

    ///OJO: El name espace debe ser exactamente igual al namespace del WebService en el asmx.cs
    public static final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";

    public static final String SOAP_ADDRESS = OutSafetyUtils.CONS_SOAP_ADDRESS;


    public AsyncExecuteGetCentroTrabajo(android.support.v4.app.Fragment objFragment, Context context, String strMensajePreExecute) {
        this.mContext = context;
        this.mCallback = (CentroTrabajoComplete) objFragment;
        this.strMensajePreExecute = strMensajePreExecute;
    }

    public AsyncExecuteGetCentroTrabajo(android.support.v4.app.Fragment objFragment, Context context) {
        this.mContext = context;
        this.mCallback = (CentroTrabajoComplete) objFragment;
    }

    @Override
    protected List<CentroTrabajo> doInBackground(String... params) {

        strUsuario = params[0];
        strModoUso = params[1];

        if (OutSafetyUtils.MODO_USO_OFFLINE.equals(strModoUso)) {
            CentroTrabajoDataSource objCentroTrabajoDataSource = new CentroTrabajoDataSource(mContext);
            objCentroTrabajoDataSource.open();
            List<CentroTrabajo> lstCentroTrabajo = objCentroTrabajoDataSource.GetAllCentrosTrabajo();
            objCentroTrabajoDataSource.close();
            return lstCentroTrabajo;
        }

        List<CentroTrabajo> lstCentroTrabajo = new ArrayList<CentroTrabajo>();
        objRestClient = new RestClient(String.format(OutSafetyUtils.CONS_URL_RESTFUL_JSON + OutSafetyUtils.CONS_JSON_GET_CENTROS_TRABAJO, "'" + strUsuario + "'"));
        RestfulResponse objRestfulResponse = null;

        try {
            objRestClient.Execute(RequestMethod.GET);
            gson = new Gson();
            objRestfulResponse = gson.fromJson(objRestClient.getResponse(), RestfulResponse.class);
            lstCentroTrabajo = gson.fromJson(objRestfulResponse.value, new TypeToken<List<CentroTrabajo>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lstCentroTrabajo;
    }

    @Override
    protected void onPreExecute() {
        mProgress = new ProgressDialog(mContext);
        mProgress.setMessage(strMensajePreExecute);
        mProgress.show();
    }

    @Override
    protected void onPostExecute(List<CentroTrabajo> result) {
        mProgress.dismiss();
        mCallback.onTaskComplete(result);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        // TODO Auto-generated method stub
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(List<CentroTrabajo> result) {
        // TODO Auto-generated method stub
        super.onCancelled(result);
    }

    @Override
    protected void onCancelled() {
        // TODO Auto-generated method stub
        super.onCancelled();
    }
}
