package co.com.appsource.outsafetyapp.Async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import co.com.appsource.outsafetyapp.complete.ConfigurarInspeccionComplete;
import co.com.appsource.outsafetyapp.custom_objects.InputConfigurarInspeccion;
import co.com.appsource.outsafetyapp.custom_objects.RestfulResponse;
import co.com.appsource.outsafetyapp.db_helper.Tables.Area;
import co.com.appsource.outsafetyapp.db_helper.Tables.AreaDataSource;
import co.com.appsource.outsafetyapp.db_helper.Tables.Habilidad;
import co.com.appsource.outsafetyapp.db_helper.Tables.Inspeccion;
import co.com.appsource.outsafetyapp.db_helper.Tables.InspeccionDataSource;
import co.com.appsource.outsafetyapp.db_helper.Tables.Parametro;
import co.com.appsource.outsafetyapp.db_helper.Tables.ParametroDataSource;
import co.com.appsource.outsafetyapp.db_helper.Tables.Persona;
import co.com.appsource.outsafetyapp.db_helper.Tables.PersonaDataSource;
import co.com.appsource.outsafetyapp.db_helper.Tables.Riesgo;
import co.com.appsource.outsafetyapp.db_helper.Tables.RiesgoDataSource;
import co.com.appsource.outsafetyapp.util.OutSafetyUtils;
import co.com.appsource.outsafetyapp.util.RequestMethod;
import co.com.appsource.outsafetyapp.util.RestClient;

/**
 * Created by JANUS on 15/12/2015.
 */
public class AsyncExecuteGetConfigurarInspeccion extends AsyncTask<String, Void, InputConfigurarInspeccion> {

    RestClient objRestClient;
    Gson gson;

    String intIdEmpresa = "";
    String strUsuario = "";
    String strModoUso = "";
    String strSincornizando = "";
    String strInspeccionesHechas = "";
    String strMensajePreExecute = "Consultando Areas,Riesgos,Inspecciones y Personas!!...Puede tardar varios minutos!!";
    public static final String OPERACION = "LT";
    public static final String OPERACION_INSP = "LI";
    public static final String OPERACION_PERSONAS = "BCCT";
    public static final String OPERACION_HABILIDADES = "LTH";
    public static final String OPERACION_POR_INSPECCION = "EX";

    private Context mContext;
    ProgressDialog mProgress;
    private ConfigurarInspeccionComplete mCallback;


    public static final String SOAP_ACTION_AREAS = "http://tempuri.org/cargarAreas";
    public static final String OPERATION_NAME_AREAS = "cargarAreas";

    public static final String SOAP_ACTION_RIESGOS = "http://tempuri.org/cargarRiesgos";
    public static final String OPERATION_NAME_RIESGO = "cargarRiesgos";

    public static final String SOAP_ACTION_INSPECCION = "http://tempuri.org/cargarInspeccionOp";
    public static final String OPERATION_NAME_INSPECCION = "cargarInspeccionOp";

    public static final String SOAP_ACTION_PERSONAS = "http://tempuri.org/CargarPersonaxEmpresa";
    public static final String OPERATION_NAME_PERSONAS = "CargarPersonaxEmpresa";

    public static final String SOAP_ACTION_POR_INSPECCION = "http://tempuri.org/cargarParametros";
    public static final String OPERATION_NAME_POR_INSPECCION = "cargarParametros";

    public static final String SOAP_ACTION_HABILIDADES = "http://tempuri.org/cargarHabilidades";
    public static final String OPERATION_NAME_HABILIDADES = "cargarHabilidades";

    ///OJO: El name espace debe ser exactamente igual al namespace del WebService en el asmx.cs
    public static final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";

    public static final String SOAP_ADDRESS = OutSafetyUtils.CONS_SOAP_ADDRESS;


    public AsyncExecuteGetConfigurarInspeccion(android.support.v4.app.Fragment objFragment, Context context) {
        this.mContext = context;
        this.mCallback = (ConfigurarInspeccionComplete) objFragment;
    }

