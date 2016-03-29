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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import co.com.appsource.outsafetyapp.complete.ConfigurarInspeccionComplete;
import co.com.appsource.outsafetyapp.custom_objects.InputConfigurarInspeccion;
import co.com.appsource.outsafetyapp.custom_objects.RestfulResponse;
import co.com.appsource.outsafetyapp.db_helper.Tables.Habilidad;
import co.com.appsource.outsafetyapp.db_helper.Tables.HabilidadDataSource;
import co.com.appsource.outsafetyapp.util.OutSafetyUtils;
import co.com.appsource.outsafetyapp.util.RequestMethod;
import co.com.appsource.outsafetyapp.util.RestClient;

/**
 * Created by JANUS on 31/12/2015.
 */
public class AsyncExecuteGetHabilidades extends AsyncTask<String, Void, InputConfigurarInspeccion> {
    String intIdEmpresa = "";
    String strModoUso = "";
    String strCedula = "";
    String strSincornizando = "";

    public static final String OPERACION_HABILIDADES = "LTH";

    private Context mContext;
    ProgressDialog mProgress;
    private ConfigurarInspeccionComplete mCallback;


    public static final String SOAP_ACTION_HABILIDADES = "http://tempuri.org/cargarHabilidades";
    public static final String OPERATION_NAME_HABILIDADES = "cargarHabilidades";

    ///OJO: El name espace debe ser exactamente igual al namespace del WebService en el asmx.cs
    public static final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";

    public static final String SOAP_ADDRESS = OutSafetyUtils.CONS_SOAP_ADDRESS;


    public AsyncExecuteGetHabilidades(android.support.v4.app.Fragment objFragment, Context context) {
        this.mContext = context;
        this.mCallback = (ConfigurarInspeccionComplete) objFragment;
    }

    @Override
    protected InputConfigurarInspeccion doInBackground(String... params) {

        RestClient objRestClient;
        Gson gson;
        RestfulResponse objRestfulResponse = null;

        intIdEmpresa = params[0];
        strCedula = params[1];
        strModoUso = params[2];

        if (params.length > 3) {
            strSincornizando = params[3];
        }

        if (OutSafetyUtils.MODO_USO_OFFLINE.equals(strModoUso)) {
            HabilidadDataSource objHabilidadDataSource = new HabilidadDataSource(mContext);
            objHabilidadDataSource.open();
            List<Habilidad> lstHabilidad = objHabilidadDataSource.GetHabilidadesByCedulaAndCt(strCedula, intIdEmpresa);
            //lstHabilidad = objHabilidadDataSource.GetHabilidadesByCedula(strCedula);
            objHabilidadDataSource.close();

            InputConfigurarInspeccion newInputConfigurarInspeccion = new InputConfigurarInspeccion();
            newInputConfigurarInspeccion.setLstHabilidad(lstHabilidad);
            return newInputConfigurarInspeccion;
        }


        String errorMessage = "";


        //Habilidades

        List<Habilidad> lstHabilidad = new ArrayList<Habilidad>();
        objRestClient = new RestClient(String.format(OutSafetyUtils.CONS_URL_RESTFUL_JSON + OutSafetyUtils.CONS_JSON_GET_HABILIDADES, "'" + strCedula + "'", intIdEmpresa));

        try {
            objRestClient.Execute(RequestMethod.GET);
            gson = new Gson();
            objRestfulResponse = gson.fromJson(objRestClient.getResponse(), RestfulResponse.class);
            lstHabilidad = gson.fromJson(objRestfulResponse.value, new TypeToken<List<Habilidad>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }

        InputConfigurarInspeccion newInputConfigurarInspeccion = new InputConfigurarInspeccion();
        newInputConfigurarInspeccion.setIntIdEmpresa(intIdEmpresa);
        newInputConfigurarInspeccion.setLstHabilidad(lstHabilidad);

        return newInputConfigurarInspeccion;
    }


    @Override
    protected void onPreExecute() {
        mProgress = new ProgressDialog(mContext);
        mProgress.setMessage("Consultando Habilidades!!");
        mProgress.show();
    }

    @Override
    protected void onPostExecute(InputConfigurarInspeccion result) {
        mProgress.dismiss();
        mCallback.onTaskComplete(result);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        // TODO Auto-generated method stub
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(InputConfigurarInspeccion result) {
        // TODO Auto-generated method stub
        super.onCancelled(result);
    }

    @Override
    protected void onCancelled() {
        // TODO Auto-generated method stub
        super.onCancelled();
    }
}
