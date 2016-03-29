package co.com.appsource.outsafetyapp.Async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import co.com.appsource.outsafetyapp.ObservacionInspeccionFragment;
import co.com.appsource.outsafetyapp.SincronzarFragment;
import co.com.appsource.outsafetyapp.complete.GuardarInspeccionComplete;
import co.com.appsource.outsafetyapp.custom_objects.RestfulResponse;
import co.com.appsource.outsafetyapp.util.GuardarInspeccionResult;
import co.com.appsource.outsafetyapp.util.OutSafetyUtils;
import co.com.appsource.outsafetyapp.util.RequestMethod;
import co.com.appsource.outsafetyapp.util.RestClient;

/**
 * Created by JANUS on 17/12/2015.
 */
public class AsyncExecuteGuardarInspeccion extends AsyncTask<String, Void, GuardarInspeccionResult> {

    String dtInspecciones = "";
    String dtInspeccionesGuardadas = "";
    String dtFirmas = "";
    String dtEvidencias = "";
    String strModoUso = "";
    String strMensajePersonalizado = "Guardando Inspección(es)!!...Puede tardar varios minutos";


    private Context mContext;
    ProgressDialog mProgress;
    private GuardarInspeccionComplete mCallback;
    private ObservacionInspeccionFragment objObservacionInspeccionFragment;

/*    public static final String SOAP_ACTION = "http://tempuri.org/guardarInspeccion";
    public static final String OPERATION_NAME = "guardarInspeccion";

    ///OJO: El name espace debe ser exactamente igual al namespace del WebService en el asmx.cs
    public static final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";

    public static final String SOAP_ADDRESS = "http://186.155.246.211/ws/wsSincronizacion.asmx";*/

    public static final String SOAP_ACTION = "http://tempuri.org/GuardarInspeccion";
    public static final String OPERATION_NAME = "GuardarInspeccion";

    ///OJO: El name espace debe ser exactamente igual al namespace del WebService en el asmx.cs
    public static final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";

    public static final String SOAP_ADDRESS = OutSafetyUtils.CONS_SOAP_ADDRESS_WRAPPER;

    public AsyncExecuteGuardarInspeccion(android.support.v4.app.Fragment objFragment, Context context) {
        this.mContext = context;
        this.mCallback = (GuardarInspeccionComplete) objFragment;

        if (objFragment.getClass().toString().equals(OutSafetyUtils.CONS_CLASS_OBSERVACION)) {
            this.objObservacionInspeccionFragment = (ObservacionInspeccionFragment) objFragment;
            this.strMensajePersonalizado = "Guardando Inspección NO INTERRUMPIR!!";
        } else {
            this.strMensajePersonalizado = "Espere, puede tardar varios minutos. Se guardaran  " + ((SincronzarFragment) objFragment).cantidadInspecciones + " inspecciones!!";
        }

    }

