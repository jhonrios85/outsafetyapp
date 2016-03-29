package co.com.appsource.outsafetyapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.com.appsource.outsafetyapp.complete.LoginComplete;
import co.com.appsource.outsafetyapp.db_helper.SQLOutsafetyHelper;
import co.com.appsource.outsafetyapp.db_helper.Tables.LoginDataSource;
import co.com.appsource.outsafetyapp.util.OutSafetyUtils;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Login.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Login extends android.support.v4.app.Fragment implements LoginComplete {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText objEtxtUsuario;
    private EditText objEtxtPassword;
    private Button objBtnIngresar;
    public SQLiteDatabase dbOutSafety = null;
    View rootView;

    public static Login fragment;

    private OnFragmentInteractionListener mListener;

    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Login.
     */
    // TODO: Rename and change types and number of parameters
    public static Login newInstance(String param1, String param2) {
        fragment = new Login();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Login() {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //menu.removeItem(R.id.action_evidencias);
        //menu.removeItem(R.id.action_modo_off_on);

        //inflater.inflate(R.menu.main, menu);

        //menu.findItem(R.id.action_modo_off_on).setTitle(((MainActivity) getActivity()).ConfigurarModoDeUso(OutSafetyUtils.GetCurrentModoUso(getContext())).equals(OutSafetyUtils.MODO_USO_ONLINE) ? OutSafetyUtils.MODO_USO_OFFLINE : OutSafetyUtils.MODO_USO_ONLINE);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        setHasOptionsMenu(true);
    }

    public void CreateDataBaseOutSafety() {
        dbOutSafety = new SQLOutsafetyHelper(this.getActivity()).getWritableDatabase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);

        ConfigurarControles();

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public boolean ValidateDatabaseExist() {
        File dbFile = this.getActivity().getDatabasePath(SQLOutsafetyHelper.DATABASE_NAME);
        return dbFile.exists();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onTaskComplete(co.com.appsource.outsafetyapp.db_helper.Tables.Login result) {

        MainActivity objMainActivity = (MainActivity) getContext();
        LoginDataSource objLoginDataSource;

        if (result != null) {

            if (Arrays.asList(OutSafetyUtils.ARRAY_PERFILES).contains(result.getIntTipoPersona())) {


                objLoginDataSource = new LoginDataSource(getContext());
                objLoginDataSource.open();
                objLoginDataSource.truncateTable();
                result = objLoginDataSource.CreateLogin(result.getIntTipoPersona()
                        , result.getStrCedula()
                        , result.getStrPassword());
                objLoginDataSource.close();

                Toast toast = Toast.makeText(getActivity().getApplicationContext()
                        , "Bienvenido!!!"
                        , Toast.LENGTH_LONG);
                toast.show();

                if (!objMainActivity.ValidateDatabaseExist()) {
                    objMainActivity.CreateDataBaseOutSafety();
                }

            /*objLoginDataSource = new LoginDataSource(objMainActivity);
            objLoginDataSource.open();
            objLoginDataSource.truncateTable();
            objLoginDataSource.CreateLogin("2"
                    , objEtxtUsuario.getText().toString()
                    , objEtxtPassword.getText().toString());
            objLoginDataSource.close();*/

                // We need an Editor object to make preference changes.
                // All objects are from android.context.Context
                SharedPreferences settings = objMainActivity.getSharedPreferences(objMainActivity.PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(OutSafetyUtils.SHARED_PREFERENCE_USERNAME, objEtxtUsuario.getText().toString());
                editor.putString(OutSafetyUtils.SHARED_PREFERENCE_TIPO_PERSONA, result.getIntTipoPersona());
                editor.commit();

                objMainActivity.updateNavigationDrawer(result.getIntTipoPersona());

                if (result.getIntTipoPersona().equals(OutSafetyUtils.CONS_TP_ADMIN)
                        || result.getIntTipoPersona().equals(OutSafetyUtils.CONS_TP_CONSULTOR)
                        || result.getIntTipoPersona().equals(OutSafetyUtils.CONS_TP_SUPER_USER)
                        || result.getIntTipoPersona().equals(OutSafetyUtils.CONS_TP_SUPER_SUPER_USER)) {
                    objMainActivity.ShowCentrosTrabajo(objEtxtUsuario.getText().toString());
                }

                if (result.getIntTipoPersona().equals(OutSafetyUtils.CONS_TP_PORTERIA)) {
                    objMainActivity.ShowControlAcceso();
                }
            } else {

                Toast toast = Toast.makeText(getActivity().getApplicationContext()
                        , "Usuario no autorizado!!!"
                        , Toast.LENGTH_LONG);
                toast.show();
            }
        } else {

            //if (!objMainActivity.ValidateDatabaseExist()) {
            Toast toast = Toast.makeText(getActivity().getApplicationContext()
                    , "Usuario y/o password incorrectos!!!"
                    , Toast.LENGTH_LONG);
            toast.show();
            //}
        }
    }

    public void addNavigationRuleNextScreen(final Activity from,
                                            final Class to) {
        Intent intent = new Intent();
        intent.setClass(from, to);
        from.startActivity(intent);
    }

    /**
     * @return
     */
    private android.support.v7.app.ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    void ConfigurarControles() {
        objEtxtUsuario = (EditText) rootView.findViewById(R.id.etxtUsuario);
        objEtxtPassword = (EditText) rootView.findViewById(R.id.etxtPassword);
        objBtnIngresar = (Button) rootView.findViewById(R.id.btnIngresar);
        fragment = this;
        objBtnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

/*                if (OutSafetyUtils.ValidarConexion(getContext())) {
                    new AsyncExecuteLogin(fragment, getContext()).execute(objEtxtUsuario.getText().toString()
                            , objEtxtPassword.getText().toString());
                } else {
                    onTaskComplete(ValidarLoginLocal());
                }*/

                new AsyncExecuteLogin(fragment, getContext()).execute(objEtxtUsuario.getText().toString()
                        , objEtxtPassword.getText().toString()
                        , OutSafetyUtils.GetCurrentModoUso(getContext()));

                InputMethodManager input = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                input.hideSoftInputFromWindow(objBtnIngresar.getWindowToken(), 0);
            }
        });

//    	addNavigationRuleNextScreen(R.id.btnIngresar, this,
//				AsambleasActivity.class);
    }

    public co.com.appsource.outsafetyapp.db_helper.Tables.Login ValidarLoginLocal() {
        co.com.appsource.outsafetyapp.db_helper.Tables.Login autenticado = null;
        MainActivity objMainActivity = (MainActivity) getContext();

        if (objMainActivity.ValidateDatabaseExist()) {
            LoginDataSource objLoginDataSource = new LoginDataSource(objMainActivity);
            objLoginDataSource.open();
            autenticado = objLoginDataSource.GetLoginByCredenciales(objEtxtUsuario.getText().toString()
                    , objEtxtPassword.getText().toString());
        }

        return autenticado;
    }

    public boolean ValidarConexion() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else {
            connected = false;
        }

        return connected;
    }

}

