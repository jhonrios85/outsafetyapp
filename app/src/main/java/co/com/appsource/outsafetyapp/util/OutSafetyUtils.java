package co.com.appsource.outsafetyapp.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.text.method.NumberKeyListener;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import co.com.appsource.outsafetyapp.db_helper.Tables.AreaDataSource;
import co.com.appsource.outsafetyapp.db_helper.Tables.CentroTrabajoDataSource;
import co.com.appsource.outsafetyapp.db_helper.Tables.InspeccionDataSource;
import co.com.appsource.outsafetyapp.db_helper.Tables.LoginDataSource;
import co.com.appsource.outsafetyapp.db_helper.Tables.ParametroDataSource;
import co.com.appsource.outsafetyapp.db_helper.Tables.Persona;
import co.com.appsource.outsafetyapp.db_helper.Tables.PersonaDataSource;
import co.com.appsource.outsafetyapp.db_helper.Tables.RiesgoDataSource;

/**
 * Created by JANUS on 02/12/2015.
 */
public class OutSafetyUtils {

    /* INICIO-----CONFIGURACIÓN DE LA APP*/
    //public static String CONS_SOAP_ADDRESS = "http://186.155.246.211/ws/wsSincronizacion.asmx";
    public static String CONS_SOAP_ADDRESS = "http://www.out-safety.com/wspruebas/wsSincronizacion.asmx"; //Produccion
    //public static String CONS_SOAP_ADDRESS_WRAPPER = "http://186.155.246.211/pruebas/WebServices/WsOutsafetyApp.asmx";
    public static String CONS_SOAP_ADDRESS_WRAPPER = "http://186.155.246.211/web/WebServices/WsOutsafetyApp.asmx"; //Produccion

/*    public static String CONS_URL_RESTFUL_JSON = "http://www.out-safety.com/queryPruebas/sincronizacion/";//Pruebas
    public static String CONS_URL_RESTFUL_SAVE_JSON = "http://www.out-safety.com/savePruebas/api/GuardarInspeccion";//Pruebas
    public static String CONS_URL_RESTFUL_SAVE_ENVIDENCIAS_JSON = "http://www.out-safety.com/savePruebas/api/GuardarEvidencia";//Pruebas
    public static String CONS_URL_RESTFUL_SAVE_COLABORADORES_JSON = "http://www.out-safety.com/savePruebas/api/GuardarFirma";//Pruebas*/

    public static String CONS_URL_RESTFUL_JSON = "http://www.out-safety.com/query/sincronizacion/";//Produccion
    public static String CONS_URL_RESTFUL_SAVE_JSON = "http://www.out-safety.com/save/api/GuardarInspeccion";//Produccion
    public static String CONS_URL_RESTFUL_SAVE_ENVIDENCIAS_JSON = "http://www.out-safety.com/save/api/GuardarEvidencia";//Produccion
    public static String CONS_URL_RESTFUL_SAVE_COLABORADORES_JSON = "http://www.out-safety.com/save/api/GuardarFirma";//Produccion*/

    public static String CONS_JSON_GET_PERSONAS = "GetPersonas(strCedula=%s,idCentroTrabajo=%s)";
    public static String CONS_JSON_GET_HABILIDADES = "GetHabilidades(strCedula=%s,idCentroTrabajo=%s)";
    public static String CONS_JSON_GET_INSPECCIONES = "GetInspecciones(idCentroTrabajo=%s,idRiesgo=%s)";
    public static String CONS_JSON_VALIDAR_PERSONA = "ValidarPersona(idpersona=%s,passwd=%s)";
    public static String CONS_JSON_GET_AREAS = "GetAreasByEmpresa(idCentroTrabajo=%s)";
    public static String CONS_JSON_GET_RIESGOS = "GetRiesgosByEmpresa(idCentroTrabajo=%s)";
    public static String CONS_JSON_GET_PARAMETROS = "GetParametros(idCentroTrabajo=%s,idInspeccion=%s)";
    public static String CONS_JSON_GET_CENTROS_TRABAJO = "GetCentrosTrabajoByPersona(strCedula=%s)";

    public static String CONS_SOAP_DATABASE_NAME = "outsafety.db";

    public static String CONS_CLASS_OBSERVACION = "class co.com.appsource.outsafetyapp.ObservacionInspeccionFragment";
    public static String CONS_CLASS_CONTROL_ACCESO = "class co.com.appsource.outsafetyapp.ControlIngresoFragment";
    public static String CONS_CLASS_SINCRONIZAR = "class co.com.appsource.outsafetyapp.SincronzarFragment";
    /* FIN-----CONFIGURACIÓN DE LA APP*/


    public static final String PREFS_NAME = "OutsafetyPrefsFile";

    public static int SIZE_EMPTY_SIGNATURE = 4179;

    public static String SHARED_PREFERENCE_MODO_USO = "modo_uso";
    public static String SHARED_PREFERENCE_MODO_USO_OFF = "offline";
    public static String SHARED_PREFERENCE_MODO_USO_ON = "online";


