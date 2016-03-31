package co.com.appsource.outsafetyapp.Async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import co.com.appsource.outsafetyapp.complete.EmpresaComplete;
import co.com.appsource.outsafetyapp.custom_objects.RestfulResponse;
import co.com.appsource.outsafetyapp.db_helper.Tables.Empresa;
import co.com.appsource.outsafetyapp.db_helper.Tables.EmpresaDataSource;
import co.com.appsource.outsafetyapp.util.OutSafetyUtils;
import co.com.appsource.outsafetyapp.util.RequestMethod;
import co.com.appsource.outsafetyapp.util.RestClient;

/**
 * Created by Administrador on 30/03/2016.
 */
public class AsyncExecuteGetEmpresa extends AsyncTask<String, Void, List<Empresa>> {

    RestClient objRestClient;
    Gson gson;

    String strModoUso = "";
    String strMensajePreExecute = "Consultando Empresas!!...";
    public static final String OPERACION = "BCT";

    private Context mContext;
    ProgressDialog mProgress;
    private EmpresaComplete mCallback;


    public static final String SOAP_ACTION = "http://tempuri.org/CargarCentroTrabajo";
    public static final String OPERATION_NAME = "CargarCentroTrabajo";

    ///OJO: El name espace debe ser exactamente igual al namespace del WebService en el asmx.cs
    public static final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";

    public static final String SOAP_ADDRESS = OutSafetyUtils.CONS_SOAP_ADDRESS;


    public AsyncExecuteGetEmpresa(android.support.v4.app.Fragment objFragment, Context context, String strMensajePreExecute) {
        this.mContext = context;
        this.mCallback = (EmpresaComplete) objFragment;
        this.strMensajePreExecute = strMensajePreExecute;
    }

    public AsyncExecuteGetEmpresa(android.support.v4.app.Fragment objFragment, Context context) {
        this.mContext = context;
        this.mCallback = (EmpresaComplete) objFragment;
    }

    @Override
    protected List<Empresa> doInBackground(String... params) {

        strModoUso = params[0];

        List<Empresa> lstEmpresa = new ArrayList<Empresa>();

        if (OutSafetyUtils.MODO_USO_OFFLINE.equals(strModoUso)) {
            EmpresaDataSource objEmpresaDataSource = new EmpresaDataSource(mContext);
            objEmpresaDataSource.open();
            lstEmpresa = objEmpresaDataSource.GetAllEmpresas();
            objEmpresaDataSource.close();
            return lstEmpresa;
        }

        objRestClient = new RestClient(OutSafetyUtils.CONS_URL_RESTFUL_JSON_HAZARD_ID + OutSafetyUtils.CONS_JSON_GET_EMPRESAS_HAZARD_ID);
        RestfulResponse objRestfulResponse = null;

        try {
            objRestClient.Execute(RequestMethod.GET);
            gson = new Gson();
            //objRestfulResponse = gson.fromJson(objRestClient.getResponse(), RestfulResponse.class);
            lstEmpresa = gson.fromJson(objRestClient.getResponse(), new TypeToken<List<Empresa>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lstEmpresa;
    }

    @Override
    protected void onPreExecute() {
        mProgress = new ProgressDialog(mContext);
        mProgress.setMessage(strMensajePreExecute);
        mProgress.show();
    }

    @Override
    protected void onPostExecute(List<Empresa> result) {
        mProgress.dismiss();
        mCallback.onTaskComplete(result);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        // TODO Auto-generated method stub
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(List<Empresa> result) {
        // TODO Auto-generated method stub
        super.onCancelled(result);
    }

    @Override
    protected void onCancelled() {
        // TODO Auto-generated method stub
        super.onCancelled();
    }
}
