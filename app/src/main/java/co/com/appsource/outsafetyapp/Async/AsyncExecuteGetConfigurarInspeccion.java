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

        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME_AREAS);
        request.addProperty("operacion", OPERACION);
        request.addProperty("centroTrabajo", intIdEmpresa);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);
        SoapObject response = null;

        String errorMessage = "";

        try {
            httpTransport.call(SOAP_ACTION_AREAS, envelope);
            response = (SoapObject) envelope.bodyIn;
        } catch (Exception exception) {
            errorMessage = exception.toString();
            return null;
        }

        SoapObject responseSoap = (SoapObject) response;

        int intCantidad = 0;
        intCantidad = Integer.valueOf(String.valueOf(responseSoap.getPropertyCount()));

        List<Area> lstArea = new ArrayList<Area>();

        SoapObject root = null;
        Area objArea = null;

        if (intCantidad > 0) {
            root = (SoapObject) responseSoap.getProperty(0);

            int intCantidadAreas = 0;

            if (((org.ksoap2.serialization.SoapObject) root.getProperty(1)).getPropertyCount() > 0) {
                intCantidadAreas = ((org.ksoap2.serialization.SoapObject) ((org.ksoap2.serialization.SoapObject) root.getProperty(1)).getProperty(0)).getPropertyCount();
            }

            for (int j = 0; j < intCantidadAreas; j++) {

                org.ksoap2.serialization.SoapObject itemArea = (org.ksoap2.serialization.SoapObject) ((org.ksoap2.serialization.SoapObject) ((org.ksoap2.serialization.SoapObject) root.getProperty(1)).getProperty(0)).getProperty(j);

                objArea = new Area();
                objArea.setIntIdEmpresa(itemArea.getProperty(0).toString());
                objArea.setIntIdArea(itemArea.getProperty(1).toString());
                objArea.setStrDescripcion(itemArea.getProperty(2).toString());
                objArea.setIntIdEmpresa(intIdEmpresa);
                lstArea.add(objArea);
            }
        }

        //Riesgo

        request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME_RIESGO);
        request.addProperty("operacion", OPERACION);
        request.addProperty("centroTrabajo", intIdEmpresa);


        envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        httpTransport = new HttpTransportSE(SOAP_ADDRESS);
        response = null;

        errorMessage = "";

        try {
            httpTransport.call(SOAP_ACTION_RIESGOS, envelope);
            response = (SoapObject) envelope.bodyIn;
        } catch (Exception exception) {
            errorMessage = exception.toString();
            return null;
        }

        responseSoap = (SoapObject) response;

        int intCantidadResultRiesgo = 0;
        intCantidadResultRiesgo = Integer.valueOf(String.valueOf(responseSoap.getPropertyCount()));

        List<Riesgo> lstRiesgo = new ArrayList<Riesgo>();

        root = null;
        Riesgo objRiesgo = null;

        if (intCantidadResultRiesgo > 0) {
            root = (SoapObject) responseSoap.getProperty(0);
            if (root != null) {
                if (root.getPropertyCount() > 1) {
                    if (((org.ksoap2.serialization.SoapObject) root.getProperty(1)).getPropertyCount() > 0) {

                        int intCantidadRiesgos = ((org.ksoap2.serialization.SoapObject) ((org.ksoap2.serialization.SoapObject) root.getProperty(1)).getProperty(0)).getPropertyCount();

                        for (int k = 0; k < intCantidadRiesgos; k++) {

                            org.ksoap2.serialization.SoapObject itemRiesgo = (org.ksoap2.serialization.SoapObject) ((org.ksoap2.serialization.SoapObject) ((org.ksoap2.serialization.SoapObject) root.getProperty(1)).getProperty(0)).getProperty(k);

                            objRiesgo = new Riesgo();
                            objRiesgo.setIntIdRiesgo(itemRiesgo.getProperty(0).toString());
                            objRiesgo.setStrDescripcionRiesgo(itemRiesgo.getProperty(1).toString());
                            objRiesgo.setStrIdCentroTrabajo(intIdEmpresa);
                            lstRiesgo.add(objRiesgo);
                        }
                    }
                }
            }
        }

        //Inspeccion
        List<Inspeccion> lstInspeccion = new ArrayList<Inspeccion>();
        objRestClient = new RestClient(String.format(OutSafetyUtils.CONS_URL_RESTFUL_JSON + OutSafetyUtils.CONS_JSON_GET_INSPECCIONES, intIdEmpresa.toString(), "null"));
        RestfulResponse objRestfulResponse = null;

        try {
            objRestClient.Execute(RequestMethod.GET);
            gson = new Gson();
            objRestfulResponse = gson.fromJson(objRestClient.getResponse(), RestfulResponse.class);
            lstInspeccion = gson.fromJson(objRestfulResponse.value, new TypeToken<List<Inspeccion>>() {
            }.getType());


        } catch (Exception e) {
            e.printStackTrace();
        }


        //Parametros
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

                for (Inspeccion itemInspeccion : lstInspeccion
                        ) {

                    //if (itemInspeccion.getIntIdEmpresa() == intIdEmpresa) {


                    request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME_POR_INSPECCION);
                    request.addProperty("operacion", OPERACION_POR_INSPECCION);
                    request.addProperty("intIdInspeccion", itemInspeccion.getIntIdInspeccion());


                    envelope = new SoapSerializationEnvelope(
                            SoapEnvelope.VER11);
                    envelope.dotNet = true;
                    envelope.setOutputSoapObject(request);

                    httpTransport = new HttpTransportSE(SOAP_ADDRESS);
                    response = null;

                    errorMessage = "";

                    try {
                        httpTransport.call(SOAP_ACTION_POR_INSPECCION, envelope);
                        response = (SoapObject) envelope.bodyIn;
                    } catch (Exception exception) {
                        errorMessage = exception.toString();
                        return null;
                    }

                    responseSoap = (SoapObject) response;

                    int intCantidadResultParams = 0;
                    intCantidadResultParams = Integer.valueOf(String.valueOf(responseSoap.getPropertyCount()));

                    Parametro objParametro = null;

                    if (intCantidadResultParams > 0) {
                        root = (SoapObject) responseSoap.getProperty(0);
                        if (root.getPropertyCount() > 1) {
                            if (((org.ksoap2.serialization.SoapObject) root.getProperty(1)).getPropertyCount() > 0) {

                                int intCantidadParametros = ((org.ksoap2.serialization.SoapObject) ((org.ksoap2.serialization.SoapObject) root.getProperty(1)).getProperty(0)).getPropertyCount();

                                for (int k = 0; k < intCantidadParametros; k++) {
                                    if (((org.ksoap2.serialization.SoapObject) ((org.ksoap2.serialization.SoapObject) root.getProperty(1)).getProperty(0)).getPropertyCount() > k) {

                                        org.ksoap2.serialization.SoapObject itemParametro = (org.ksoap2.serialization.SoapObject) ((org.ksoap2.serialization.SoapObject) ((org.ksoap2.serialization.SoapObject) root.getProperty(1)).getProperty(0)).getProperty(k);
                                        objParametro = new Parametro();
                                        objParametro.setIntIdParametro(itemParametro.getProperty(0).toString());

                                        String descr = itemParametro.getProperty(1).toString();

                                        if (descr.toUpperCase().equals("anyType{}".toUpperCase())) {
                                            descr = "";
                                        }
                                        objParametro.setStrDescripcionParametro(descr);
                                        objParametro.setIntIdInspeccion(itemInspeccion.getIntIdInspeccion());
                                        lstParametro.add(objParametro);
                                    }
                                }
                            }
                        }
                    }
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