    public static String SHARED_PREFERENCE_EVIDENCIAS = "evidencias";
    public static String SHARED_PREFERENCE_USERNAME = "username";
    public static String SHARED_PREFERENCE_TIPO_PERSONA = "tipoPersona";
    public static String SHARED_PREFERENCE_CENTRO_TRABAJO = "centroTrabajo";
    public static String SHARED_PREFERENCE_LAST_FRAGMENT = "ultimoFragment";
    public static String SHARED_PREFERENCE_BEFORE_FRAGMENT = "anteriorFragment";
    public static String SHARED_PREFERENCE_ID_AREA = "idArea";
    public static String SHARED_PREFERENCE_ID_RIESGO = "idRiesgo";
    public static String SHARED_PREFERENCE_ID_INSP = "idInspeccion";
    public static String SHARED_PREFERENCE_ID_FOTO = "idFoto";


    public static String MODO_USO_ONLINE = "Trabajar Online";
    public static String MODO_USO_OFFLINE = "Trabajar Offline";
    public static String CONS_COLABORADORES_SELECTED = "colaboradoresSeleccionados";
    public static String CONS_OBSERVACION_INSP = "observacionInsp";
    public static String CONS_ES_SIN_OBSERVACION_INSP = "esSinObservacion";
    public static String CONS_ES_INSP_POS = "esPositiva";
    public static String CONS_SINCRONIZANDO = "True";
    public static String CONS_FRAG_COLABORADORES_INSP = "fragment_colaboradores_inspeccion";
    public static String CONS_FRAG_CENTROS_TRABAJO = "fragment_centro_trabajo";
    public static String CONS_FRAG_HABILIDADES = "fragment_habilidades";
    public static String CONS_FRAG_CONTROL_ACCESO = "fragment_control_ingreso";
    public static String CONS_FRAG_HAZARD_ID = "fragment_hazard_id";
    public static String CONS_FRAG_HAZARD_ID_PORTADA = "fragment_hazard_id_portada";
    public static String CONS_FRAG_LOGIN = "fragment_login";
    public static String CONS_FRAG_CONFIG_INSP = "fragment_configurar_inspeccion";
    public static String CONS_FRAG_OBS_INSP = "fragment_observacion_inspeccion";


    //1	Administrador
    //2	Consultor
    //6	Porteria
    //14	Super Usuario
    public static String[] ARRAY_PERFILES = {"2", "6", "1", "14", "10"};

    public static String CONS_TP_ADMIN = "1";
    public static String CONS_TP_CONSULTOR = "2";
    public static String CONS_TP_PORTERIA = "6";
    public static String CONS_TP_SUPER_USER = "14";
    public static String CONS_TP_SUPER_SUPER_USER = "10";