    @Override
    protected GuardarInspeccionResult doInBackground(String... params) {

        RestClient objRestClient;
        Boolean boolInspeccionGuardada = true;
        Gson gson;

        dtInspecciones = params[0];
        dtInspeccionesGuardadas = params[1];
        dtFirmas = params[2];
        dtEvidencias = params[3];
        strModoUso = params[4];

        if (this.mCallback.getClass().toString().equals(OutSafetyUtils.CONS_CLASS_OBSERVACION)) {
            this.objObservacionInspeccionFragment.GuardarInspeccion();

            dtInspecciones = this.objObservacionInspeccionFragment.serialInsp_Inspeccion;
            dtInspeccionesGuardadas = this.objObservacionInspeccionFragment.serialInsp_Parametro;
            dtFirmas = this.objObservacionInspeccionFragment.serialInsp_Colaborador;
            dtEvidencias = this.objObservacionInspeccionFragment.serialInsp_Evidencia;
        }


        objRestClient = new RestClient(String.format(OutSafetyUtils.CONS_URL_RESTFUL_SAVE_JSON));
        String strInsp = "";
        try {

            strInsp = "{\n" +
                    "  \"ConcInspeccion\": \"1\",\n" +
                    "  \"idUsuario\": \"43758296\",\n" +
                    "  \"idCentroTrabajo\": \"1593\",\n" +
                    "  \"idRiesgo\": \"812\",\n" +
                    "  \"idInspeccion\": \"4742\",\n" +
                    "  \"Observacion\": \"lo q sea\",\n" +
                    "  \"intIdArea\": \"327\",\n" +
                    "  \"dtFechaProgramacion\": \"2016-02-10\",\n" +
                    "  \"dtFechaInicioInspeccion\": \"2016-02-10\",\n" +
                    "  \"dtFechaFinInspeccion\": \"2016-02-10\",\n" +
                    "  \"dtFechaRegistro\": \"2016-02-10\"\n" +
                    "}|[\n" +
                    "      {\n" +
                    "        \"ConcInspeccion\": \"1\",\n" +
                    "        \"idInspeccion\": \"4742\",\n" +
                    "        \"idParametro\": \"51239\",\n" +
                    "        \"bitCumple\": \"True\",\n" +
                    "        \"dtFechaProgramacion\": \"2016-02-10\",\n" +
                    "        \"dtFechaInicioInspeccion\": \"2016-02-10\",\n" +
                    "        \"dtFechaFinInspeccion\": \"2016-02-10\",\n" +
                    "        \"dtFechaRegistro\": \"2016-02-10\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"ConcInspeccion\": \"1\",\n" +
                    "        \"idInspeccion\": \"4742\",\n" +
                    "        \"idParametro\": \"51240\",\n" +
                    "        \"bitCumple\": \"True\",\n" +
                    "        \"dtFechaProgramacion\": \"2016-02-10\",\n" +
                    "        \"dtFechaInicioInspeccion\": \"2016-02-10\",\n" +
                    "        \"dtFechaFinInspeccion\": \"2016-02-10\",\n" +
                    "        \"dtFechaRegistro\": \"2016-02-10\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"ConcInspeccion\": \"1\",\n" +
                    "        \"idInspeccion\": \"4742\",\n" +
                    "        \"idParametro\": \"51241\",\n" +
                    "        \"bitCumple\": \"False\",\n" +
                    "        \"dtFechaProgramacion\": \"2016-02-10\",\n" +
                    "        \"dtFechaInicioInspeccion\": \"2016-02-10\",\n" +
                    "        \"dtFechaFinInspeccion\": \"2016-02-10\",\n" +
                    "        \"dtFechaRegistro\": \"2016-02-10\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"ConcInspeccion\": \"1\",\n" +
                    "        \"idInspeccion\": \"4742\",\n" +
                    "        \"idParametro\": \"51242\",\n" +
                    "        \"bitCumple\": \"True\",\n" +
                    "        \"dtFechaProgramacion\": \"2016-02-10\",\n" +
                    "        \"dtFechaInicioInspeccion\": \"2016-02-10\",\n" +
                    "        \"dtFechaFinInspeccion\": \"2016-02-10\",\n" +
                    "        \"dtFechaRegistro\": \"2016-02-10\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"ConcInspeccion\": \"1\",\n" +
                    "        \"idInspeccion\": \"4742\",\n" +
                    "        \"idParametro\": \"51243\",\n" +
                    "        \"bitCumple\": \"True\",\n" +
                    "        \"dtFechaProgramacion\": \"2016-02-10\",\n" +
                    "        \"dtFechaInicioInspeccion\": \"2016-02-10\",\n" +
                    "        \"dtFechaFinInspeccion\": \"2016-02-10\",\n" +
                    "        \"dtFechaRegistro\": \"2016-02-10\"\n" +
                    "      }\n" +
                    "    ]";

            objRestClient.AddHeader(HTTP.UTF_8, "application/json");
            objRestClient.AddParamString(strInsp);
            //objRestClient.AddParam("request", "|");
            objRestClient.Execute(RequestMethod.POST_STRING);


            String responseddddd = objRestClient.getResponse();


        } catch (Exception e) {
            e.printStackTrace();
        }

        Long intIdInspeccion;
        GuardarInspeccionResult objGuardarInspeccionResult = new GuardarInspeccionResult();

        if (params.length > 5) {
            intIdInspeccion = Long.parseLong(params[5]);
            objGuardarInspeccionResult.setIntIdInspeccion(intIdInspeccion);
        }


        if (OutSafetyUtils.MODO_USO_OFFLINE.equals(strModoUso)) {

            objGuardarInspeccionResult.setExito(true);
            return objGuardarInspeccionResult;
        }

        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);
        request.addProperty("strDtInspecciones", dtInspecciones);
        request.addProperty("strDtInspeccionesGuardadas", dtInspeccionesGuardadas);
        request.addProperty("strDtFirmas", dtFirmas);
        request.addProperty("strDtEvidencias", dtEvidencias);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
        SoapObject response = null;

        String errorMessage = "";

        try {
            httpTransport.call(SOAP_ACTION, envelope);
            response = (SoapObject) envelope.bodyIn;
        } catch (Exception exception) {
            errorMessage = exception.toString();
            objGuardarInspeccionResult.setExito(false);
        }

        SoapObject responseSoap = (SoapObject) response;


        if (responseSoap.getProperty(0).toString().equals("True")) {
            objGuardarInspeccionResult.setExito(true);
        } else {
            objGuardarInspeccionResult.setExito(false);
        }

        return objGuardarInspeccionResult;
    }

    @Override
    protected void onPreExecute() {

        mProgress = new ProgressDialog(mContext);
        mProgress.setMessage(strMensajePersonalizado);
        mProgress.show();
    }

    @Override
    protected void onPostExecute(GuardarInspeccionResult result) {
        mProgress.dismiss();
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
