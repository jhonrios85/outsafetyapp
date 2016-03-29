package co.com.appsource.outsafetyapp.Async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import co.com.appsource.outsafetyapp.SincronzarFragment;
import co.com.appsource.outsafetyapp.complete.GuardarControlAccesoComplete;
import co.com.appsource.outsafetyapp.util.GuardarControlAccesoResult;
import co.com.appsource.outsafetyapp.util.OutSafetyUtils;

/**
 * Created by JANUS on 23/01/2016.
 */
public class AsyncExecuteGuardarControlAcceso extends AsyncTask<String, Void, GuardarControlAccesoResult> {

    String dtControlAcceso = "";
    String strModoUso = "";
    String strMensajePersonalizado = "Guardando Control de Acceso!!";


    private Context mContext;
    ProgressDialog mProgress;
    private GuardarControlAccesoComplete mCallback;

/*    public static final String SOAP_ACTION = "http://tempuri.org/guardarInspeccion";
    public static final String OPERATION_NAME = "guardarInspeccion";

    ///OJO: El name espace debe ser exactamente igual al namespace del WebService en el asmx.cs
    public static final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";

    public static final String SOAP_ADDRESS = "http://186.155.246.211/ws/wsSincronizacion.asmx";*/

    public static final String SOAP_ACTION = "http://tempuri.org/GuardarControlAccesoDt";
    public static final String OPERATION_NAME = "GuardarControlAccesoDt";

    ///OJO: El name espace debe ser exactamente igual al namespace del WebService en el asmx.cs
    public static final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";

    public static final String SOAP_ADDRESS = OutSafetyUtils.CONS_SOAP_ADDRESS_WRAPPER;

    public AsyncExecuteGuardarControlAcceso(android.support.v4.app.Fragment objFragment, Context context) {
        this.mContext = context;
        this.mCallback = (GuardarControlAccesoComplete) objFragment;

        if (objFragment.getClass().toString().equals(OutSafetyUtils.CONS_CLASS_CONTROL_ACCESO)) {
            this.strMensajePersonalizado = "Guardando Control Acceso!!";
        } else {
            //this.strMensajePersonalizado = "Espere,Guardando Control Acceso, puede tardar varios minutos. Se guardaran  " + ((SincronzarFragment) objFragment).cantidadInspecciones + " inspecciones!!";
            this.strMensajePersonalizado = "Guardando Control Acceso!!";
        }

    }

    @Override
    protected GuardarControlAccesoResult doInBackground(String... params) {


        GuardarControlAccesoResult boolControlAccesoResult = new GuardarControlAccesoResult();
        boolControlAccesoResult.setExito(false);

        dtControlAcceso = params[0];
        Long intIdInspeccion;


        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);
        request.addProperty("strDtControlAcceso", dtControlAcceso);

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
            boolControlAccesoResult.setExito(false);
        }

        SoapObject responseSoap = (SoapObject) response;


        if (responseSoap.getProperty(0).toString().equals("True")) {
            boolControlAccesoResult.setExito(true);
        } else {
            boolControlAccesoResult.setExito(false);
        }

        return boolControlAccesoResult;
    }

    @Override
    protected void onPreExecute() {

        mProgress = new ProgressDialog(mContext);
        mProgress.setMessage(strMensajePersonalizado);
        mProgress.show();
    }

    @Override
    protected void onPostExecute(GuardarControlAccesoResult result) {
        mProgress.dismiss();
        mCallback.onTaskComplete(result);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        // TODO Auto-generated method stub
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(GuardarControlAccesoResult result) {
        // TODO Auto-generated method stub
        super.onCancelled(result);
    }

    @Override
    protected void onCancelled() {
        // TODO Auto-generated method stub
        super.onCancelled();
    }
}