    public static String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }


    public static Bitmap decodeOriginalImage(String path) {

        //First decode with inJustDecodeBounds=true to check dimensions
        BitmapFactory.Options options = new BitmapFactory.Options();
        options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bmpFile = BitmapFactory.decodeFile(path, options);

        return BitmapFactory.decodeFile(path, options);
    }

    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) { // BEST QUALITY MATCH

        //First decode with inJustDecodeBounds=true to check dimensions
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize, Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > reqHeight) {
            inSampleSize = Math.round((float) height / (float) reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth) {
            //if(Math.round((float)width / (float)reqWidth) > inSampleSize) // If bigger SampSize..
            inSampleSize = Math.round((float) width / (float) reqWidth);
        }

        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        Bitmap bmpFile = BitmapFactory.decodeFile(path, options);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bmpFile.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

        File f = new File(path);
        try {
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (java.io.FileNotFoundException ex) {

        } catch (java.io.IOException exm) {

        }

        options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        return BitmapFactory.decodeFile(path, options);
    }

    public static Bitmap ImageCompressCustom(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 75, baos);

        return BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()));
    }


    public static boolean ValidarConexion(Context currentContext) {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) currentContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null) {
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                //we are connected to a network
                connected = true;
            } else {
                connected = false;
            }
        } else {
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                //we are connected to a network
                connected = true;
            } else {
                connected = false;
            }
        }

        return connected;
    }

    public static boolean CheckExternalStorage() {
// Get the external storage's state
        String state = Environment.getExternalStorageState();
        boolean externalStorageAvailable = false;
        boolean externalStorageWriteable = false;

        boolean allowExternaCheck = false;

        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // Storage is available and writeable
            externalStorageAvailable = externalStorageWriteable = true;
            allowExternaCheck = true;
        } else if (state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            // Storage is only readable
            externalStorageAvailable = true;
            externalStorageWriteable = false;
            allowExternaCheck = true;
        } else {
            // Storage is neither readable nor writeable
            externalStorageAvailable = externalStorageWriteable = false;
        }

        return allowExternaCheck;
    }


    public static void MostrarAlerta(String strMensaje, Context currentContext) {
        new AlertDialog.Builder(currentContext)
                .setTitle("Validación")
                .setMessage(strMensaje)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
//                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        //                  public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        //                }
                        //          })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public static boolean ValidarExisteInformacionSincronizada(Context currentContext) {
        boolean existe = true;

        PersonaDataSource objPersonaDataSource = new PersonaDataSource(currentContext);
        objPersonaDataSource.open();
        if (!(objPersonaDataSource.GetCount() > 0)) {
            objPersonaDataSource.close();
            return false;
        }
        objPersonaDataSource.close();

        AreaDataSource objAreaDataSource = new AreaDataSource(currentContext);
        objAreaDataSource.open();
        if (!(objAreaDataSource.GetCount() > 0)) {
            objAreaDataSource.close();
            return false;
        }
        objAreaDataSource.close();

        CentroTrabajoDataSource objCentroTrabajoDataSource = new CentroTrabajoDataSource(currentContext);
        objCentroTrabajoDataSource.open();
        if (!(objCentroTrabajoDataSource.GetCount() > 0)) {
            objCentroTrabajoDataSource.close();
            return false;
        }
        objCentroTrabajoDataSource.close();

        InspeccionDataSource objInspeccionDataSource = new InspeccionDataSource(currentContext);
        objInspeccionDataSource.open();
        if (!(objInspeccionDataSource.GetCount() > 0)) {
            objInspeccionDataSource.close();
            return false;
        }
        objInspeccionDataSource.close();

        LoginDataSource objLoginDataSource = new LoginDataSource(currentContext);
        objLoginDataSource.open();
        if (!(objLoginDataSource.GetCount() > 0)) {
            objLoginDataSource.close();
            return false;
        }
        objLoginDataSource.close();

        ParametroDataSource objParametroDataSource = new ParametroDataSource(currentContext);
        objParametroDataSource.open();
        if (!(objParametroDataSource.GetCount() > 0)) {
            objParametroDataSource.close();
            return false;
        }
        objParametroDataSource.close();

        RiesgoDataSource objRiesgoDataSource = new RiesgoDataSource(currentContext);
        objRiesgoDataSource.open();
        if (!(objRiesgoDataSource.GetCount() > 0)) {
            objRiesgoDataSource.close();
            return false;
        }
        objRiesgoDataSource.close();

        return existe;
    }

    public static String GetCurrentModoUso(Context currentContext) {
        SharedPreferences settings = currentContext.getSharedPreferences(PREFS_NAME, 0);
        return settings.getString(SHARED_PREFERENCE_MODO_USO, "");
    }

    public static String GetCurrentUser(Context currentContext) {
        SharedPreferences settings = currentContext.getSharedPreferences(PREFS_NAME, 0);
        return settings.getString(SHARED_PREFERENCE_USERNAME, "");
    }

    public static String GetCurrentUserTipoPersona(Context currentContext) {
        SharedPreferences settings = currentContext.getSharedPreferences(PREFS_NAME, 0);
        return settings.getString(SHARED_PREFERENCE_TIPO_PERSONA, "");
    }

    public static String GetCurrentCentroTrabajo(Context currentContext) {
        SharedPreferences settings = currentContext.getSharedPreferences(PREFS_NAME, 0);
        return settings.getString(SHARED_PREFERENCE_CENTRO_TRABAJO, "");
    }

    public static String GetCurrentInspeccionObservacion(Context currentContext) {
        SharedPreferences settings = currentContext.getSharedPreferences(PREFS_NAME, 0);
        return settings.getString(CONS_OBSERVACION_INSP, "");
    }

    public static Boolean GetCurrentInspeccionEsPositiva(Context currentContext) {
        SharedPreferences settings = currentContext.getSharedPreferences(PREFS_NAME, 0);
        return settings.getBoolean(CONS_ES_INSP_POS, false);
    }

    public static Boolean GetCurrentStateSinObservacion(Context currentContext) {
        SharedPreferences settings = currentContext.getSharedPreferences(PREFS_NAME, 0);
        return settings.getBoolean(CONS_ES_SIN_OBSERVACION_INSP, false);
    }


    public static String GetCurrentFragment(Context currentContext) {
        SharedPreferences settings = currentContext.getSharedPreferences(PREFS_NAME, 0);
        return settings.getString(SHARED_PREFERENCE_LAST_FRAGMENT, "");
    }

    public static String GetCurrentFragmentBefore(Context currentContext) {
        SharedPreferences settings = currentContext.getSharedPreferences(PREFS_NAME, 0);
        return settings.getString(SHARED_PREFERENCE_BEFORE_FRAGMENT, "");
    }

    public static void SetLastFragment(Context currentContext, String framgentTag) {
        SharedPreferences settings = currentContext.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(SHARED_PREFERENCE_LAST_FRAGMENT, framgentTag);
        editor.commit();
    }

    public static void SetBeforeFragment(Context currentContext, String framgentTag) {
        SharedPreferences settings = currentContext.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(SHARED_PREFERENCE_BEFORE_FRAGMENT, framgentTag);
        editor.commit();
    }
}
