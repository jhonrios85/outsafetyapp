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

import co.com.appsource.outsafetyapp.complete.CentroTrabajoComplete;
import co.com.appsource.outsafetyapp.db_helper.Tables.CentroTrabajo;
import co.com.appsource.outsafetyapp.db_helper.Tables.CentroTrabajoDataSource;
import co.com.appsource.outsafetyapp.util.OutSafetyUtils;

/**
 * Created by JANUS on 15/12/2015.
 */
public class AsyncExecuteGetCentroTrabajo extends AsyncTask<String, Void, List<CentroTrabajo>> {

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

        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);
        request.addProperty("idpersona", strUsuario);
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

        List<CentroTrabajo> lstCentroTrabajo = new ArrayList<CentroTrabajo>();

        SoapObject root = null;
        CentroTrabajo objCentroTrabajo = null;
        if (intCantidad > 0) {
            root = (SoapObject) responseSoap.getProperty(0);

            int intCantidadCts = 0;

            if (((org.ksoap2.serialization.SoapObject) root.getProperty(1)).getPropertyCount() > 0) {
                intCantidadCts = ((org.ksoap2.serialization.SoapObject) ((org.ksoap2.serialization.SoapObject) root.getProperty(1)).getProperty(0)).getPropertyCount();
            }


            for (int i = 0; i < intCantidadCts; i++) {

                org.ksoap2.serialization.SoapObject itemCentroTrabajo = (org.ksoap2.serialization.SoapObject) ((org.ksoap2.serialization.SoapObject) ((org.ksoap2.serialization.SoapObject) root.getProperty(1)).getProperty(0)).getProperty(i);

                objCentroTrabajo = new CentroTrabajo();
                objCentroTrabajo.setIntIdEmpresa(itemCentroTrabajo.getProperty(0).toString());
                objCentroTrabajo.setIntIdPersona(itemCentroTrabajo.getProperty(1).toString());
                objCentroTrabajo.setStrRazonSocial(itemCentroTrabajo.getProperty(2).toString());
                lstCentroTrabajo.add(objCentroTrabajo);
            }
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
