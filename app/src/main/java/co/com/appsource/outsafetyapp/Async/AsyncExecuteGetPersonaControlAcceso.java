package co.com.appsource.outsafetyapp.Async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

import co.com.appsource.outsafetyapp.complete.BuscarColaboradorComplete;
import co.com.appsource.outsafetyapp.complete.BuscarPersonaIngresoComplete;
import co.com.appsource.outsafetyapp.db_helper.Tables.Persona;
import co.com.appsource.outsafetyapp.db_helper.Tables.PersonaDataSource;
import co.com.appsource.outsafetyapp.util.OutSafetyUtils;

/**
 * Created by JANUS on 20/01/2016.
 */
public class AsyncExecuteGetPersonaControlAcceso extends AsyncTask<String, Void, Persona> {

    String strCedula = "";
    String intIdCentroTrabajo = "";
    String strModoUso = "";
    public static final String OPERACION = "BCCT";
    public static final String intIdTipoPersona = "2";

    private Context mContext;
    ProgressDialog mProgress;
    private BuscarPersonaIngresoComplete mCallback;


    public static final String SOAP_ACTION = "http://tempuri.org/CargarPersonaxEmpresa";
    public static final String OPERATION_NAME = "CargarPersonaxEmpresa";

    ///OJO: El name espace debe ser exactamente igual al namespace del WebService en el asmx.cs
    public static final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";

    public static final String SOAP_ADDRESS = OutSafetyUtils.CONS_SOAP_ADDRESS;


    public AsyncExecuteGetPersonaControlAcceso(android.support.v4.app.Fragment objFragment, Context context) {
        this.mContext = context;
        this.mCallback = (BuscarPersonaIngresoComplete) objFragment;
    }

    @Override
    protected Persona doInBackground(String... params) {

        strCedula = params[0];
        intIdCentroTrabajo = params[1];
        strModoUso = params[2];

        if (OutSafetyUtils.MODO_USO_OFFLINE.equals(strModoUso)) {
            PersonaDataSource objPersonaDataSource = new PersonaDataSource(mContext);
            objPersonaDataSource.open();
            List<Persona> lstPersonas = objPersonaDataSource.GetPersonasByDocumento(strCedula);
            objPersonaDataSource.close();

            Persona objPersona = new Persona();

            if (lstPersonas != null) {
                if (lstPersonas.size() > 0) {
                    objPersona = lstPersonas.get(0);
                }
            }

            return objPersona;
        }

        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);
        request.addProperty("idEmpresa", intIdCentroTrabajo);
        request.addProperty("TipoPersona", intIdTipoPersona);
        request.addProperty("operacion", OPERACION);


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
            return null;
        }

        SoapObject responseSoap = (SoapObject) response;

        int intCantidad = 0;
        intCantidad = Integer.valueOf(String.valueOf(responseSoap.getPropertyCount()));

        List<Persona> lstPersonas = new ArrayList<Persona>();

        SoapObject root = null;
        Persona objPersona = null;
        if (intCantidad > 0) {
            root = (SoapObject) responseSoap.getProperty(0);
            int intCantidadPersonas = ((org.ksoap2.serialization.SoapObject) ((org.ksoap2.serialization.SoapObject) root.getProperty(1)).getProperty(0)).getPropertyCount();

            for (int i = 0; i < intCantidadPersonas; i++) {

                org.ksoap2.serialization.SoapObject itemPersona = (org.ksoap2.serialization.SoapObject) ((org.ksoap2.serialization.SoapObject) ((org.ksoap2.serialization.SoapObject) root.getProperty(1)).getProperty(0)).getProperty(i);

                if (itemPersona.getProperty(5).toString().equals(strCedula)) {

                    objPersona = new Persona();
                    objPersona.setIntIdEmpresa(itemPersona.getProperty(0).toString());
                    objPersona.setStrRazonSocial(itemPersona.getProperty(1).toString());
                    objPersona.setIntIdPersona(itemPersona.getProperty(2).toString());
                    objPersona.setStrNombreProfesional(itemPersona.getProperty(3).toString());
                    objPersona.setStrApellidoProfesional(itemPersona.getProperty(4).toString());
                    objPersona.setStrCedula(itemPersona.getProperty(5).toString());
                    objPersona.setIntTipoPersona(itemPersona.getProperty(6).toString());
                    lstPersonas.add(objPersona);
                }
            }
        }

        if (lstPersonas != null) {
            if (lstPersonas.size() > 0) {
                objPersona = lstPersonas.get(0);
            }
        }

        return objPersona;
    }

    @Override
    protected void onPreExecute() {
        mProgress = new ProgressDialog(mContext);
        mProgress.setMessage("Consultado Colaborador!!...");
        mProgress.show();
    }

    @Override
    protected void onPostExecute(Persona result) {
        mProgress.dismiss();
        mCallback.onTaskComplete(result);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        // TODO Auto-generated method stub
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(Persona result) {
        // TODO Auto-generated method stub
        super.onCancelled(result);
    }

    @Override
    protected void onCancelled() {
        // TODO Auto-generated method stub
        super.onCancelled();
    }
}