class AsyncExecuteLogin extends AsyncTask<String, Void, co.com.appsource.outsafetyapp.db_helper.Tables.Login> {

    String strUsuario = "";
    String strPassword = "";
    String strModoUso = "";

    private Context mContext;
    ProgressDialog mProgress;
    private LoginComplete mCallback;


    public static final String SOAP_ACTION = "http://tempuri.org/sincronizarPersona";
    public static final String OPERATION_NAME = "sincronizarPersona";

    ///OJO: El name espace debe ser exactamente igual al namespace del WebService en el asmx.cs
    public static final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";

    public static final String SOAP_ADDRESS = OutSafetyUtils.CONS_SOAP_ADDRESS;


    public AsyncExecuteLogin(android.support.v4.app.Fragment objFragment, Context context) {
        this.mContext = context;
        this.mCallback = (LoginComplete) objFragment;
    }

    @Override
    protected co.com.appsource.outsafetyapp.db_helper.Tables.Login doInBackground(String... params) {

        strUsuario = params[0];
        strPassword = params[1];
        strModoUso = params[2];

        if (OutSafetyUtils.MODO_USO_OFFLINE.equals(strModoUso)) {
            LoginDataSource objLoginDataSource = new LoginDataSource(mContext);
            objLoginDataSource.open();
            co.com.appsource.outsafetyapp.db_helper.Tables.Login currentLogin = objLoginDataSource.GetLoginByCredenciales(strUsuario, strPassword);
            objLoginDataSource.close();
            return currentLogin;
        }

        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);
        request.addProperty("idpersona", strUsuario);
        request.addProperty("passwd", strPassword);


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

        co.com.appsource.outsafetyapp.db_helper.Tables.Login objLogin = null;

        SoapObject root = null;

        if (intCantidad > 0) {
            root = (SoapObject) responseSoap.getProperty(0);

            int intCantidadPersonas = 0;

            if (((org.ksoap2.serialization.SoapObject) root.getProperty(1)).getPropertyCount() > 0) {
                intCantidadPersonas = ((org.ksoap2.serialization.SoapObject) ((org.ksoap2.serialization.SoapObject) root.getProperty(1)).getProperty(0)).getPropertyCount();
            }

            if (intCantidadPersonas > 0) {
                org.ksoap2.serialization.SoapObject itemLogin = (org.ksoap2.serialization.SoapObject) ((org.ksoap2.serialization.SoapObject) ((org.ksoap2.serialization.SoapObject) root.getProperty(1)).getProperty(0)).getProperty(0);
                objLogin = new co.com.appsource.outsafetyapp.db_helper.Tables.Login();
                objLogin.setStrPassword(itemLogin.getProperty(0).toString());
                objLogin.setStrCedula(itemLogin.getProperty(1).toString());
                objLogin.setIntTipoPersona(itemLogin.getProperty(2).toString());
            }
        }

        return objLogin;
    }

    @Override
    protected void onPreExecute() {
        mProgress = new ProgressDialog(mContext);
        mProgress.setMessage("Autenticando!!...");
        mProgress.show();
    }

    @Override
    protected void onPostExecute(co.com.appsource.outsafetyapp.db_helper.Tables.Login result) {
        mProgress.dismiss();
        mCallback.onTaskComplete(result);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        // TODO Auto-generated method stub
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(co.com.appsource.outsafetyapp.db_helper.Tables.Login result) {
        // TODO Auto-generated method stub
        super.onCancelled(result);
    }

    @Override
    protected void onCancelled() {
        // TODO Auto-generated method stub
        super.onCancelled();
    }
}
