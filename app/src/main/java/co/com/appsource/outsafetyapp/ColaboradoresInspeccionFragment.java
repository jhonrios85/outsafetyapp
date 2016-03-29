package co.com.appsource.outsafetyapp;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

import co.com.appsource.outsafetyapp.complete.BuscarColaboradorComplete;
import co.com.appsource.outsafetyapp.custom_objects.RestfulResponse;
import co.com.appsource.outsafetyapp.db_helper.Tables.Persona;
import co.com.appsource.outsafetyapp.db_helper.Tables.PersonaDataSource;
import co.com.appsource.outsafetyapp.model.ColaboradorEncontradoAdapter;
import co.com.appsource.outsafetyapp.model.ColaboradorSeleccionadoAdapter;
import co.com.appsource.outsafetyapp.util.OutSafetyUtils;
import co.com.appsource.outsafetyapp.util.RequestMethod;
import co.com.appsource.outsafetyapp.util.RestClient;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ColaboradoresInspeccionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ColaboradoresInspeccionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ColaboradoresInspeccionFragment extends android.support.v4.app.Fragment implements BuscarColaboradorComplete {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public String intIdEmpresa;
    public boolean boolEsInspeccionPositiva;
    public String strCedulasSeleccionadas = "";

    private OnFragmentInteractionListener mListener;

    public View objColaboradoresInspeccionFragment = null;
    public ColaboradoresInspeccionFragment currentColaboradoresInspeccionFragment = null;

    public List<Persona> lstPersonaSeleccionada = null;
    public EditText etxt_num_doc = null;

    public ColaboradoresInspeccionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ColaboradoresInspeccionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ColaboradoresInspeccionFragment newInstance(String param1, String param2) {
        ColaboradoresInspeccionFragment fragment = new ColaboradoresInspeccionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Gson gson = new Gson();
        List<Persona> selectedCols = GetColaboradoresInspeccionados();
        String strSelectedCols = gson.toJson(selectedCols);

        SharedPreferences settings = getActivity().getSharedPreferences(OutSafetyUtils.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(OutSafetyUtils.CONS_COLABORADORES_SELECTED, strSelectedCols);
        editor.putBoolean(OutSafetyUtils.CONS_ES_INSP_POS, boolEsInspeccionPositiva);
        editor.commit();

        DeleteSelectedCols();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        objColaboradoresInspeccionFragment = inflater.inflate(R.layout.fragment_colaboradores_inspeccion, container, false);
        Button btnBuscar = (Button) objColaboradoresInspeccionFragment.findViewById(R.id.btnBuscarColaborador);
        currentColaboradoresInspeccionFragment = this;

        etxt_num_doc = (EditText) objColaboradoresInspeccionFragment.findViewById(R.id.etxt_num_doc);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             EditText editDocumento = etxt_num_doc;
                                             new AsyncExecuteGetPersona(currentColaboradoresInspeccionFragment, getActivity()).execute(editDocumento.getText().toString(), currentColaboradoresInspeccionFragment.intIdEmpresa, OutSafetyUtils.GetCurrentModoUso(getContext()));
                                         }
                                     }
        );

        Button btn_continuar_fin_insp = (Button) objColaboradoresInspeccionFragment.findViewById(R.id.btn_continuar_fin_insp);
        btn_continuar_fin_insp.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {

                                                          if (ValidarColaboradores()) {
                                                              ((MainActivity) getActivity()).ContinuarHaciaObservacionesInspeccion(boolEsInspeccionPositiva, intIdEmpresa);
                                                          }

                                                      }
                                                  }
        );

        if (savedInstanceState != null) {
            //AddColaboradorAfterConfChange(OutSafetyUtils.GetSelectedColaborador(getContext()));
        }

        return objColaboradoresInspeccionFragment;
    }

    public void DeleteSelectedCols() {
        ListView lstSeleccionado = (ListView) objColaboradoresInspeccionFragment.findViewById(R.id.lstvColaboradoresSeleccionados);
        lstPersonaSeleccionada = new ArrayList<Persona>();
        strCedulasSeleccionadas = "";
        etxt_num_doc.setText("");

        ColaboradorEncontradoAdapter adapterPersonas = new ColaboradorEncontradoAdapter(this.getActivity(), R.layout.item_colaborador_encontrado, lstPersonaSeleccionada);
        lstSeleccionado.setAdapter(adapterPersonas);
        adapterPersonas.notifyDataSetChanged();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.removeItem(R.id.action_evidencias);
        menu.removeItem(R.id.action_modo_off_on);

        inflater.inflate(R.menu.main, menu);

        menu.findItem(R.id.action_modo_off_on).setTitle(((MainActivity) getActivity()).ConfigurarModoDeUso(OutSafetyUtils.GetCurrentModoUso(getContext())).equals(OutSafetyUtils.MODO_USO_ONLINE) ? OutSafetyUtils.MODO_USO_OFFLINE : OutSafetyUtils.MODO_USO_ONLINE);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onTaskComplete(List<Persona> result) {
        ListView objListView = (ListView) objColaboradoresInspeccionFragment.findViewById(R.id.lstvColaboradorEncontrado);
        List<Persona> lstPersona = result;
        ColaboradorEncontradoAdapter adapterPersonas = new ColaboradorEncontradoAdapter(this.getActivity(), R.layout.item_colaborador_encontrado, lstPersona);
        objListView.setAdapter(adapterPersonas);

        //Spinner objSpinner=(Spinner)objCentroTrabajoFragment.findViewById(R.id.spCentroTrabajo);
        //CentroTrabajoAdapterSpinner adapterCentrosTrabajoSp= new CentroTrabajoAdapterSpinner(this.getActivity(), lstCentrosTrabajo);
        //objSpinner.setAdapter(adapterCentrosTrabajoSp);

        setListViewHeightBasedOnChildren(objListView);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public boolean ValidarColaboradores() {
        boolean cumplen = true;
        ListView lstSeleccionado = (ListView) objColaboradoresInspeccionFragment.findViewById(R.id.lstvColaboradoresSeleccionados);

        if (!(lstSeleccionado.getCount() > 0)) {
            cumplen = false;
            MostrarAlerta("Debe seleccionar colaboradores!!");
            return cumplen;
        }

        Persona currentPerson = null;
        for (int i = 0; i < lstSeleccionado.getCount(); i++) {
            currentPerson = (Persona) lstSeleccionado.getItemAtPosition(i);

            if (!boolEsInspeccionPositiva) {
                if (!(currentPerson.getStrFirmaBase64() != null)) {
                    cumplen = false;
                    break;
                } else {
                    if (!(currentPerson.getStrFirmaBase64() != "")) {
                        cumplen = false;
                        break;
                    } else {
                        if (!(currentPerson.getStrFirmaBase64().length() != OutSafetyUtils.SIZE_EMPTY_SIGNATURE)) {
                            {
                                cumplen = false;
                                break;
                            }
                        }

                    }
                }
            }
        }

        if (!cumplen) {
            MostrarAlerta("Todos los colaboradores deben firmar!!");
        }

        return cumplen;
    }

    public List<Persona> GetColaboradoresInspeccionados() {
        ListView lstSeleccionado = (ListView) objColaboradoresInspeccionFragment.findViewById(R.id.lstvColaboradoresSeleccionados);

        List<Persona> lstPersona = new ArrayList<Persona>();
        Persona currentPerson = null;
        for (int i = 0; i < lstSeleccionado.getCount(); i++) {
            currentPerson = (Persona) lstSeleccionado.getItemAtPosition(i);
            lstPersona.add(currentPerson);
        }

        return lstPersona;
    }

    public void MostrarAlerta(String strMensaje) {
        new AlertDialog.Builder(getContext())
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

    public void RemoverColaboradorDeInspeccion(int itemPosition, String strFirmaBase64) {
        ListView lstSeleccionado = (ListView) objColaboradoresInspeccionFragment.findViewById(R.id.lstvColaboradoresSeleccionados);
        Persona personaSeleccionada = (Persona) lstSeleccionado.getItemAtPosition(itemPosition);

        ColaboradorSeleccionadoAdapter adapterPersonasSeleccionadas = ((ColaboradorSeleccionadoAdapter) lstSeleccionado.getAdapter());
        adapterPersonasSeleccionadas.remove(personaSeleccionada);
        lstSeleccionado.setAdapter(adapterPersonasSeleccionadas);
        adapterPersonasSeleccionadas.notifyDataSetChanged();
        strCedulasSeleccionadas = strCedulasSeleccionadas.replace(personaSeleccionada.getStrCedula(), "");
    }

    public void GuardarFirmaColaborador(int itemPosition, String strFirmaBase64) {
        ListView lstSeleccionado = (ListView) objColaboradoresInspeccionFragment.findViewById(R.id.lstvColaboradoresSeleccionados);
        Persona personaSeleccionada = (Persona) lstSeleccionado.getItemAtPosition(itemPosition);
        personaSeleccionada.setStrFirmaBase64(strFirmaBase64);

        ColaboradorSeleccionadoAdapter adapterPersonasSeleccionadas = ((ColaboradorSeleccionadoAdapter) lstSeleccionado.getAdapter());
        adapterPersonasSeleccionadas.replaceItem(itemPosition, personaSeleccionada);
        lstSeleccionado.setAdapter(adapterPersonasSeleccionadas);
        adapterPersonasSeleccionadas.notifyDataSetChanged();
    }

    public void LimpiarFirma(int itemPosition) {
        ListView lstSeleccionado = (ListView) objColaboradoresInspeccionFragment.findViewById(R.id.lstvColaboradoresSeleccionados);
        Persona personaSeleccionada = (Persona) lstSeleccionado.getItemAtPosition(itemPosition);
        personaSeleccionada.setStrFirmaBase64("");

        ColaboradorSeleccionadoAdapter adapterPersonasSeleccionadas = ((ColaboradorSeleccionadoAdapter) lstSeleccionado.getAdapter());
        adapterPersonasSeleccionadas.replaceItem(itemPosition, personaSeleccionada);
        lstSeleccionado.setAdapter(adapterPersonasSeleccionadas);
        adapterPersonasSeleccionadas.notifyDataSetChanged();
    }

    public void AddColaboradorToInspeccion(int itemPosition) {
        ListView lstEncontrados = (ListView) objColaboradoresInspeccionFragment.findViewById(R.id.lstvColaboradorEncontrado);
        Persona personaEncontrada = (Persona) lstEncontrados.getItemAtPosition(itemPosition);
        personaEncontrada.setEsInspeccionPositiva(this.boolEsInspeccionPositiva);

        if (strCedulasSeleccionadas.contains(personaEncontrada.getStrCedula())) {
            MostrarAlerta("Ya ha seleccionado este colaborador antes!!");

            return;
        }

        strCedulasSeleccionadas = strCedulasSeleccionadas + "," + personaEncontrada.getStrCedula();

        if (lstPersonaSeleccionada == null) {
            lstPersonaSeleccionada = new ArrayList<Persona>();
        }

        lstPersonaSeleccionada.add(personaEncontrada);

        ListView lstSeleccionado = (ListView) objColaboradoresInspeccionFragment.findViewById(R.id.lstvColaboradoresSeleccionados);
        ColaboradorSeleccionadoAdapter adapterPersonasSeleccionadas = new ColaboradorSeleccionadoAdapter(this.getActivity(), R.layout.item_colaborador_seleccionado, lstPersonaSeleccionada);
        lstSeleccionado.setAdapter(adapterPersonasSeleccionadas);
        adapterPersonasSeleccionadas.notifyDataSetChanged();
        etxt_num_doc.setText("");
    }

    @Override
    public void onResume() {
        super.onResume();

        if (lstPersonaSeleccionada == null) {
            lstPersonaSeleccionada = ((MainActivity) getActivity()).GetSelectedColaborador();
        }

        strCedulasSeleccionadas = "";
        for (Persona itemSelected :
                lstPersonaSeleccionada) {
            strCedulasSeleccionadas = strCedulasSeleccionadas.equals("") ? itemSelected.getStrCedula() : strCedulasSeleccionadas + "," + itemSelected.getStrCedula();
        }

        ListView lstSeleccionado = (ListView) objColaboradoresInspeccionFragment.findViewById(R.id.lstvColaboradoresSeleccionados);
        ColaboradorSeleccionadoAdapter adapterPersonasSeleccionadas = new ColaboradorSeleccionadoAdapter(this.getActivity(), R.layout.item_colaborador_seleccionado, lstPersonaSeleccionada);
        lstSeleccionado.setAdapter(adapterPersonasSeleccionadas);
        adapterPersonasSeleccionadas.notifyDataSetChanged();
    }

    public void AddColaboradorAfterConfChange(List<Persona> lstSelecteds) {

        if (lstPersonaSeleccionada == null) {
            lstPersonaSeleccionada = new ArrayList<Persona>();
        }

        //lstPersonaSeleccionada.addAll(lstSelecteds);

        ListView lstSeleccionado = (ListView) objColaboradoresInspeccionFragment.findViewById(R.id.lstvColaboradoresSeleccionados);
        ColaboradorSeleccionadoAdapter adapterPersonasSeleccionadas = new ColaboradorSeleccionadoAdapter(this.getActivity(), R.layout.item_colaborador_seleccionado, lstPersonaSeleccionada);
        lstSeleccionado.setAdapter(adapterPersonasSeleccionadas);
        adapterPersonasSeleccionadas.notifyDataSetChanged();
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
        void onFragmentInteraction(Uri uri);
    }
}

class AsyncExecuteGetPersona extends AsyncTask<String, Void, List<Persona>> {

    String strCedula = "";
    String intIdCentroTrabajo = "";
    String strModoUso = "";
    public static final String OPERACION = "BCCT";
    public static final String intIdTipoPersona = "2";

    private Context mContext;
    ProgressDialog mProgress;
    private BuscarColaboradorComplete mCallback;


    public static final String SOAP_ACTION = "http://tempuri.org/CargarPersonaxEmpresa";
    public static final String OPERATION_NAME = "CargarPersonaxEmpresa";

    ///OJO: El name espace debe ser exactamente igual al namespace del WebService en el asmx.cs
    public static final String WSDL_TARGET_NAMESPACE = "http://tempuri.org/";

    public static final String SOAP_ADDRESS = OutSafetyUtils.CONS_SOAP_ADDRESS;


    public AsyncExecuteGetPersona(android.support.v4.app.Fragment objFragment, Context context) {
        this.mContext = context;
        this.mCallback = (BuscarColaboradorComplete) objFragment;
    }

    @Override
    protected List<Persona> doInBackground(String... params) {

        strCedula = params[0];
        intIdCentroTrabajo = params[1];
        strModoUso = params[2];


        RestClient objRestClient = new RestClient(String.format(OutSafetyUtils.CONS_URL_RESTFUL_JSON + OutSafetyUtils.CONS_JSON_GET_PERSONAS, "'" + strCedula + "'", intIdCentroTrabajo));
        RestfulResponse objRestfulResponse = null;
        List<Persona> lstPersonas=new ArrayList<Persona>();

        if (OutSafetyUtils.MODO_USO_OFFLINE.equals(strModoUso)) {
            PersonaDataSource objPersonaDataSource = new PersonaDataSource(mContext);
            objPersonaDataSource.open();
            lstPersonas = objPersonaDataSource.GetPersonasByDocumento(strCedula);
            objPersonaDataSource.close();

            return lstPersonas;
        }

        try {
            objRestClient.Execute(RequestMethod.GET);
            Gson gson = new Gson();
            objRestfulResponse = gson.fromJson(objRestClient.getResponse(), RestfulResponse.class);
            lstPersonas = gson.fromJson(objRestfulResponse.value, new TypeToken<List<Persona>>() {
            }.getType());


        } catch (Exception e) {
            e.printStackTrace();
        }

        return lstPersonas;
    }

    @Override
    protected void onPreExecute() {
        mProgress = new ProgressDialog(mContext);
        mProgress.setMessage("Consultado Colaborador!!...");
        mProgress.show();
    }

    @Override
    protected void onPostExecute(List<Persona> result) {
        mProgress.dismiss();
        mCallback.onTaskComplete(result);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        // TODO Auto-generated method stub
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(List<Persona> result) {
        // TODO Auto-generated method stub
        super.onCancelled(result);
    }

    @Override
    protected void onCancelled() {
        // TODO Auto-generated method stub
        super.onCancelled();
    }
}