    public AsyncExecuteGetConfigurarInspeccion(android.support.v4.app.Fragment objFragment, Context context, String strMensajePreExecute) {
        this.mContext = context;
        this.mCallback = (ConfigurarInspeccionComplete) objFragment;
        this.strMensajePreExecute = strMensajePreExecute;
    }

    @Override
    protected InputConfigurarInspeccion doInBackground(String... params) {

        intIdEmpresa = params[0];
        strUsuario = params[1];
        strModoUso = params[2];

        if (params.length > 3) {
            strSincornizando = params[3];
        }

        if (params.length > 4) {
            strInspeccionesHechas = params[4];
        }

        if (OutSafetyUtils.MODO_USO_OFFLINE.equals(strModoUso)) {
            AreaDataSource objAreaDataSource = new AreaDataSource(mContext);
            objAreaDataSource.open();
            List<Area> lstArea = objAreaDataSource.GetAreasByCentroTrabajo(intIdEmpresa);
            objAreaDataSource.close();

            RiesgoDataSource objRiesgoDataSource = new RiesgoDataSource(mContext);
            objRiesgoDataSource.open();
            List<Riesgo> lstRiesgo = objRiesgoDataSource.GetRiesgosByCentroTrabajo(intIdEmpresa);
            objRiesgoDataSource.close();

            InspeccionDataSource objInspeccionDataSource = new InspeccionDataSource(mContext);
            objInspeccionDataSource.open();
            List<Inspeccion> lstInspeccion = objInspeccionDataSource.GetInspeccionesByCentroTrabajo(intIdEmpresa);
            objInspeccionDataSource.close();

            PersonaDataSource objPersonaDataSource = new PersonaDataSource(mContext);
            objPersonaDataSource.open();
            List<Persona> lstPersona = objPersonaDataSource.GetPersonasByCentroTrabajo(intIdEmpresa);
            objPersonaDataSource.close();

            List<Parametro> lstParametros = new ArrayList<Parametro>();
            ParametroDataSource objParametroDataSource = new ParametroDataSource(mContext);
            objParametroDataSource.open();
            for (Inspeccion itemInsp :
                    lstInspeccion) {
                List<Parametro> currentParams = objParametroDataSource.GetParametrosByInspeccion(itemInsp.getIntIdInspeccion());
                for (Parametro currentItemParam :
                        currentParams) {
                    lstParametros.add(currentItemParam);
                }
            }
            objParametroDataSource.close();

            InputConfigurarInspeccion newInputConfigurarInspeccion = new InputConfigurarInspeccion();
            newInputConfigurarInspeccion.setLstArea(lstArea);
            newInputConfigurarInspeccion.setLstRiesgo(lstRiesgo);
            newInputConfigurarInspeccion.setLstInspeccion(lstInspeccion);
            newInputConfigurarInspeccion.setLstPersonas(lstPersona);
            newInputConfigurarInspeccion.setLstParametro(lstParametros);

            return newInputConfigurarInspeccion;
        }

        String errorMessage = "";

        List<Area> lstArea = new ArrayList<Area>();
        objRestClient = new RestClient(String.format(OutSafetyUtils.CONS_URL_RESTFUL_JSON + OutSafetyUtils.CONS_JSON_GET_AREAS, intIdEmpresa));
        RestfulResponse objRestfulResponse = null;

        try {
            objRestClient.Execute(RequestMethod.GET);
            gson = new Gson();
            objRestfulResponse = gson.fromJson(objRestClient.getResponse(), RestfulResponse.class);
            lstArea = gson.fromJson(objRestfulResponse.value, new TypeToken<List<Area>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Riesgo
        List<Riesgo> lstRiesgo = new ArrayList<Riesgo>();
        objRestClient = new RestClient(String.format(OutSafetyUtils.CONS_URL_RESTFUL_JSON + OutSafetyUtils.CONS_JSON_GET_RIESGOS, intIdEmpresa));
        objRestfulResponse = null;

        try {
            objRestClient.Execute(RequestMethod.GET);
            gson = new Gson();
            objRestfulResponse = gson.fromJson(objRestClient.getResponse(), RestfulResponse.class);
            lstRiesgo = gson.fromJson(objRestfulResponse.value, new TypeToken<List<Riesgo>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Inspeccion
        List<Inspeccion> lstInspeccion = new ArrayList<Inspeccion>();
        objRestClient = new RestClient(String.format(OutSafetyUtils.CONS_URL_RESTFUL_JSON + OutSafetyUtils.CONS_JSON_GET_INSPECCIONES, intIdEmpresa.toString(), "null"));
        objRestfulResponse = null;

        try {
            objRestClient.Execute(RequestMethod.GET);
            gson = new Gson();
            objRestfulResponse = gson.fromJson(objRestClient.getResponse(), RestfulResponse.class);
            lstInspeccion = gson.fromJson(objRestfulResponse.value, new TypeToken<List<Inspeccion>>() {
            }.getType());


        } catch (Exception e) {
            e.printStackTrace();
        }


        List<Parametro> lstParametro = new ArrayList<Parametro>();
        List<Habilidad> lstHabilidad = new ArrayList<Habilidad>();
        List<Persona> lstPersonas = new ArrayList<Persona>();

        if (OutSafetyUtils.CONS_SINCRONIZANDO.equals(strSincornizando)) {

            if (strInspeccionesHechas == "") {

                //Personas
                objRestClient = new RestClient(String.format(OutSafetyUtils.CONS_URL_RESTFUL_JSON + OutSafetyUtils.CONS_JSON_GET_PERSONAS, "null", intIdEmpresa.toString()));
                objRestfulResponse = null;

                try {
                    objRestClient.Execute(RequestMethod.GET);
                    gson = new Gson();
                    objRestfulResponse = gson.fromJson(objRestClient.getResponse(), RestfulResponse.class);
                    lstPersonas = gson.fromJson(objRestfulResponse.value, new TypeToken<List<Persona>>() {
                    }.getType());


                } catch (Exception e) {
                    e.printStackTrace();
                }

                //Parametros
                objRestClient = new RestClient(String.format(OutSafetyUtils.CONS_URL_RESTFUL_JSON + OutSafetyUtils.CONS_JSON_GET_PARAMETROS, intIdEmpresa.toString(), "null"));
                objRestfulResponse = null;

                try {
                    objRestClient.Execute(RequestMethod.GET);
                    gson = new Gson();
                    objRestfulResponse = gson.fromJson(objRestClient.getResponse(), RestfulResponse.class);
                    lstParametro= gson.fromJson(objRestfulResponse.value, new TypeToken<List<Parametro>>() {
                    }.getType());


                } catch (Exception e) {
                    e.printStackTrace();
                }

                //Habilidades
                objRestClient = new RestClient(String.format(OutSafetyUtils.CONS_URL_RESTFUL_JSON + OutSafetyUtils.CONS_JSON_GET_HABILIDADES, "null", intIdEmpresa.toString()));
                objRestfulResponse = null;

                try {
                    objRestClient.Execute(RequestMethod.GET);
                    gson = new Gson();
                    objRestfulResponse = gson.fromJson(objRestClient.getResponse(), RestfulResponse.class);
                    lstHabilidad = gson.fromJson(objRestfulResponse.value, new TypeToken<List<Habilidad>>() {
                    }.getType());


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        InputConfigurarInspeccion newInputConfigurarInspeccion = new InputConfigurarInspeccion();
        newInputConfigurarInspeccion.setLstArea(lstArea);
        newInputConfigurarInspeccion.setLstRiesgo(lstRiesgo);
        newInputConfigurarInspeccion.setLstInspeccion(lstInspeccion);
        newInputConfigurarInspeccion.setLstPersonas(lstPersonas);
        newInputConfigurarInspeccion.setIntIdEmpresa(intIdEmpresa);
        newInputConfigurarInspeccion.setLstParametro(lstParametro);
        newInputConfigurarInspeccion.setLstHabilidad(lstHabilidad);

        return newInputConfigurarInspeccion;
    }

    @Override
    protected void onPreExecute() {
        mProgress = new ProgressDialog(mContext);
        mProgress.setMessage(strMensajePreExecute);
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